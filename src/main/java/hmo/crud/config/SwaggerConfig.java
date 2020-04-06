package hmo.crud.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@Data
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${api.version}")
    private String apiVersion;
    @Value("${swagger.enabled}")
    private String enabled = "false";
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.useDefaultResponseMessages}")
    private String useDefaultResponseMessages;
    @Value("${swagger.enableUrlTemplating}")
    private String enableUrlTemplating;
//    @Value("${swagger.deepLinking}")
//    private String deepLinking;
//    @Value("${swagger.defaultModelsExpandDepth}")
//    private String defaultModelsExpandDepth;
//    @Value("${swagger.defaultModelExpandDepth}")
//    private String defaultModelExpandDepth;
//    @Value("${swagger.displayOperationId}")
//    private String displayOperationId;
//    @Value("${swagger.displayRequestDuration}")
//    private String displayRequestDuration;
//    @Value("${swagger.filter}")
//    private String filter;
//    @Value("${swagger.maxDisplayedTags}")
//    private String maxDisplayedTags;
//    @Value("${swagger.showExtensions}")
//    private String showExtensions;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(Boolean.parseBoolean(enabled))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(Boolean.parseBoolean(useDefaultResponseMessages))
                .enableUrlTemplating(Boolean.parseBoolean(enableUrlTemplating));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(apiVersion)
                .build();
    }
}
