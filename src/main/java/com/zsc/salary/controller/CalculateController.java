package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.pojo.Calculate;
import com.zsc.salary.service.impl.CalculateServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@RestController
@RequestMapping("/calculate")
public class CalculateController {
    @Resource
    CalculateServiceImpl calculateService;

    @PostMapping("/updateCalculateFormula")
    public GlobalResponse updateCalculate(Calculate calculate){
        Integer code = calculateService.UpdateCalculateFormula(calculate);
        if(code == 0)
        {
            return GlobalResponse.failed().message("更新失败");
        }
        else{
            return GlobalResponse.success().message("更新成功");
        }
    }
}

