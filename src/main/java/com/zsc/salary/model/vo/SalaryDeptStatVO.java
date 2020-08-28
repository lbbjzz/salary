package com.zsc.salary.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *@ClassName SalaryDeptStatVO
 *@Description TODO
 *@Author Kami
 *@Date 2020/8/27 17:19
 *@Version 1.0
 */
@Data
@ApiModel("工资总结统计的实体类")
public class SalaryDeptStatVO {

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "部门名字")
    private String deptName;

    @ApiModelProperty(value = "查询日期（年/月）")
    private String queryDate;

    @ApiModelProperty(value = "总工资")
    private BigDecimal sumSalary;

    @ApiModelProperty(value = "最高工资")
    private BigDecimal maxSalary;

    @ApiModelProperty(value = "最低工资")
    private BigDecimal minSalary;

    @ApiModelProperty(value = "平均工资")
    private BigDecimal avgSalary;
}
