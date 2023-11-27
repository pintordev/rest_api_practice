package com.pintor.rest_api_practice.base.spring_doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "REST API", version = "v1")) // API 이름
@Configuration
public class SpringDocConfig {
}
