package org.aldouscloud.aldouscloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String STORAGE_PATH =
            "file:/C:/Users/adile/OneDrive/Рабочий стол/Aldousrich/Cloud/aldous-cloud/.opt/aldous-cloud/storage/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(STORAGE_PATH);
    }

}
