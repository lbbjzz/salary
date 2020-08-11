package com.zsc.salary.service;

import com.zsc.salary.model.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
public interface EmployeeService extends IService<Employee> {

    void insert(Employee employee);

    int deleteById(Integer id);

    int update(Employee employee);

    Map<String, Object> listEmployee(Integer pageNo, Integer pageSize);

    Employee findById(Integer id);
}
