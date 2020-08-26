package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.dto.EmployeeDTO;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @ApiOperation(value = "新增员工信息")
    @PostMapping("/insertEmployee")
    public GlobalResponse insertEmployee(@RequestBody Employee employee) {
        if (employee == null) {
            return GlobalResponse.failed().message("添加失败");
        }
        employeeService.insert(employee);
        return GlobalResponse.success().message("添加成功");
    }

    @ApiOperation(value = "根据id查询员工信息")
    @GetMapping("/findById")
    public GlobalResponse findById(@RequestParam Integer id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return GlobalResponse.failed().message("获取失败");
        }
        return GlobalResponse.success().data("employee", employee).message("获取成功");
    }

    @ApiOperation(value = "根据条件分页查询员工信息")
    @GetMapping("/listEmployeeVO")
    public GlobalResponse listEmployeeVO(EmployeeDTO employeeDTO) {
        if (employeeDTO.getPageNo() == null) {
            employeeDTO.setPageNo(1);
        }
        if (employeeDTO.getPageSize() == null) {
            employeeDTO.setPageSize(5);
        }
        Map<String, Object> map = employeeService.listEmployeeVO(employeeDTO);
        if (map.isEmpty()) {
            return GlobalResponse.failed().message("获取失败");
        }
        return GlobalResponse.success().data(map).message("获取成功");
    }

    @ApiOperation(value = "更新员工信息", notes = "-2为岗位超过核定人数，-1为要修改的部门或岗位不存在，0为更新失败，1为更新成功")
    @PostMapping("/updateEmployee")
    public GlobalResponse updateEmployee(@RequestBody Employee employee) {
        int flag = 0;
        if (employee != null) {
            flag = employeeService.update(employee);
        }
        if (flag == 0) {
            return GlobalResponse.failed().message("修改失败");
        }
        if (flag == -1) {
            return GlobalResponse.failed().message("该部门或岗位不存在");
        }
        if (flag == -2) {
            return GlobalResponse.failed().message("超过岗位核定人数");
        }
        return GlobalResponse.success().message("修改成功");
    }

    @PostMapping("/deleteById")
    public GlobalResponse deleteById(@RequestParam Integer id) {
        int flag = employeeService.deleteById(id);
        if (flag == 0){
            return GlobalResponse.failed().message("删除失败");
        }
        return GlobalResponse.success().message("删除成功");
    }

}

