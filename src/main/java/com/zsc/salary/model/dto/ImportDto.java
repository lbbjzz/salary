package com.zsc.salary.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/8/4 15:18
 */
@Data
@ApiModel(value="修改Import传入的参数", description="")
public class ImportDto implements Serializable {

    @ApiModelProperty(value = "导入Id", required = true)
    private Integer id;

    @ApiModelProperty(value = "员工ID", required = true)
    private Integer employeeId;

    @ApiModelProperty(value = "病假天数")
    private Integer sickLeaveDay = 0;

    @ApiModelProperty(value = "事假天数")
    private Integer personalLeaveDay = 0;

    @ApiModelProperty(value = "迟到次数")
    private Integer lateDay = 0;

    @ApiModelProperty(value = "加班天数")
    private Integer overtimeDay = 0;

    @ApiModelProperty(value = "补发")
    private BigDecimal backPay = BigDecimal.valueOf(0.00);
}
