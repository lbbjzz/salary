package com.zsc.salary.bean;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 配置Mybatis-plus 自动填充
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/6/26 14:10
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
        this.strictInsertFill(metaObject, "status", Integer.class, 2);
        this.strictInsertFill(metaObject, "heatingSubsidy", BigDecimal.class, BigDecimal.valueOf(0));

        this.strictInsertFill(metaObject, "dailySickLeaveDeduction", BigDecimal.class, BigDecimal.valueOf(50));
        this.strictInsertFill(metaObject, "dailyPersonalLeaveDeduction", BigDecimal.class, BigDecimal.valueOf(30));
        this.strictInsertFill(metaObject, "dailyLateDeduction", BigDecimal.class, BigDecimal.valueOf(100));
        this.strictInsertFill(metaObject, "dailyOvertimePay", BigDecimal.class, BigDecimal.valueOf(200));
        this.strictInsertFill(metaObject, "personalEndowmentInsuranceRate", BigDecimal.class, BigDecimal.valueOf(0));
        this.strictInsertFill(metaObject, "companyEndowmentInsuranceRate", BigDecimal.class, BigDecimal.valueOf(0));
        this.strictInsertFill(metaObject, "personalUnemploymentInsuranceRate", BigDecimal.class, BigDecimal.valueOf(0));
        this.strictInsertFill(metaObject, "personalAccumulationFundRate", BigDecimal.class, BigDecimal.valueOf(0));
        this.strictInsertFill(metaObject, "companyAccumulationFundRate", BigDecimal.class, BigDecimal.valueOf(0));
        this.strictInsertFill(metaObject, "personalMedicalInsuranceRate", BigDecimal.class, BigDecimal.valueOf(0));
        this.strictInsertFill(metaObject, "companyMedicalInsuranceRate", BigDecimal.class, BigDecimal.valueOf(0));
        this.strictInsertFill(metaObject, "personalIncomeTaxRate", BigDecimal.class, BigDecimal.valueOf(0));

        this.strictInsertFill(metaObject, "sickLeaveDay", Integer.class, 0);
        this.strictInsertFill(metaObject, "personalLeaveDay", Integer.class, 0);
        this.strictInsertFill(metaObject, "lateDay", Integer.class, 0);
        this.strictInsertFill(metaObject, "overtimeDay", Integer.class, 0);
        this.strictInsertFill(metaObject, "backPay", Integer.class, 0);
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictInsertFill(metaObject, "modifyTime", LocalDateTime.class, LocalDateTime.now());

    }
}
