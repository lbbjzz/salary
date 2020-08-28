package com.zsc.salary.model.data;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  导入文件的实体类
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/8/3 18:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadData {
    private Integer employeeId;

    @TableField(fill = FieldFill.INSERT)
    private Integer sickLeaveDay;

    @TableField(fill = FieldFill.INSERT)
    private Integer personalLeaveDay;

    @TableField(fill = FieldFill.INSERT)
    private Integer lateDay;

    @TableField(fill = FieldFill.INSERT)
    private Integer overtimeDay;

    @TableField(fill = FieldFill.INSERT)
    private Integer backPay;
}
