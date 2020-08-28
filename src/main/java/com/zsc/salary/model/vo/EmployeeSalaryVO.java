package com.zsc.salary.model.vo;/**
 * <p>
 *
 * </p>
 *
 * @author Kami
 * @date 2020/8/28 20:00
 * @version 1.0
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *@ClassName EmployeeSalaryVO
 *@Description TODO
 *@Author Kami
 *@Date 2020/8/28 20:00
 *@Version 1.0
 */
@Data
@ApiModel(value = "员工工资统计实体类")
public class EmployeeSalaryVO {

    @ApiModelProperty(value = "员工Id")
    private Integer employeeId;

    @ApiModelProperty(value = "员工名")
    private String employeeName;

    @ApiModelProperty(value = "总基本工资")
    private BigDecimal basicSalaryTotal;

    @ApiModelProperty(value = "总养老保险")
    private BigDecimal endowmentInsuranceTotal;

    @ApiModelProperty(value = "总公积金")
    private BigDecimal accumulationFundTotal;

    @ApiModelProperty(value = "总医疗保险")
    private BigDecimal medicalInsuranceTotal;

    @ApiModelProperty(value = "总病假扣款")
    private BigDecimal sickLeaveDeductionTotal;

    @ApiModelProperty(value = "总事假扣款")
    private BigDecimal personalLeaveDeductionTotal;

    @ApiModelProperty(value = "总迟到扣款")
    private BigDecimal lateDeductionTotal;

    @ApiModelProperty(value = "总加班工资")
    private BigDecimal overtimePayTotal;
}
