package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.dto.CalculateDto;
import com.zsc.salary.model.pojo.Calculate;
import com.zsc.salary.service.impl.CalculateServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @ApiModelProperty(value = "获取员工的计算项目", notes = "pageNo 为页号 pageSize 为每页的个数")
    @GetMapping("/listCalculateVo/{pageNo}/{pageSize}/{deptId}/{employeeName}")
    public GlobalResponse listCalculateVo(@PathVariable(value = "pageNo") Integer pageNo,
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
        Map<String, Object> resultMap = calculateService.listCalculateVo(map);
        return GlobalResponse.success().data(resultMap).message("更新成功");
    }

    @ApiModelProperty(value = "更新员工的计算项目")
    @PostMapping("/updateCalculate")
    public GlobalResponse updateCalculate(@RequestBody CalculateDto calculateDto){
        calculateService.updateCalculate(calculateDto);
        return GlobalResponse.success().message("更新成功");
    }
}

