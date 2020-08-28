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
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@ApiModel(value="Salary对象", description="")
public class Salary implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "工资Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工ID")
    private Integer employeeId;

    @ApiModelProperty(value = "员工考勤表ID")
    private Integer importId;

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

    @ApiModelProperty(value = "结算状态")
    private Integer status;

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

    public Salary(Integer employeeId, BigDecimal basicSalary, BigDecimal personalEndowmentInsurance,
                  BigDecimal companyEndowmentInsurance, BigDecimal personalUnemploymentInsurance,
                  BigDecimal personalAccumulationFund, BigDecimal companyAccumulationFund,
                  BigDecimal personalMedicalInsurance, BigDecimal companyMedicalInsurance,
                  BigDecimal personalIncomeTax, BigDecimal sickLeaveDeduction,
                  BigDecimal personalLeaveDeduction, BigDecimal lateDeduction,
                  BigDecimal overtimePay, BigDecimal backPay, BigDecimal netPay,
                  BigDecimal shouldPay) {
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.personalEndowmentInsurance = personalEndowmentInsurance;
        this.companyEndowmentInsurance = companyEndowmentInsurance;
        this.personalUnemploymentInsurance = personalUnemploymentInsurance;
        this.personalAccumulationFund = personalAccumulationFund;
        this.companyAccumulationFund = companyAccumulationFund;
        this.personalMedicalInsurance = personalMedicalInsurance;
        this.companyMedicalInsurance = companyMedicalInsurance;
        this.personalIncomeTax = personalIncomeTax;
        this.sickLeaveDeduction = sickLeaveDeduction;
        this.personalLeaveDeduction = personalLeaveDeduction;
        this.lateDeduction = lateDeduction;
        this.overtimePay = overtimePay;
        this.backPay = backPay;
        this.netPay = netPay;
        this.shouldPay = shouldPay;
    }
}
