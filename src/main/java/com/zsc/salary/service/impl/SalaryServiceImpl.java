package com.zsc.salary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.bind.v2.TODO;
import com.zsc.salary.mapper.CalculateMapper;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.mapper.ImportMapper;
import com.zsc.salary.model.pojo.Calculate;
import com.zsc.salary.model.pojo.Dept;
import com.zsc.salary.model.pojo.Import;
import com.zsc.salary.model.pojo.Salary;
import com.zsc.salary.mapper.SalaryMapper;
import com.zsc.salary.model.vo.EmployeeVO;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
import com.zsc.salary.model.vo.SalaryVo;
import com.zsc.salary.service.DeptService;
import com.zsc.salary.service.SalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsc.salary.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
@Slf4j
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements SalaryService {

    @Resource
    SalaryMapper salaryMapper;

    @Resource
    EmployeeMapper employeeMapper;

    @Resource
    ImportMapper importMapper;

    @Resource
    private DeptService deptService;

    @Resource
    CalculateMapper calculateMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public int addSalary(Integer importId) {
        Calculate calculate = calculateMapper.selectById(1);
        log.info(String.valueOf(calculate));
        Import imported = importMapper.selectById(importId);
        Integer employeeId = imported.getEmployeeId();
        log.info(String.valueOf(imported));
        //员工基本工资
        BigDecimal basicSalary = employeeMapper.getEmployeeSalary(employeeId);
        //个人支付养老保险
        BigDecimal personalEndowmentInsurance = basicSalary.multiply(calculate.getPersonalEndowmentInsuranceRate());
        log.info("个人支付养老保险" + personalEndowmentInsurance);

        //公司支付养老保险医疗保险
        BigDecimal companyEndowmentInsurance = basicSalary.multiply(calculate.getCompanyEndowmentInsuranceRate());
        log.info("公司支付养老保险医疗保险" + companyEndowmentInsurance);

        //个人支付失业保险
        BigDecimal personalUnemploymentInsurance = basicSalary.multiply(calculate.getPersonalUnemploymentInsuranceRate());
        log.info("个人支付失业保险" + personalUnemploymentInsurance);

        //个人支付失业保险
        BigDecimal personalAccumulationFund = basicSalary.multiply(calculate.getPersonalAccumulationFundRate());
        log.info("个人支付失业保险" + personalAccumulationFund);

        //公司支付公积金
        BigDecimal companyAccumulationFund = basicSalary.multiply(calculate.getCompanyAccumulationFundRate());
        log.info("公司支付公积金" + companyAccumulationFund);

        //公司支付公积金
        BigDecimal personalMedicalInsurance = basicSalary.multiply(calculate.getPersonalMedicalInsuranceRate());
        log.info("公司支付公积金" + personalMedicalInsurance);

        //公司支付医保
        BigDecimal companyMedicalInsurance = basicSalary.multiply(calculate.getCompanyMedicalInsuranceRate());
        log.info("公司支付医保" + companyMedicalInsurance);

        //个人所得税
        BigDecimal personalIncomeTax = basicSalary.multiply(calculate.getPersonalIncomeTaxRate());
        log.info("个人所得税" + personalIncomeTax);



        // 病假天数
        int sickLeaveDay = imported.getSickLeaveDay();
        log.info("病假天数" + sickLeaveDay);

        // 事假天数
        int personalLeaveDay = imported.getPersonalLeaveDay();
        log.info("事假天数" + personalLeaveDay);

        // 迟到次数
        int lateDay = imported.getLateDay();
        log.info("迟到次数" + lateDay);

        // 加班天数
        int overtimeDay = imported.getOvertimeDay();
        log.info("加班天数" + overtimeDay);

        //事假扣款
        BigDecimal personalLeaveDeduction = BigDecimal.valueOf(personalLeaveDay).multiply(calculate.getDailyPersonalLeaveDeduction());
        log.info("事假扣款" + personalLeaveDeduction);

        //病假扣款
        BigDecimal sickLeaveDeduction = basicSalary.multiply(calculate.getDailySickLeaveDeduction());
        log.info("病假扣款" + sickLeaveDeduction);

        //迟到扣款
        BigDecimal lateDeduction = basicSalary.multiply(calculate.getDailyLateDeduction());
        log.info("迟到扣款" + lateDeduction);

        //加班工资
        BigDecimal overtimePay = basicSalary.multiply(calculate.getDailyOvertimePay());
        log.info("加班工资" + overtimePay);

        //补发工资
        BigDecimal backPay = imported.getBackPay();
        log.info("补发工资" + backPay);

        //应发工资
        BigDecimal shouldPay = basicSalary.add(overtimePay).add(backPay);
        log.info("应发工资" + shouldPay);

        //实发工资
        BigDecimal netPay = shouldPay.subtract(personalEndowmentInsurance).subtract(personalUnemploymentInsurance)
                .subtract(personalAccumulationFund).subtract(sickLeaveDeduction)
                .subtract(personalLeaveDeduction).subtract(lateDeduction);
        log.info("实发工资" + netPay);

        Salary salary = new Salary(employeeId, basicSalary, personalEndowmentInsurance,
                companyEndowmentInsurance, personalUnemploymentInsurance,
                personalAccumulationFund, companyAccumulationFund,
                personalMedicalInsurance, companyMedicalInsurance,
                personalIncomeTax, sickLeaveDeduction,
                personalLeaveDeduction, lateDeduction,
                overtimePay, backPay, netPay,
                shouldPay);
        return salaryMapper.insert(salary);
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
    public Map<String, Object> listSalaryVoDetail(Map<String, Object> map) {
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        Map<String, Object> result = new HashMap<>(2);
        PageHelper.startPage(pageNo, pageSize);
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
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("queryDate", month);
        queryMap.put("deptId", deptId);

        return salaryMapper.getDeptMonthlySalaryStatById(queryMap);

    }

    @Override
    public List<SalaryDeptStatVO> getDeptMonthlySalaryStat(String month){
        List<Dept> deptList = deptService.allDept();
        List<SalaryDeptStatVO> salaryDeptStatVOList = new ArrayList<>();
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
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("queryDate", year);
        queryMap.put("deptId", deptId);

        return salaryMapper.getDeptYearlySalaryStatById(queryMap);
    }

    @Override
    public List<SalaryDeptStatVO> getDeptYearlySalaryStat(String year) {
        List<Dept> deptList = deptService.allDept();
        List<SalaryDeptStatVO> salaryDeptStatVOList = new ArrayList<>();
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
    public Map<String, Object> getCompMonthlySalaryStat(String month) {
        if (month == null) {
            throw new RuntimeException("查询数据为空，查询失败");
        }
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("queryDate", month);

        return salaryMapper.getCompMonthlySalaryStat(queryMap);
    }

    @Override
    public Map<String, Object> getCompYearlySalaryStat(String year) {
        if (year == null) {
            throw new RuntimeException("查询数据为空，查询失败");
        }
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("queryDate", year);

        return salaryMapper.getCompYearlySalaryStat(queryMap);
    }
}
