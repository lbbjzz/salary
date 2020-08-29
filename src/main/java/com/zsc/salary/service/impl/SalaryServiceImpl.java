package com.zsc.salary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.bind.v2.TODO;
import com.zsc.salary.mapper.CalculateMapper;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.mapper.ImportMapper;
import com.zsc.salary.model.dto.ImportDto;
import com.zsc.salary.model.dto.UpdateSalaryDto;
import com.zsc.salary.model.pojo.*;
import com.zsc.salary.model.pojo.*;
import com.zsc.salary.mapper.SalaryMapper;
import com.zsc.salary.model.vo.*;
import com.zsc.salary.service.DeptService;
import com.zsc.salary.service.SalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsc.salary.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
@Slf4j
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

    @Resource
    private SalaryMapper salaryMapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private ImportMapper importMapper;

    @Resource
    private DeptService deptService;

    @Resource
    private CalculateMapper calculateMapper;

    @Async
    @Override
    public void generateSalary(Integer deptId, String time) {

        //查询部门全部的在职员工
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>().eq("dept_id", deptId)
                .eq("status", 1));
        //存放全部员工的ID
        Integer[] employeeIds = new Integer[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            employeeIds[i] = employees.get(i).getId();
        }
        System.out.println(Arrays.toString(employeeIds));

        //获取员工考勤数据
        List<Import> imports = importMapper.getByEmployeeAndTime(employeeIds, time);
        Collections.sort(imports);
        imports.forEach(System.out::println);

        //获取员工计算属性
        List<Calculate> calculateList = calculateMapper.listCalculate(employeeIds);
        Collections.sort(calculateList);
        calculateList.forEach(System.out::println);

        List<Salary> salaryList = new ArrayList<>(employeeIds.length);

        for (int i = 0; i < employeeIds.length; i++) {
            //获取该员工的全部信息
            Employee employee = employees.get(i);
            //考勤数据
            Import importing = imports.get(i);
            //计算属性数据
            Calculate calculate = calculateList.get(i);
            Salary salary = calculateSalary(employee, importing, calculate);
            salaryList.add(salary);
        }
        salaryList.forEach(System.out::println);
        salaryMapper.insertEmployeeSalary(salaryList);
    }

    @Override
    public Map<String, Object> listSalaryVo(Map<String, Object> map) {
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        Map<String, Object> result = new HashMap<>(2);
        PageHelper.startPage(pageNo, pageSize);
        List<SalaryVo> salaryVoList = salaryMapper.listSalaryVo(map);
        PageInfo<SalaryVo> pageInfo = new PageInfo<>(salaryVoList);
        result.put("salaryVoList", salaryVoList);
        result.put("total", pageInfo.getTotal());
        return result;
    }

    @Override
    public Map<String, Object> listSalaryVo(Integer deptId, String time) {
        Map<String, Object> map = new HashMap<>(2);
        if (deptId != 0) {
            map.put("deptId", deptId);
        }
        if (!"0".equals(time)) {
            map.put("time", time);
        }
        Map<String, Object> result = new HashMap<>(2);
        List<SalaryVo> salaryVoList = salaryMapper.listSalaryVo(map);
        result.put("salaryVoList", salaryVoList);
        result.put("total", salaryVoList.size());
        return result;
    }

    @Override
    public Map<String, Object> listSalaryVoDetail(Map<String, Object> map) {
        //分页当前页
        Integer pageNo = (Integer) map.get("pageNo");
        //分页大小
        Integer pageSize = (Integer) map.get("pageSize");
        Map<String, Object> result = new HashMap<>(2);
        PageHelper.startPage(pageNo, pageSize);
        //获取工资查询报表
        List<SalaryVo> salaryVoList = salaryMapper.listSalaryVoDetail(map);
        PageInfo<SalaryVo> pageInfo = new PageInfo<>(salaryVoList);
        result.put("salaryVoList", salaryVoList);
        result.put("total", pageInfo.getTotal());
        return result;
    }

    @Override
    public SalaryDeptStatVO getDeptMonthlySalaryStatById(String month, Integer deptId) {
        if (month == null || deptId == null) {
            throw new RuntimeException("查询数据为空，查询失败");
        }
        //查询条件map
        Map<String, Object> queryMap = new HashMap<>(2);
        //查询月份
        queryMap.put("queryDate", month);
        //查询的部门id
        queryMap.put("deptId", deptId);
        //根据id获得部门月度工资统计数据
        return salaryMapper.getDeptMonthlySalaryStatById(queryMap);
    }

    @Override
    public List<SalaryDeptStatVO> getDeptMonthlySalaryStat(String month) {
        //获取全部部门的信息
        List<Dept> deptList = deptService.allDept();
        List<SalaryDeptStatVO> salaryDeptStatVOList = new ArrayList<>();
        //遍历根据部门id获得部门月度工资统计信息
        deptList.forEach(dept -> {
            SalaryDeptStatVO salaryDeptStatVO = this.getDeptMonthlySalaryStatById(month, dept.getId());
            salaryDeptStatVOList.add(salaryDeptStatVO);
        });
        return salaryDeptStatVOList;
    }

    @Override
    public SalaryDeptStatVO getDeptYearlySalaryStatById(String year, Integer deptId) {
        if (year == null || deptId == null) {
            throw new RuntimeException("查询数据为空，查询失败");
        }
        //查询条件map
        Map<String, Object> queryMap = new HashMap<>(2);
        //查询年份
        queryMap.put("queryDate", year);
        //查询的部门id
        queryMap.put("deptId", deptId);
        //根据id获得部门年度工资统计数据
        return salaryMapper.getDeptYearlySalaryStatById(queryMap);
    }

    @Override
    public List<SalaryDeptStatVO> getDeptYearlySalaryStat(String year) {
        //获取全部部门的信息
        List<Dept> deptList = deptService.allDept();
        List<SalaryDeptStatVO> salaryDeptStatVOList = new ArrayList<>();
        //遍历根据部门id获得部门月度工资统计信息
        deptList.forEach(dept -> {
            SalaryDeptStatVO salaryDeptStatVO = this.getDeptYearlySalaryStatById(year, dept.getId());
            salaryDeptStatVOList.add(salaryDeptStatVO);
        });
        return salaryDeptStatVOList;
    }

    @Override
    public Boolean judgeSendSalary(Integer deptId, String time) {
        Integer count = salaryMapper.isSendSalary(deptId, time);
        return count != 0;
    }

    @Override
    public Map<String, Object> getEmployeeSalaryStat(Integer pageNo, Integer pageSize) {
        //判断分页条件
        if (pageNo >= 0 && pageSize >= 0) {
            PageHelper.startPage(pageNo, pageSize);
        }
        //获取在职的全部员工的id
        List<Employee> employeeList = employeeMapper.selectList(new QueryWrapper<Employee>().select("id").eq("status", 1));
        List<EmployeeSalaryVO> employeeSalaryVOList = new ArrayList<>();
        //遍历员工id列表
        employeeList.forEach(employee -> {
            //根据员工id获得员工工资统计信息
            EmployeeSalaryVO employeeSalaryStat = salaryMapper.getEmployeeSalaryStat(employee.getId());
            employeeSalaryVOList.add(employeeSalaryStat);
        });

        PageInfo<Employee> pageInfo = new PageInfo<>(employeeList);

        Map<String, Object> map = new HashMap<>(2);
        map.put("employeeSalaryVOList", employeeSalaryVOList);
        map.put("total", pageInfo.getTotal());
        return map;
    }

    @Override
    public Map<String, Object> listNetPayVo(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>(2);
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<NetPayVo> netPayVoList = salaryMapper.listNetPayVo(map);
        PageInfo<NetPayVo> info = new PageInfo<>(netPayVoList);
        result.put("netPayVoList", netPayVoList);
        result.put("total", info.getTotal());
        return result;
    }

    @Override
    public Map<String, Object> getCompMonthlySalaryStat(String month) {
        if (month == null) {
            throw new RuntimeException("查询数据为空，查询失败");
        }
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("queryDate", month);
        //根据月份month获取公司月度工资统计数据
        return salaryMapper.getCompMonthlySalaryStat(queryMap);
    }

    @Override
    public Map<String, Object> getCompYearlySalaryStat(String year) {
        if (year == null) {
            throw new RuntimeException("查询数据为空，查询失败");
        }
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("queryDate", year);
        //根据年份year获取公司年度工资统计数据
        return salaryMapper.getCompYearlySalaryStat(queryMap);
    }

    @Override
    public Boolean judgeIsStorage(Integer deptId, String time) {
        Integer count = salaryMapper.judgeIsStorage(deptId, time);
        return count != 0;
    }

    @Override
    public void updateSalaryStorage(UpdateSalaryDto updateSalaryDto) {
        importMapper.update(null, new UpdateWrapper<Import>()
        .set("sick_leave_day", updateSalaryDto.getSickLeaveDay())
        .set("personal_leave_day", updateSalaryDto.getPersonalLeaveDay())
        .set("late_day", updateSalaryDto.getLateDay())
        .set("overtime_day", updateSalaryDto.getOvertimeDay())
        .set("back_pay", updateSalaryDto.getBackPay())
        .eq("id", updateSalaryDto.getImportId()));

        Employee employee = employeeMapper.selectById(updateSalaryDto.getEmployeeId());
        Import importing = importMapper.selectById(updateSalaryDto.getImportId());
        Calculate calculate = calculateMapper.selectOne(new QueryWrapper<Calculate>().eq("employee_id", updateSalaryDto.getEmployeeId()));
        Salary salary = calculateSalary(employee, importing, calculate);
        salary.setId(updateSalaryDto.getSalaryId());
        salaryMapper.updateById(salary);
    }

    @Override
    public void sendSalary(Integer deptId, String time) {
        //查询部门全部的在职员工
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>().eq("dept_id", deptId)
                .eq("status", 1).select("id"));
        log.error(String.valueOf(employees));
        Map<String, Object> map = new HashMap<>(2);
        map.put("employeeList", employees);
        map.put("time", time);
        salaryMapper.updateSalaryStatus(map);
    }

    /**
     * 封装了计算员工工资
     * @param employee 员工信息
     * @param importing 员工考勤信息
     * @param calculate 员工计算项目的信息
     * @return 计算后的员工工资表
     */
    private Salary calculateSalary(Employee employee, Import importing, Calculate calculate) {
        //员工Id
        Integer employeeId = employee.getId();
        // 员工底薪
        BigDecimal basicSalary = employeeMapper.getEmployeeSalary(employeeId);
        log.info("员工底薪 = " + basicSalary);

        // 采暖补贴
        BigDecimal heatingSubsidy = employee.getHeatingSubsidy();
        log.info("采暖补贴 = " + heatingSubsidy);

        // 补发工资
        BigDecimal backPay = importing.getBackPay();
        log.info("补发工资 = " + backPay);

        // 病假扣款
        BigDecimal sickLeaveDeduction = calculate.getDailySickLeaveDeduction().multiply(BigDecimal.valueOf(importing.getSickLeaveDay()));
        log.info("病假扣款 = " + sickLeaveDeduction);

        // 事假扣款
        BigDecimal personalLeaveDeduction = calculate.getDailyPersonalLeaveDeduction().multiply(BigDecimal.valueOf(importing.getPersonalLeaveDay()));
        log.info("事假扣款 = " + personalLeaveDeduction);

        // 迟到扣款
        BigDecimal lateDeduction = calculate.getDailyLateDeduction().multiply(BigDecimal.valueOf(importing.getLateDay()));
        log.info("迟到扣款 = " + lateDeduction);

        // 加班工资
        BigDecimal overtimePay = calculate.getDailyOvertimePay().multiply(BigDecimal.valueOf(importing.getOvertimeDay()));
        log.info("加班工资 = " + overtimePay);

        // 个人支付养老保险
        BigDecimal personalEndowmentInsurance = calculate.getPersonalEndowmentInsuranceRate().multiply(basicSalary);
        log.info("个人支付养老保险 = " + personalEndowmentInsurance);

        // 公司支付养老保险医疗保险
        BigDecimal companyEndowmentInsurance = calculate.getCompanyEndowmentInsuranceRate().multiply(basicSalary);
        log.info("公司支付养老保险医疗保险 = " + companyEndowmentInsurance);

        // 个人支付失业保险
        BigDecimal personalUnemploymentInsurance = calculate.getPersonalUnemploymentInsuranceRate().multiply(basicSalary);
        log.info("个人支付失业保险 = " + personalUnemploymentInsurance);

        // 个人支付公积金
        BigDecimal personalAccumulationFund = calculate.getPersonalAccumulationFundRate().multiply(basicSalary);
        log.info("个人支付公积金 = " + personalAccumulationFund);

        // 公司支付公积金
        BigDecimal companyAccumulationFund = calculate.getPersonalAccumulationFundRate().multiply(basicSalary);
        log.info("公司支付公积金 = " + companyAccumulationFund);

        // 个人支付医保
        BigDecimal personalMedicalInsurance = calculate.getPersonalMedicalInsuranceRate().multiply(basicSalary);
        log.info("个人支付医保 = " + personalMedicalInsurance);

        // 公司支付医保
        BigDecimal companyMedicalInsurance = calculate.getCompanyMedicalInsuranceRate().multiply(basicSalary);
        log.info("公司支付医保 = " + companyMedicalInsurance);

        // 个人所得税
        BigDecimal personalIncomeTax = calculate.getPersonalIncomeTaxRate().multiply(basicSalary);
        log.info("个人所得税 = " + personalIncomeTax);

        // 应发工资 (员工底薪, 补发工资, 加班工资, 采暖补贴)
        BigDecimal shouldPay = basicSalary.add(backPay).add(overtimePay).add(heatingSubsidy);
        log.info("应发工资 = " + shouldPay);

        // 实发工资 (应发工资 - (个人支付养老保险, 个人支付失业保险, 个人支付公积金, 个人支付医保, 个人所得税
        // , 病假扣款, 事假扣款, 迟到扣款))
        BigDecimal netPay = shouldPay.subtract(personalEndowmentInsurance).subtract(personalUnemploymentInsurance)
                .subtract(personalAccumulationFund).subtract(personalMedicalInsurance)
                .subtract(personalIncomeTax).subtract(sickLeaveDeduction).subtract(personalLeaveDeduction)
                .subtract(lateDeduction);
        log.info("实发工资 = " + netPay);
        Salary salary = new Salary(employeeId,importing.getId(),basicSalary,personalEndowmentInsurance,
                companyEndowmentInsurance,personalUnemploymentInsurance,personalAccumulationFund,
                companyAccumulationFund,personalMedicalInsurance,companyMedicalInsurance,
                personalIncomeTax,sickLeaveDeduction,personalLeaveDeduction,lateDeduction,
                overtimePay, backPay, netPay, shouldPay, 0);
        log.info("工资信息 = "  + salary);
        return salary;
    }
}
