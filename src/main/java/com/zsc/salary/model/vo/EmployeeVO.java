package com.zsc.salary.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *@ClassName EmployeeVO
 *@Description TODO
 *@Author Kami
 *@Date 2020/8/24 11:04
 *@Version 1.0
 */
@Data
@ApiModel(value = "页面显示Employee数据")
public class EmployeeVO {

    @ApiModelProperty(value = "员工id")
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "员工所属部门")
    private String deptName;

    @ApiModelProperty(value = "员工职业")
    private String jobName;

    @ApiModelProperty(value = "性别")
    private Boolean sex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "表示员工的状态（0：离职，1：试用，2：在职）")
    private Integer status;
}
