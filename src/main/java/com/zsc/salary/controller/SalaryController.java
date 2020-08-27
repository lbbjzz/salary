package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.service.SalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Resource
    SalaryService salaryService;

    @PostMapping("/insertSalary")
    public GlobalResponse insertSalary(Integer importId) {
        System.out.println(importId);
        int flag = salaryService.addSalary(importId);
        return GlobalResponse.success();
    }

    @GetMapping("/listEmployeeVO/{pageNo}/{pageSize}")
    public GlobalResponse listEmployeeVO(@PathVariable("pageNo") Integer pageNo,
                                         @PathVariable("pageSize") Integer pageSize) {
        Map<String, Object> map = salaryService.listSalaryVo(pageNo, pageSize);
        if (map.isEmpty()) {
            return GlobalResponse.failed();
        }
        return GlobalResponse.success().data(map);
    }

    @ApiOperation(value = "获取最高、最低、平均工资", notes = "按月份和部门查询")
    @GetMapping("/getSalaryStat")
    public GlobalResponse getSalaryStat(@RequestParam String dateTime,
                                       @RequestParam Integer deptId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(dateTime, formatter);

        Map<String, Object> salary = salaryService.getSalaryStat(time, deptId);

        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data(salary);
    }
}

