package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.service.SalaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
}

