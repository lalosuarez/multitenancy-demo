package com.example.service;

import io.arconia.multitenancy.web.context.resolvers.OAuth2TenantResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantOAuthConfiguration {

    @Bean
    OAuth2TenantResolver oAuth2TenantResolver() {
        return OAuth2TenantResolver
                .builder()
                .tenantClaimName("tenant")
                .build();
    }
}
