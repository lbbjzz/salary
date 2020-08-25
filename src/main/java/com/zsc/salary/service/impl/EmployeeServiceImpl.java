package com.zsc.salary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.salary.model.dto.EmployeeDTO;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.vo.EmployeeVO;
import com.zsc.salary.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public int deleteById(Integer id) {
        String key = employeeKey + id;
        if (redisUtil.hasKey(key)) {
            redisUtil.del(key);
        }
        return employeeMapper.deleteById(id);
    }

    @Override
    public int update(Employee employee) {
        String key = employeeKey + employee.getId();
        int flag = employeeMapper.updateById(employee);
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
        if (!employeeDTO.getEmployeeName().isEmpty() && employeeDTO.getEmployeeName() != null) {
            queryMap.put("employeeName", employeeDTO.getEmployeeName());
        }
        PageHelper.startPage(employeeDTO.getPageNo(), employeeDTO.getPageSize());
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

}
