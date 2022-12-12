package tech.powerjob.server.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * CORS
 *
 * @author tjq
 * @since 2020/4/13
 */
@Configuration
@EnableWebSocket
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<HealthCheckFilter> healthCheckFilter() {
        FilterRegistrationBean<HealthCheckFilter> registration = new FilterRegistrationBean<HealthCheckFilter>();
        registration.setFilter(new HealthCheckFilter());
        registration.addUrlPatterns("/_health");
        registration.setOrder(1);
        return registration;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
