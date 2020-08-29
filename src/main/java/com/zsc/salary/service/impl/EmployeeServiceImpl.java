package com.zsc.salary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.mapper.CalculateMapper;
import com.zsc.salary.mapper.ImportMapper;
import com.zsc.salary.mapper.JobMapper;
import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.dto.EmployeeDTO;
import com.zsc.salary.model.pojo.Calculate;
import com.zsc.salary.model.pojo.Dept;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.pojo.Job;
import com.zsc.salary.model.vo.EmployeeFixedSalaryVo;
import com.zsc.salary.model.vo.EmployeeVO;
import com.zsc.salary.service.DeptService;
import com.zsc.salary.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsc.salary.service.JobService;
import com.zsc.salary.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private CalculateMapper calculateMapper;

    @Resource
    private ImportMapper importMapper;

    @Resource
    private JobService jobService;

    @Resource
    private DeptService deptService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 保存用户key值 admin:ID
     */
    private final String employeeKey = "employee:";

    @Override
    public void insert(Employee employee) {
        employeeMapper.insert(employee);
        //插入员工的计算项目
        Calculate calculate = new Calculate();
        calculate.setEmployeeId(employee.getId());
        while (true) {
            if (calculateMapper.insert(calculate) == 1) {
                break;
            }
        }
        //增加员工每月的考勤表
        UploadData uploadData = new UploadData();
        uploadData.setBackPay(0);
        uploadData.setLateDay(0);
        uploadData.setOvertimeDay(0);
        uploadData.setSickLeaveDay(0);
        uploadData.setPersonalLeaveDay(0);
        uploadData.setEmployeeId(employee.getId());
        importMapper.insertImport(uploadData);
    }

    @Override
    public int update(Employee employee) {
        String key = employeeKey + employee.getId();
        int flag;
        Job  job = jobService.findById(employee.getJobId());
        Dept dept = deptService.findById(employee.getDeptId());

        if(job == null || dept == null){
            flag = -1;
            return flag;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO(-1, -1,  null, job.getId(), "");

        Map<String, Object> map = this.listEmployeeVO(employeeDTO);
        long total = (long)map.get("total");

        if(total == job.getApprovedNum()){
            flag = -2;
            return flag;
        }

        flag = employeeMapper.updateById(employee);
        if (redisUtil.hasKey(key)) {
            redisUtil.set(key, employee, 24);
        }
        return flag;
    }

    @Override
    public Map<String, Object> listEmployeeVO(EmployeeDTO employeeDTO) {
        Map<String, Object> queryMap = new HashMap<>(5);
        //要查询的部门id
        if (employeeDTO.getDeptId() != null) {
            queryMap.put("deptId", employeeDTO.getDeptId());
        }
        //要查询的岗位id
        if (employeeDTO.getJobId() != null) {
            queryMap.put("jobId", employeeDTO.getJobId());
        }
        //要查询的员工名
        if (employeeDTO.getEmployeeName() != null && !employeeDTO.getEmployeeName().isEmpty()) {
            queryMap.put("employeeName", employeeDTO.getEmployeeName());
        }
        //判断是否分页
        if(employeeDTO.getPageNo() >= 0 && employeeDTO.getPageSize() >= 0){
            PageHelper.startPage(employeeDTO.getPageNo(), employeeDTO.getPageSize());
        }
        //根据查询条件queryMap获取员工具体信息
        List<EmployeeVO> list = employeeMapper.listEmployeeVO(queryMap);

        PageInfo<EmployeeVO> pageInfo = new PageInfo<>(list);

        Map<String, Object> map = new HashMap<>(2);
        map.put("listEmployeeVO", list);
        map.put("total", pageInfo.getTotal());
        return map;
    }

    @Override
    public Employee findById(Integer id) {
        String key = employeeKey + id;
        Employee employee;
        if (redisUtil.hasKey(key)) {
            employee = (Employee) redisUtil.get(key);
        } else {
            employee = employeeMapper.selectById(id);
            if (employee != null) {
                redisUtil.set(key, employee, 24);
            }
        }
        return employee;
    }

    @Override
    public int deleteById(Integer id) {
        return employeeMapper.deleteEmployeeById(id);
    }

    @Override
    public Map<String, Object> listEmployeeFixedSalaryVo(Map<String, Object> map) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        PageHelper.startPage(pageNo, pageSize);
        List<EmployeeFixedSalaryVo> employeeFixedSalaryVos = employeeMapper.listEmployeeFixedSalaryVo(map);
        PageInfo<EmployeeFixedSalaryVo> info = new PageInfo<>(employeeFixedSalaryVos);
        returnMap.put("employeeFixedSalaryVos", employeeFixedSalaryVos);
        returnMap.put("total", info.getTotal());
        return returnMap;
    }

    @Override
    public void insertHeatingSubsidy(Integer[] employeeId, BigDecimal heatingSubsidy) {
        Map<String, Object> map = returnHeatingSubsidyMap(employeeId, heatingSubsidy);
        employeeMapper.insertHeatingSubsidy(map);
    }

    @Override
    public void addHeatingSubsidy(Integer[] employeeId, BigDecimal heatingSubsidy) {
        Map<String, Object> map = returnHeatingSubsidyMap(employeeId, heatingSubsidy);
        employeeMapper.addHeatingSubsidy(map);
    }

    @Override
    public List<Employee> getEmployeeId() {
        return employeeMapper.selectList(new QueryWrapper<Employee>().eq("status", 1).select("id"));
    }

    private Map<String, Object> returnHeatingSubsidyMap(Integer[] employeeId, BigDecimal heatingSubsidy) {
        List<Integer> id = new ArrayList<>(Arrays.asList(employeeId));
        Map<String, Object> map = new HashMap<>(2);
        map.put("employeeId", id);
        map.put("heatingSubsidy", heatingSubsidy);
        return map;
    }

}
