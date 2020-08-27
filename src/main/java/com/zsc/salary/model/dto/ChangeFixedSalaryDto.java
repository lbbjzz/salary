package com.zsc.salary.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

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
@ApiModel(value = "修改员工固定工资接受的参数封装实体类")
public class ChangeFixedSalaryDto {

    @ApiModelProperty(value = "操作方式", notes = "1表示编辑，2表示增加，3表示删除")
    private Integer operation;

    @ApiModelProperty(value = "员工的ID数组")
    private Integer[] employeeId;

    @ApiModelProperty(value = "采暖补贴")
    private BigDecimal heatingSubsidy;
}
