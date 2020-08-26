package com.zsc.salary.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author: D
 * @since: 2020/8/24
 * @version: 1
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "岗位信息更新")
public class JobDto implements Serializable {

    @ApiModelProperty(value = "岗位ID", required = true)
    private Integer id;

    @ApiModelProperty(value = "岗位名称", required = true)
    private String name;

    @ApiModelProperty(value = "核定人数", required = true)
    private Integer approvedNum;

    @ApiModelProperty(value = "岗位底薪", required = true)
    private BigDecimal salary;

}
