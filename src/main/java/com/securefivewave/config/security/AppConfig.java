package com.securefivewave.config.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.securefivewave.tool.WebXssSecurityUtils;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class AppConfig {

    private static boolean TRACER;

    public static boolean isTracerEnabled() {
        return AppConfig.TRACER;
    }

    @Value("${app.enable.tracer:false}")
    public void setTracer(boolean enabledTracer) {
        AppConfig.TRACER = enabledTracer;
    }

    @Bean
    @Primary
    ObjectMapper xssObjectMapper(Jackson2ObjectMapperBuilder builder) {
        // parser serialized
        final ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // Register XSS parser
        final SimpleModule xssModule = new SimpleModule("XssStringJsonSerializer");

        xssModule.addDeserializer(String.class, new JsonDeserializer<>() {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException {
                final String value = jsonParser.getValueAsString();
                return WebXssSecurityUtils.validateXSS(value);
            }
        });

        objectMapper.registerModule(xssModule);

        // Return
        return objectMapper;
    }

}
