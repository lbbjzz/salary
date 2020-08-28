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
 * @since: 2020/8/28
 * @version: 1
 */
@ApiModel(value = "更新员工工资接受参数的实体类")
@Data
public class UpdateSalaryDto {


    @ApiModelProperty(value = "工资表的Id", required = true)
    private Integer salaryId;

    @ApiModelProperty(value = "导入表Id", required = true)
    private Integer importId;

    @ApiModelProperty(value = "员工Id", required = true)
    private Integer employeeId;

    @ApiModelProperty(value = "病假天数", required = true)
    private Integer sickLeaveDay;

    @ApiModelProperty(value = "事假天数", required = true)
    private Integer personalLeaveDay;

    @ApiModelProperty(value = "迟到次数", required = true)
    private Integer lateDay;

    @ApiModelProperty(value = "加班天数", required = true)
    private Integer overtimeDay;

    @ApiModelProperty(value = "补发", required = true)
    private BigDecimal backPay;
}
