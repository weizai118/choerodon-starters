package io.choerodon.swagger;

import io.choerodon.swagger.exclude.EnableHandSwagger2;
import io.choerodon.swagger.property.PropertyController;
import io.choerodon.swagger.property.PropertyData;
import io.choerodon.swagger.property.PropertyDataProcessor;
import io.choerodon.swagger.swagger.CustomSwagger2Controller;
import io.choerodon.swagger.swagger.extra.ExtraDataProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import springfox.documentation.RequestHandler;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static com.google.common.base.Predicates.not;

/**
 * swagger的config类，配置Docket和自定以CustomSwaggerOperationPlugin插件
 *
 * @author xausky
 */
@Configuration
@EnableHandSwagger2
public class SwaggerConfig {

    @Value("${swagger.oauthUrl:http://localhost:8080/oauth/oauth/authorize}")
    private String oauthUrl;

    @Value("${spring.application.name}")
    private String service;

    /**
     * 配置swagger-ui
     * 因为包依赖没有BasicErrorController，所以没有使用instanceOf
     *
     * @return swagger-ui Docket
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(not((RequestHandler requestHandler) ->
                requestHandler.declaringClass().getName().equals("org.springframework.boot.autoconfigure.web.BasicErrorController")
        )).build();
    }


    @Bean
    public CustomSwaggerOperationPlugin customSwaggerOperationPlugin() {
        return new CustomSwaggerOperationPlugin();
    }

    @Bean
    public OperationCustomPageRequestReader operationCustomPageRequestReader() {
        return new OperationCustomPageRequestReader();
    }

    @Bean("extraDataProcessor")
    public ExtraDataProcessor extraDataProcessor() {
        return new ExtraDataProcessor();
    }

    @Bean
    @DependsOn("extraDataProcessor")
    public CustomSwagger2Controller customSwagger2Controller() {
        return new CustomSwagger2Controller();
    }

    @Bean
    public PropertyData propertyData() {
        PropertyData propertyData = new PropertyData();
        propertyData.setService(service);
        return propertyData;
    }

    @Bean
    public PropertyDataProcessor propertyDataProcessor() {
        return new PropertyDataProcessor(propertyData());
    }


    @Bean
    public PropertyController propertyController() {
        return new PropertyController(propertyData());
    }

}
