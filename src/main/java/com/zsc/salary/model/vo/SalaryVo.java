package com.zsc.salary.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author: D
 * @since: 2020/8/25
 * @version: 1
 */
@Data
@ApiModel(value = "封装员工工资信息的实体类")
public class SalaryVo {

    @ApiModelProperty(value = "工资Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工ID")
    private Integer employeeId;

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "员工部门ID")
    private Integer deptId;

    @ApiModelProperty(value = "员工部门")
    private String deptName;

    @ApiModelProperty(value = "员工岗位ID")
    private Integer jobId;

    @ApiModelProperty(value = "员工岗位")
    private String jobName;

    @ApiModelProperty(value = "基本工资")
    private BigDecimal basicSalary;

    @ApiModelProperty(value = "个人支付养老保险")
    private BigDecimal personalEndowmentInsurance;

    @ApiModelProperty(value = "公司支付养老保险医疗保险")
    private BigDecimal companyEndowmentInsurance;

    @ApiModelProperty(value = "个人支付失业保险")
    private BigDecimal personalUnemploymentInsurance;

    @ApiModelProperty(value = "个人支付公积金")
    private BigDecimal personalAccumulationFund;

    @ApiModelProperty(value = "公司支付公积金")
    private BigDecimal companyAccumulationFund;

    @ApiModelProperty(value = "个人支付医保")
    private BigDecimal personalMedicalInsurance;

    @ApiModelProperty(value = "公司支付医保")
    private BigDecimal companyMedicalInsurance;

    @ApiModelProperty(value = "个人所得税")
    private BigDecimal personalIncomeTax;

    @ApiModelProperty(value = "病假扣款")
    private BigDecimal sickLeaveDeduction;

    @ApiModelProperty(value = "事假扣款")
    private BigDecimal personalLeaveDeduction;

    @ApiModelProperty(value = "迟到扣款")
    private BigDecimal lateDeduction;

    @ApiModelProperty(value = "加班工资")
    private BigDecimal overtimePay;

    @ApiModelProperty(value = "补发工资")
    private BigDecimal backPay;

    @ApiModelProperty(value = "实发工资")
    private BigDecimal netPay;

    @ApiModelProperty(value = "应发工资")
    private BigDecimal shouldPay;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;
}
