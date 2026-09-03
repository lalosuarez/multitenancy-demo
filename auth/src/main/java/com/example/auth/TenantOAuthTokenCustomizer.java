package com.example.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

@Component
class TenantOAuthTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    private static final Logger logger = LoggerFactory.getLogger(TenantOAuthTokenCustomizer.class);

    private final JdbcClient jdbcClient;

    TenantOAuthTokenCustomizer(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void customize(JwtEncodingContext context) {
        logger.info("Getting tenant for user {}", context.getPrincipal().getName());

        // Move this to a service instead of callind DB directly
        var tenant = jdbcClient.sql("""
                        SELECT utd.tenant_details_identifier
                        FROM users_tenant_details utd
                        WHERE utd.users_username = ?
                        """)
                .params(context.getPrincipal().getName())
                .query((rs, rowNum) -> rs.getString("tenant_details_identifier"))
                .single();
        // Adds the tenant in the claims
        // Claim name has to match with whatever the client set in OAuth2TenantResolver
        context.getClaims().claim("tenant", tenant);
    }
}
