package com.zsc.salary.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since: 2020/8/29
 * @version: 1
 */
@Data
@ApiModel(value = "封装员工的实发工资")
public class NetPayVo {
    @ApiModelProperty(value = "工资表的ID")
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    private String employeeName;

    @ApiModelProperty(value = "员工所在部门")
    private String deptName;

    @ApiModelProperty(value = "员工所在岗位")
    private String jobName;

    @ApiModelProperty(value = "员工实发工资")
    private BigDecimal netPay;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
