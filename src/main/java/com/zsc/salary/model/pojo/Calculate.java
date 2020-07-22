package com.zsc.salary.model.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author D
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Calculate对象", description="")
public class Calculate implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "计算表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "病假扣款（天）")
    private BigDecimal dailySickLeaveDeduction;

    @ApiModelProperty(value = "事假扣款（天）")
    private BigDecimal dailyPersonalLeaveDeduction;

    @ApiModelProperty(value = "迟到扣款（天）")
    private BigDecimal dailyLateDeduction;

    @ApiModelProperty(value = "加班费（天）")
    private BigDecimal dailyOvertimePay;

    @ApiModelProperty(value = "个人支付养老保险（百分比）")
    private BigDecimal personalEndowmentInsuranceRate;

    @ApiModelProperty(value = "公司支付养老保险（百分比）")
    private BigDecimal companyEndowmentInsuranceRate;

    @ApiModelProperty(value = "个人支付失业保险（百分比）")
    private BigDecimal personalUnemploymentInsuranceRate;

    @ApiModelProperty(value = "个人支付公积金（百分比）")
    private BigDecimal personalAccumulationFundRate;

    @ApiModelProperty(value = "公司支付公积金（百分比）")
    private BigDecimal companyAccumulationFundRate;

    @ApiModelProperty(value = "个人支付医保（百分比）")
    private BigDecimal personalMedicalInsuranceRate;

    @ApiModelProperty(value = "公司支付医保（百分比）")
    private BigDecimal companyMedicalInsuranceRate;

    @ApiModelProperty(value = "个人所得税（百分比）")
    private BigDecimal personalIncomeTaxRate;

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
