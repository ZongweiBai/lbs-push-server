package com.github.baymin.pushserver.config

import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.ResponseEntity
import org.springframework.util.StopWatch
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import java.nio.ByteBuffer

import static springfox.documentation.builders.PathSelectors.regex

/**
 * SwaggerUI配置
 * @author Zongwei* @date 2020/4/6 15:13
 */
@Slf4j
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
class SwaggerConfig {

    @Bean
    Docket platformApiDocket() {
        log.info("swagger:开始初始化Swagger API")
        def watch = new StopWatch()
        watch.start()

        def docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("PushServer Api")
                .apiInfo(getApiInfo("PushServer API", "基于地理位置的个推后台服务API"))
                .forCodeGeneration(true)
                .directModelSubstitute(ByteBuffer.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.baymin.pushserver"))
                .paths(regex("/api/.*"))
                .build()
        // 解决Groovy中使用Swagger导致,页面卡死问题
        docket.ignoredParameterTypes(MetaClass.class)
        watch.stop()
        log.info("swagger:完成初始化Swagger API，共耗时{}毫秒", watch.getTotalTimeMillis())
        return docket
    }

    private ApiInfo getApiInfo(String title, String descrpition) {
        return new ApiInfo(
                title,
                descrpition,
                "1.0.0",
                "",
                getContactInfo(),
                "",
                "",
                new ArrayList<>())
    }

    private Contact getContactInfo() {
        return new Contact(
                "ZongweiBai",
                "https://github.com/ZongweiBai",
                "")
    }

}
