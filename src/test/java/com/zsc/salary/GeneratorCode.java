package com.zsc.salary;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class GeneratorCode {

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //项目路径
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath+"/src/main/java");

        globalConfig.setAuthor("D");
        globalConfig.setOpen(false);
        //是否覆盖
        globalConfig.setFileOverride(false);
        //去电Service的I前缀
        globalConfig.setServiceName("%sService");
        //ID策略
        globalConfig.setIdType(IdType.AUTO);
        //日期类型
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setSwagger2(true);

        generator.setGlobalConfig(globalConfig);

        //设置数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:p6spy:mysql://47.107.234.173/salary?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false");
        dataSourceConfig.setDriverName("com.p6spy.engine.spy.P6SpyDriver");
        dataSourceConfig.setUsername("kami");
        dataSourceConfig.setPassword("dgd039133");
        dataSourceConfig.setDbType(DbType.MYSQL);

        generator.setDataSource(dataSourceConfig);

        // 包的配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName("");
        packageConfig.setParent("com.zsc.salary");
        packageConfig.setEntity("model.pojo");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setController("controller");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //映射的表名
        strategy.setInclude("admin");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //逻辑删除
        //strategy.setLogicDeleteFieldName("");

        //自动填充配置
        TableFill create_time = new TableFill("create_time", FieldFill.INSERT);
        TableFill update_time = new TableFill("modify_time", FieldFill.INSERT_UPDATE);
        TableFill deleted = new TableFill("deleted", FieldFill.INSERT);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(create_time);
        tableFills.add(update_time);
        tableFills.add(deleted);
        strategy.setTableFillList(tableFills);

        //乐观锁
//        strategy.setVersionFieldName("version");

        strategy.setRestControllerStyle(true);

        strategy.setControllerMappingHyphenStyle(true);

        generator.setStrategy(strategy);


        generator.setPackageInfo(packageConfig);

        //执行
        generator.execute();
    }
}
