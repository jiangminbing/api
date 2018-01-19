package com.soft;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by chijr on 17/3/22.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {


    @Bean
    public Docket ewalletApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(or(regex("/.*")))//过滤的接口
                .build()
                .apiInfo(ewalletApiInfo());
    }
    private ApiInfo ewalletApiInfo() {
        ApiInfo apiInfo = new ApiInfo("Api接口文档",//大标题
                " api 接口",//小标题
                "1.0",//版本
                "NO terms of service",
                "chijr@139.com",//作者
                "版权所有,公司内部使用",//链接显示文字
                "http://www.apache.org/licenses/LICENSE-2.0.html"//网站链接
        );
        return apiInfo;
    }
}




