package com.zsc.salary.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class FixedSalaryDto {

    @ApiModelProperty(value = "分页的当前页数")
    private Integer pageNo;

    @ApiModelProperty(value = "分页的大小")
    private Integer pageSize;

    @ApiModelProperty(value = "按部门查询 -1 为不按部门查询")
    private Integer deptId;

    @ApiModelProperty(value = "排序字段")
    private String sortName;

    @ApiModelProperty(value = "升序为 true 降序为false")
    private Boolean sortOrder;
}
