package com.yhyt.health.config;



import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("'${swagger.enable}' == 'true'")
public class SwaggerConfig {
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .paths(Predicates.or(
//                        //这里添加你需要展示的接口
//                                PathSelectors.ant("/account/**"),
//                                PathSelectors.ant("/xxx/**"),
//                                PathSelectors.ant("/qqq/**"),
//                                PathSelectors.ant("/eee/**")
//                                            )
//                                )
                .apis(RequestHandlerSelectors.basePackage("com.yhyt.health.app.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("新医疗接口API")
                .description("客户端与服务端接口文档")
                .termsOfServiceUrl("http://www.cis.com.cn/")
                .contact("gsh")
                .version("1.0")
                .build();
    }
}