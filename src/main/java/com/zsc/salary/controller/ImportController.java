package com.zsc.salary.controller;


import com.alibaba.excel.EasyExcel;
import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.listener.UploadDataListener;
import com.zsc.salary.model.data.UploadData;
import com.zsc.salary.service.ImportService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/import")
public class ImportController {

    @Resource
    private ImportService importService;

    @PutMapping("/upload")
    public GlobalResponse uploadData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(importService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GlobalResponse.success().message("添加成功！");
    }
}

