package com.zsc.salary;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.model.pojo.Import;
import com.zsc.salary.model.vo.EmployeeFixedSalaryVo;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
import com.zsc.salary.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class SalaryApplicationTests {
    @Resource
    SalaryService salaryService;

    @Resource
    JobService jobService;

    @Test
    public void test() {
        salaryService.generateSalary(1, "2020-08");
    }

    @Test
    void test1(){
        System.out.println(jobService.getJobCount());
    }
}
