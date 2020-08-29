package com.zsc.salary.model.vo;/**
 * <p>
 *
 * </p>
 *
 * @author Kami
 * @date 2020/8/29 11:19
 * @version 1.0
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 *@ClassName JobCountVO
 *@Description TODO
 *@Author Kami
 *@Date 2020/8/29 11:19
 *@Version 1.0
 */
@Data
@ApiModel(value = "岗位在岗人数统计实体类")
public class JobCountVO {

    @ApiModelProperty(value = "岗位名")
    private String jobName;

    @ApiModelProperty(value = "在岗人数")
    private Integer jobCount;
}
