package com.zsc.salary.service.impl;

import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
