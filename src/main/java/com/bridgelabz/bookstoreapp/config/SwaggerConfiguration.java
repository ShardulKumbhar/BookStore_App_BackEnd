package com.bridgelabz.bookstoreapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * To call Swagger api calls
     * @return
     */
    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.bridgelabz.bookstoreapp"))
                .paths(PathSelectors.any()).build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        Contact contact = new Contact("CFP-161", "https://bridgelabz.com/", "kumbharshardul384@gmail.com");
        return new ApiInfoBuilder().title("BookStore Application  API ")
                .description("Online Book Store Api for managing the book store")
                .version("1.0")
                .license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").contact(contact)
                .build();
    }

}
