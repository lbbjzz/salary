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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Import对象")
public class Import implements Serializable, Comparable<Import>{

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "导入Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工ID")
    private Integer employeeId;

    @ApiModelProperty(value = "病假天数")
    @TableField(fill = FieldFill.INSERT)
    private Integer sickLeaveDay;

    @ApiModelProperty(value = "事假天数")
    @TableField(fill = FieldFill.INSERT)
    private Integer personalLeaveDay;

    @ApiModelProperty(value = "迟到次数")
    @TableField(fill = FieldFill.INSERT)
    private Integer lateDay;

    @ApiModelProperty(value = "加班天数")
    @TableField(fill = FieldFill.INSERT)
    private Integer overtimeDay;

    @ApiModelProperty(value = "补发")
    @TableField(fill = FieldFill.INSERT)
    private BigDecimal backPay;

    @ApiModelProperty(value = "状态，0为未结算，1为已结算")
    @TableField(fill = FieldFill.INSERT)
    private boolean deleted;

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


    @Override
    public int compareTo(Import o) {
        return this.employeeId - o.employeeId;
    }
}
