package com.zsc.salary.model.data;

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
    private Integer sickLeaveDay;
    private Integer personalLeaveDay;
    private Integer lateDay;
    private Integer overtimeDay;
    private Integer backPay;
}
