package br.com.alura.carteira.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxSwaggerConfigurations {
	
	 @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())              
	          .paths(PathSelectors.any())                          
	          .build()
	          .apiInfo( apiInfo());
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("API Carteira de Investimentos").description("Documentação")
	                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
	                .termsOfServiceUrl("https://google.com.br").version("1.0.0")
	                .contact(new Contact("Henrique Lustosa", "google.com.br", "henriqlustosa@gmai.com")).build();
	    }
}
