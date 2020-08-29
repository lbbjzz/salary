package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.dto.ImportDto;
import com.zsc.salary.model.dto.UpdateSalaryDto;
import com.zsc.salary.model.vo.EmployeeSalaryVO;
import com.zsc.salary.model.vo.SalaryDeptStatVO;
import com.zsc.salary.service.SalaryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
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
@Slf4j
@RequestMapping("/salary")
public class SalaryController {

    @Resource
    SalaryService salaryService;

    @ApiOperation(value = "查询发放的工资", notes = "pageNo pageSize为分页 deptId为查询的部门Id 0为显示全部 time为查询的月份 0 为不查询")
    @GetMapping("/listSalaryVO")
    public GlobalResponse listSalaryVO(@RequestParam Integer pageNo,
                                       @RequestParam Integer pageSize,
                                       @RequestParam Integer deptId,
                                       @RequestParam String time) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        if (deptId != 0) {
            map.put("deptId", deptId);
        }
        if (!"0".equals(time)) {
            map.put("time", time);
        }

        log.error(String.valueOf(map));
        Map<String, Object> result = salaryService.listSalaryVo(map);
        if (deptId != 0 && !"0".equals(time)) {
            Boolean flag = salaryService.judgeIsStorage(deptId, time);
            result.put("isStorage", flag);
        }
        if (map.isEmpty()) {
            return GlobalResponse.failed();
        }
        return GlobalResponse.success().data(result);
    }

    @ApiOperation(value = "工资查询报表", notes = "pageNo，pageSize为分页，beginTime为开始日期(-1为不查询)，endTime为结束日期(-1为不查询)，deptName为部门名(-1为不查询)")
    @GetMapping("/listSalaryVoDetail")
    public GlobalResponse listSalaryVoDetail(@RequestParam Integer pageNo,
                                             @RequestParam Integer pageSize,
                                             @RequestParam String beginDate,
                                             @RequestParam String endDate,
                                             @RequestParam String deptName) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);

        if (!"-1".equals(beginDate)) {
            map.put("beginDate", beginDate);
        }
        if (!"-1".equals(endDate)) {
            map.put("endDate", endDate);
        }
        if (!"-1".equals(deptName)) {
            map.put("deptName", deptName);
        }

        log.error(String.valueOf(map));
        Map<String, Object> result = salaryService.listSalaryVoDetail(map);
        if (map.isEmpty()) {
            return GlobalResponse.failed();
        }
        return GlobalResponse.success().data(result);
    }

    @ApiOperation(value = "根据id查询部门的月度最高、最低、平均工资", notes = "按月份和部门查询")
    @GetMapping("/getMonthlySalaryStatByDeptId")
    public GlobalResponse getMonthlySalaryStatByDeptId(@RequestParam String month,
                                                       @RequestParam Integer deptId){
        SalaryDeptStatVO salary = salaryService.getDeptMonthlySalaryStatById(month, deptId);
        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("salaryStat", salary).message("查询成功");
    }

    @ApiOperation(value = "查询所有部门的月度最高、最低、平均工资", notes = "按月份查询")
    @GetMapping("/getMonthlySalaryStat")
    public GlobalResponse getMonthlySalaryStat(@RequestParam String month){
        List<SalaryDeptStatVO> salary = salaryService.getDeptMonthlySalaryStat(month);

        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("listSalaryStat", salary).message("查询成功");
    }

    @ApiOperation(value = "根据id查询部门年度最高、最低、平均工资", notes = "按年份和部门查询")
    @GetMapping("/getYearlySalaryStatByDeptId")
    public GlobalResponse getYearlySalaryStatByDeptId(@RequestParam String year,
                                                      @RequestParam Integer deptId){
        SalaryDeptStatVO salary = salaryService.getDeptYearlySalaryStatById(year, deptId);
        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("salaryStat", salary).message("查询成功");
    }

    @ApiOperation(value = "查询所有部门的年度最高、最低、平均工资", notes = "按年份查询")
    @GetMapping("/getYearlySalaryStat")
    public GlobalResponse getYearlySalaryStat(@RequestParam String year){
        List<SalaryDeptStatVO> salary = salaryService.getDeptYearlySalaryStat(year);

        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data("listSalaryStat", salary).message("查询成功");
    }

    @ApiOperation(value = "查询公司的月度最高、最低、平均工资", notes = "按月份查询")
    @GetMapping("/getCompMonthlySalaryStat")
    public GlobalResponse getCompMonthlySalaryStat(@RequestParam String month){
        Map<String, Object> salary = salaryService.getCompMonthlySalaryStat(month);

        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data(salary).message("查询成功");
    }

    @ApiOperation(value = "查询公司的年度最高、最低、平均工资", notes = "按年份查询")
    @GetMapping("/getCompYearlySalaryStat")
    public GlobalResponse getCompYearlySalaryStat(@RequestParam String year){
        Map<String, Object> salary = salaryService.getCompYearlySalaryStat(year);

        if (salary == null) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data(salary).message("查询成功");
    }

    @ApiOperation(value = "判断部门在该月是否发放了工资")
    @GetMapping("/judgeSendSalary/{deptId}/{time}")
    public GlobalResponse judgeSendSalary(@PathVariable(value = "deptId") Integer deptId,
                                          @PathVariable(value = "time") String time){
        log.error(String.valueOf(deptId));
        log.error(time);
        Boolean isSend = salaryService.judgeSendSalary(deptId, time);
        return GlobalResponse.success().data("isSend", isSend);
    }


    @ApiOperation(value = "给部门指定月份发放工资")
    @PostMapping("/generateSalary")
    public GlobalResponse generateSalary(Integer deptId, String time){
        salaryService.generateSalary(deptId, time);
        return GlobalResponse.success();
    }

    @ApiOperation(value = "编辑暂存的工资")
    @PostMapping("/updateSalaryStorage")
    public GlobalResponse updateSalaryStorage(@RequestBody UpdateSalaryDto updateSalaryDto){
        log.error(String.valueOf(updateSalaryDto));
        salaryService.updateSalaryStorage(updateSalaryDto);
        return GlobalResponse.success();
    }

    @ApiOperation(value = "发放工资，将暂存的工资状态改为发放")
    @PostMapping("/sendSalary")
    public GlobalResponse sendSalary(Integer deptId, String time){
        log.error(String.valueOf(deptId));
        log.error(String.valueOf(time));
        salaryService.sendSalary(deptId, time);
        return GlobalResponse.success();
    }

    @ApiOperation(value = "员工工资统计报表")
    @GetMapping("/getEmployeeSalaryStat")
    public GlobalResponse getEmployeeSalaryStat(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Map<String, Object> result = salaryService.getEmployeeSalaryStat(pageNo, pageSize);
        if (result.isEmpty()) {
            return GlobalResponse.failed().message("查询失败");
        }
        return GlobalResponse.success().data(result);
    }

    @ApiOperation(value = "查询员工实发工资", notes = "total为数据总数用于分页，netPayVoList 为查询的数据")
    @GetMapping("/listNetPayVo/{pageNo}/{pageSize}/{deptId}/{employeeName}")
    public GlobalResponse listNetPayVo(@PathVariable(value = "pageNo") Integer pageNo,
                                            @PathVariable(value = "pageSize") Integer pageSize,
                                            @PathVariable(value = "deptId") Integer deptId,
                                            @PathVariable(value = "employeeName") String employeeName){

        System.out.println("employeeName = " + employeeName);
        System.out.println("deptId = " + deptId);
        Map<String, Object> map = new HashMap<>();
        if (deptId != 0) {
            map.put("deptId", deptId);
        }
        if (!"null".equals(employeeName)) {
            map.put("employeeName", employeeName);
        }
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        Map<String, Object> resultMap = salaryService.listNetPayVo(map);
        return GlobalResponse.success().data(resultMap).message("查询成功");
    }
}

