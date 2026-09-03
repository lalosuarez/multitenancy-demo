package com.example.auth;

import io.arconia.multitenancy.details.jdbc.JdbcTenantDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class TenantOAuthConfiguration {

    @Bean
    Customizer<HttpSecurity> securityFilterChainCustomizer() {
        return http -> http
                .oauth2AuthorizationServer(a -> a
                        .oidc(Customizer.withDefaults()));
    }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setEnableUpdatePassword(true);
        return jdbcUserDetailsManager;
    }

    @Bean
    JdbcTenantDetailsService jdbcTenantDetailsService(DataSource dataSource) {
        return JdbcTenantDetailsService
                .builder()
                .dataSource(dataSource)
                .build();
    }
}
