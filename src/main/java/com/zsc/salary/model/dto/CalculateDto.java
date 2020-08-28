package com.zsc.salary.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
 * @since: 2020/8/27
 * @version: 1
 */
@Data
public class CalculateDto {
    @ApiModelProperty(value = "计算表ID")
    private Integer id;

    @ApiModelProperty(value = "计算表Id数组")
    private Integer[] ids;

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
}
