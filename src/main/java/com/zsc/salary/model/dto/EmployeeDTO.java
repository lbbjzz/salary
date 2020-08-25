package com.zsc.salary.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *@ClassName EmployeeDTO
 *@Description TODO
 *@Author Kami
 *@Date 2020/8/24 16:37
 *@Version 1.0
 */
@Data
@ApiModel(value = "employee查询输入的条件")
public class EmployeeDTO {

    @ApiModelProperty(value = "分页的当前页数")
    private Integer pageNo;

    @ApiModelProperty(value = "分页的大小")
    private Integer pageSize;

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "职位id")
    private Integer jobId;

    @ApiModelProperty(value = "名字")
    private String employeeName;
}
