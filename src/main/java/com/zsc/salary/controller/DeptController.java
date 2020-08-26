package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.pojo.Dept;
import com.zsc.salary.service.DeptService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @ApiOperation(value = "新增部门信息")
    @PostMapping("/insertDept")
    public GlobalResponse insertDept(@RequestBody Dept dept) {
        if (dept == null) {
            return GlobalResponse.failed().message("添加失败");
        }
        deptService.insert(dept);
        return GlobalResponse.success().message("添加成功");
    }

    @ApiOperation(value = "删除部门信息", notes = "逻辑删除")
    @DeleteMapping("/deleteDept")
    public GlobalResponse deleteDept(@RequestParam Integer id) {
        int flag = deptService.deleteById(id);
        if (flag == -1){
            return GlobalResponse.failed().message("还有员工在该部门，删除失败");
        }
        if (flag == 0) {
            return GlobalResponse.failed().message("删除失败");
        }
        return GlobalResponse.success().message("删除成功");
    }

    @ApiOperation(value = "更新部门信息", notes = "0为更新失败，1为更新成功")
    @PostMapping("/updateDept")
    public GlobalResponse updateDept(@RequestBody Dept dept) {
        int flag = 0;
        if (dept != null) {
            flag = deptService.update(dept);
        }
        if (flag == 0) {
            return GlobalResponse.failed().message("更新失败");
        }
        return GlobalResponse.success().message("更新成功");
    }

    @ApiOperation(value = "根据id查询部门信息")
    @GetMapping("/findById")
    public GlobalResponse findById(@RequestParam Integer id) {
        Dept dept = deptService.findById(id);
        if (dept == null) {
            return GlobalResponse.failed().message("查找失败");
        }
        return GlobalResponse.success().data("dept", dept).message("查找成功");
    }

    @ApiOperation(value = "分页查询所有部门信息")
    @GetMapping("/listDept")
    public GlobalResponse listDept(@RequestParam Integer pageNo,
                                   @RequestParam Integer pageSize) {
        Map<String, Object> map = deptService.listDept(pageNo, pageSize);
        if (map.isEmpty()) {
            return GlobalResponse.failed().message("查找失败");
        }
        return GlobalResponse.success().data(map).message("查找成功");
    }
}

