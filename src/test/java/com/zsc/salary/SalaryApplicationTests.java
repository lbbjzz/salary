package com.zsc.salary;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsc.salary.mapper.EmployeeMapper;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.model.pojo.Import;
import com.zsc.salary.model.vo.EmployeeFixedSalaryVo;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
import com.zsc.salary.service.CalculateService;
import com.zsc.salary.service.EmployeeService;
import com.zsc.salary.service.SalaryService;
import com.zsc.salary.service.ImportService;
import com.zsc.salary.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import com.zsc.salary.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SalaryApplicationTests {
}
