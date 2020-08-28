package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
import com.zsc.salary.service.SalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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

    @ApiOperation(value = "根据id查询部门的月度最高、最低、平均工资", notes = "按月份和部门查询")
    @GetMapping("/getMonthlySalaryStatByDeptId")
    public GlobalResponse getMonthlySalaryStatByDeptId(@RequestParam String month,
                                                       @RequestParam Integer deptId){
        SalaryDeptStatVO salary = salaryService.getMonthlySalaryStatByDeptId(month, deptId);
        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("salaryStat", salary);
    }

    @ApiOperation(value = "查询所有部门的月度最高、最低、平均工资", notes = "按月份查询")
    @GetMapping("/getMonthlySalaryStat")
    public GlobalResponse getMonthlySalaryStat(@RequestParam String month){
        List<SalaryDeptStatVO> salary = salaryService.getMonthlySalaryStat(month);

        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("listSalaryStat", salary);
    }

    @ApiOperation(value = "根据id查询部门年度最高、最低、平均工资", notes = "按年份和部门查询")
    @GetMapping("/getYearlySalaryStatByDeptId")
    public GlobalResponse getYearlySalaryStatByDeptId(@RequestParam String year,
                                                      @RequestParam Integer deptId){
        SalaryDeptStatVO salary = salaryService.getYearlySalaryStatByDeptId(year, deptId);
        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("salaryStat", salary);
    }

    @ApiOperation(value = "查询所有部门的年度最高、最低、平均工资", notes = "按年份查询")
    @GetMapping("/getYearlySalaryStat")
    public GlobalResponse getYearlySalaryStat(@RequestParam String year){
        List<SalaryDeptStatVO> salary = salaryService.getYearlySalaryStat(year);

        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("listSalaryStat", salary);
    }
}

