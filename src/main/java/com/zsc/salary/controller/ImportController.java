package com.zsc.salary.controller;


import com.alibaba.excel.EasyExcel;
import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.listener.UploadDataListener;
import com.zsc.salary.listener.UploadDataNotCheckListener;
import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.model.dto.ImportDto;
import com.zsc.salary.service.ImportService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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
    @PutMapping("/uploadData")
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

    @ApiOperation(value = "excel导入数据,不会进一步确认用户是否存在", notes = "不推荐使用这个方法，除非确保导入的用户一定存在")
    @PutMapping("/uploadDataNotCheck")
    public GlobalResponse uploadDataNotCheck(MultipartFile file) {
        if(file.isEmpty()) {
            return GlobalResponse.failed();
        }
        try {
            EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataNotCheckListener(importService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GlobalResponse.success().message("添加成功！");
    }

    @ApiResponses({
            @ApiResponse(code = 4000, message = "请求异常"),
            @ApiResponse(code = 2000, message = "成功请求"),
            @ApiResponse(code = 4001, message = "修改失败，用户不存在！")
    })
    @ApiOperation(value = "修改导入的数据", notes = "修改导入的数据，ID不能为空")
    @PostMapping("/updateImport")
    public GlobalResponse updateImport(ImportDto importDto) {
        int flag = importService.updateImport(importDto);
        if (flag == -1) {
            return GlobalResponse.failed().code(4001).message("修改失败，用户不存在！");
        }
        if (flag == 0) {
            return GlobalResponse.failed().message("修改失败！");
        }
        return GlobalResponse.success().message("修改成功！");
    }

    @ApiOperation(value = "删除导入的数据")
    @DeleteMapping("/deleteImport")
    public GlobalResponse deleteImport(@RequestParam(value = "importId") Integer importId) {
        int flag = importService.deleteImport(importId);
        if (flag == 0) {
            return GlobalResponse.failed().message("删除失败！");
        }
        return GlobalResponse.success().message("修改成功！");
    }

    @ApiOperation(value = "查询数据", notes = "包含listImportVo数据， total全部的总数量")
    @GetMapping("/listImportVo")
    public GlobalResponse listImportVo(@RequestParam(value = "pageNo") Integer pageNo,
                                       @RequestParam(value = "pageSize") Integer pageSize) {
        Map<String, Object> map  = importService.listImportVo(pageNo, pageSize);
        if (map.isEmpty()) {
            return GlobalResponse.failed().message("获取失败");
        }
        return GlobalResponse.success().data(map).message("获取成功！");
    }
}

