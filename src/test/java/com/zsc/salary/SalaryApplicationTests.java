package com.zsc.salary;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SalaryApplicationTests {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EmployeeService employeeService;

    @Test
    void contextLoads() {
        System.out.println(employeeMapper.selectOne(new QueryWrapper<Employee>().select("id").eq("id", 2)));
    }

    @Test
    void insert(){
        Employee employee = new Employee();
        employee.setDeptId(1);
        employee.setJobId(1);
        employee.setName("sss");
        employeeService.insert(employee);
    }

    @Test
    void delete(){
        employeeService.deleteById(3);
    }

    @Test
    void select(){
        System.out.println(employeeService.listEmployee(1, 2));
    }

    @Test
    void update(){
        Employee employee = new Employee();
        employee.setId(4);
        employee.setName("ggggg");
        employee.setSex(true);
        System.out.println(employeeService.update(employee));
    }
}
