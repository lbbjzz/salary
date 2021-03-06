package com.zsc.salary.controller;


import com.alibaba.excel.EasyExcel;
import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.listener.UploadDataListener;
import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.dto.ImportDto;
import com.zsc.salary.model.dto.ImportIdArrays;
import com.zsc.salary.model.vo.ImportVo;
import com.zsc.salary.service.ImportService;
import io.swagger.annotations.*;
import org.omg.CORBA.INTERNAL;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
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
@ApiResponses({
        @ApiResponse(code = 4000, message = "请求异常"),
        @ApiResponse(code = 2000, message = "成功请求")
})
@RestController
@RequestMapping("/import")
public class ImportController {

    @Resource
    private ImportService importService;

    @ApiOperation(value = "excel导入数据, 会确认用户是否存在", notes = "推荐使用这个方法")
    @PostMapping("/uploadData")
    public GlobalResponse uploadData(MultipartFile file) {
        if(file.isEmpty()) {
            return GlobalResponse.failed();
        }
        try {
            EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(importService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GlobalResponse.success().message("添加成功！");
    }

    @ApiResponses({
            @ApiResponse(code = 4000, message = "请求异常"),
            @ApiResponse(code = 2000, message = "成功请求")
    })
    @ApiOperation(value = "修改导入的数据", notes = "修改导入的数据，ID不能为空")
    @PostMapping("/updateImport")
    public GlobalResponse updateImport(@RequestBody ImportDto importDto) {
        int flag = importService.updateImport(importDto);
        if (flag == 0) {
            return GlobalResponse.failed().message("修改失败！");
        }
        return GlobalResponse.success().message("修改成功！");
    }

    @ApiOperation(value = "删除导入的数据")
    @PostMapping("/deleteImport")
    public GlobalResponse deleteImport(Integer importId) {
        int flag = importService.deleteImport(importId);
        if (flag == 0) {
            return GlobalResponse.failed().message("删除失败！");
        }
        return GlobalResponse.success().message("修改成功！");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "time", value = "查询时间范围，now 表示今天的数据 month表示一月之内的数据",defaultValue = "now" ,required = true, dataType = "String", paramType = "path")
    })
    @ApiOperation(value = "查询数据", notes = "包含listImportVo数据， total全部的总数量")
    @GetMapping("/listImportVo/{pageNo}/{pageSize}/{time}")
    public GlobalResponse listImportVo(@PathVariable(value = "pageNo") Integer pageNo,
                                       @PathVariable(value = "pageSize") Integer pageSize,
                                       @PathVariable(value = "time") String time) {
        Map<String, Object> map  = importService.listImportVo(pageNo, pageSize, time);
        if (map.isEmpty()) {
            return GlobalResponse.failed().message("获取失败");
        }
        return GlobalResponse.success().data(map).message("获取成功！");
    }

    @ApiOperation(value = "查询导入的考勤数据", notes = "包含listImportVo数据， total全部的总数量")
    @GetMapping("/listImport/{pageNo}/{pageSize}/{deptId}/{employeeName}")
    public GlobalResponse listImport(@PathVariable(value = "pageNo") Integer pageNo,
                                       @PathVariable(value = "pageSize") Integer pageSize,
                                       @PathVariable(value = "deptId") Integer deptId,
                                     @PathVariable(value = "employeeName") String employeeName) {
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
        Map<String, Object> resultMap = importService.listImportVo(map);
        return GlobalResponse.success().data(resultMap).message("更新成功");
    }


    @ApiOperation(value = "查询导入中重复的数据", notes = "包含listImportVo数据， total全部的总数量")
    @GetMapping("/listImportVoRepeatData")
    public GlobalResponse listImportVoRepeatData() {
        List<ImportVo> list  = importService.listImportVoRepeatData();
        if (list.isEmpty()) {
            return GlobalResponse.failed();
        }
        return GlobalResponse.success().data("repeatDataImportVo", list).message("获取成功！");
    }

    @ApiOperation(value = "批量删除重复的数据")
    @PostMapping("/deleteRepeatImportData")
    public GlobalResponse deleteRepeatImportData(@RequestBody ImportIdArrays importIdArrays) {
        importService.deleteRepeatImportData(importIdArrays.getId());
        System.out.println(Arrays.toString(importIdArrays.getId()));
        return GlobalResponse.success().message("获取成功！");
    }

    @ApiOperation(value = "清除该员工的考勤数据")
    @GetMapping("/clearImportData/{importId}")
    public GlobalResponse clearImportData(@PathVariable("importId") Integer importId) {
        int flag = importService.clearImportData(importId);
        if (flag == 0) {
            return GlobalResponse.failed().message("清除失败！");
        }
        return GlobalResponse.success().message("清除成功！");

    }

}

