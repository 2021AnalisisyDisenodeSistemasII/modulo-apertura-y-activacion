package opActAcct.starBankbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración del Backend para que tenga correcto funcionamiento con el Frontend
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public WebMvcConfigurer setCors() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedMethods("GET", "POST")
                        .allowedHeaders("*")
                        .allowedOrigins("http://localhost:3006");
            }
        };
    }
}
