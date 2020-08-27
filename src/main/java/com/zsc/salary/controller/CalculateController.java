package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.pojo.Calculate;
import com.zsc.salary.service.impl.CalculateServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
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

    @GetMapping("/listCalculateVo/{pageNo}/{pageSize}")
    public GlobalResponse listCalculateVo(@PathVariable(value = "pageNo") Integer pageNo,
                                          @PathVariable(value = "pageSize") Integer pageSize){
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        Map<String, Object> resultMap = calculateService.listCalculateVo(map);
        return GlobalResponse.success().data(resultMap).message("更新成功");
    }
}

