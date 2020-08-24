package com.zsc.salary.controller;


import com.zsc.salary.bean.GlobalResponse;
import com.zsc.salary.model.dto.JobDto;
import com.zsc.salary.model.pojo.Job;
import com.zsc.salary.service.JobService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/job")
public class JobController {

    @Resource
    JobService jobService;

    @PutMapping("/insertJob")
    public GlobalResponse insertJob(@RequestBody Job job) {
        if (job == null) {
            return GlobalResponse.failed().message("数据为空");
        }

        int flag = jobService.insertJob(job);

        if (flag == 1) {
            return GlobalResponse.success().message("插入成功");
        }
        return GlobalResponse.failed().message("插入失败");
    }

    @PostMapping("/updateJob")
    public GlobalResponse updateJob(@RequestBody JobDto jobDto) {
        if (jobDto == null) {
            return GlobalResponse.failed().message("数据为空");
        }

        int flag = jobService.updateJob(jobDto);

        if (flag == 1) {
            return GlobalResponse.success().message("更新成功");
        }
        return GlobalResponse.failed().message("更新失败");
    }

    @GetMapping("/listJob/{pageNo}/{pageSize}")
    public GlobalResponse listJob(@PathVariable(value = "pageNo") Integer pageNo,
                                  @PathVariable("pageSize") Integer pageSize) {
        if (pageNo == null || pageSize == null) {
            return GlobalResponse.failed().message("数据为空");
        }

        Map<String, Object> map = jobService.listJob(pageNo, pageSize);

        if (map.isEmpty()) {
            return GlobalResponse.success().message("查询失败");
        }
        return GlobalResponse.success().data(map);
    }


}

