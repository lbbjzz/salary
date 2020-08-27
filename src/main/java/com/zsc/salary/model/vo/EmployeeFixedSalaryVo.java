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
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author: D
 * @since: 2020/8/26
 * @version: 1
 */
@Data
@ApiModel(value = "员工的固定工资实体类")
public class EmployeeFixedSalaryVo {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "部门名")
    private String deptName;

    @ApiModelProperty(value = "岗位名")
    private String jobName;

    @ApiModelProperty(value = "岗位底薪")
    private BigDecimal salary;

    @ApiModelProperty(value = "采暖补贴")
    private BigDecimal heatingSubsidy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    private LocalDateTime modifyTime;
}
