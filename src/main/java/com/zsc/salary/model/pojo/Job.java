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
@ApiModel(value="Job对象", description="")
public class Job implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "岗位id", hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "岗位名称")
    private String name;

    @ApiModelProperty(value = "核定人数")
    private Integer approvedNum;

    @ApiModelProperty(value = "岗位底薪")
    private BigDecimal salary;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;


}
