package com.zsc.salary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;


/**
 * <p>
 * 配置Swagger
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/6/9 16:30
 */

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    /**
     * 配置swagger Docket实例
     * @return 返回Docket 对象
     */
    @Bean
    public Docket docket(Environment environment) {

        Profiles profiles = Profiles.of("dev");
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zsc.salary.controller"))
                .build();
    }

    /**
     * 配置swagger 信息 apiInfo
     * @return ApiInfo 配置信息
     */
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("不会起名", "", "*********@qq.com");

        return new ApiInfo("工资管理系统",
                "菜鸟团队在线秀操作",
                "1.0", "urn:tos",
                contact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }
}
