package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.pojo.Employee;
import com.zsc.salary.service.EmployeeService;
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
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @PostMapping("/insertEmployee")
    public GlobalResponse insertEmployee(@RequestBody Employee employee) {
        if (employee == null) {
            return GlobalResponse.failed().message("添加失败");
        }
        employeeService.insert(employee);
        return GlobalResponse.success().message("添加成功");
    }

    @GetMapping("/findById")
    public GlobalResponse findById(@RequestParam Integer id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return GlobalResponse.failed().message("获取失败");
        }
        return GlobalResponse.success().data("employee", employee).message("获取成功");
    }

    @GetMapping("/listEmployee")
    public GlobalResponse listEmployeeVO(@RequestParam Integer pageNo,
                                       @RequestParam Integer pageSize) {
        Map<String, Object> map = employeeService.listEmployeeVO(pageNo, pageSize);
        if (map.isEmpty()) {
            return GlobalResponse.failed().message("获取失败");
        }
        return GlobalResponse.success().data(map).message("获取成功");
    }

    @PostMapping("/updateEmployee")
    public GlobalResponse updateEmployee(@RequestBody Employee employee) {
        int flag = 0;
        if (employee != null) {
            flag = employeeService.update(employee);
        }
        if (flag == 0) {
            return GlobalResponse.failed().message("修改失败");
        }
        return GlobalResponse.success().message("修改成功");
    }

    @DeleteMapping("/deleteEmployee")
    public GlobalResponse deleteEmployee(@RequestParam Integer id) {
        int flag = employeeService.deleteById(id);
        if (flag == 0) {
            return GlobalResponse.failed().message("删除失败");
        }
        return GlobalResponse.success().message("删除成功");
    }
}

