package com.zsc.salary;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.model.vo.EmployeeFixedSalaryVo;
import com.zsc.salary.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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
    void update(){
        List<EmployeeFixedSalaryVo> employeeFixedSalaryVos = employeeMapper.listEmployeeFixedSalaryVo(new HashMap<>());
        System.out.println(employeeFixedSalaryVos);
    }

}
