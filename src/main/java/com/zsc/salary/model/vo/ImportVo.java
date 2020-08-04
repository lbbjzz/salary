package com.zsc.salary.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/8/4 16:06
 */
@Data
@ApiModel(value="页面显示Import数据", description="")
public class ImportVo {

    @ApiModelProperty(value = "导入Id")
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "员工部门")
    private String employeeDept;

    @ApiModelProperty(value = "员工岗位")
    private String employeeJob;

    @ApiModelProperty(value = "病假天数")
    private Integer sickLeaveDay;

    @ApiModelProperty(value = "事假天数")
    private Integer personalLeaveDay;

    @ApiModelProperty(value = "迟到次数")
    private Integer lateDay;

    @ApiModelProperty(value = "加班天数")
    private Integer overtimeDay;

    @ApiModelProperty(value = "补发")
    private BigDecimal backPay;
}
