package com.zsc.salary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.mapper.JobMapper;
import com.zsc.salary.model.dto.EmployeeDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (employeeDTO.getDeptId() != null) {
            queryMap.put("deptId", employeeDTO.getDeptId());
        }
        if (employeeDTO.getJobId() != null) {
            queryMap.put("jobId", employeeDTO.getJobId());
        }
        if (employeeDTO.getEmployeeName() != null && !employeeDTO.getEmployeeName().isEmpty()) {
            queryMap.put("employeeName", employeeDTO.getEmployeeName());
        }

        if(employeeDTO.getPageNo() >= 0 && employeeDTO.getPageSize() >= 0){
            PageHelper.startPage(employeeDTO.getPageNo(), employeeDTO.getPageSize());
        }
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
        int flag = employeeMapper.deleteEmployeeById(id);
        return flag;

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

}
