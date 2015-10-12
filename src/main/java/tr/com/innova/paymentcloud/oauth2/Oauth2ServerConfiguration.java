package tr.com.innova.paymentcloud.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * Created by mehme on 12.10.2015.
 */
@Configuration
public class Oauth2ServerConfiguration {

    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Value("${config.oauth2.jwt.privateKey}")
        private String privateKey;

        @Value("${config.oauth2.jwt.publicKey}")
        private String publicKey;

        @Bean
        private JwtAccessTokenConverter jwtTokenEnhancer() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(privateKey);
            converter.setVerifierKey(publicKey);
            return converter;
        }

        @Bean
        public JwtTokenStore tokenStore() {
            return new JwtTokenStore(jwtTokenEnhancer());
        }

        @Autowired
        private DataSource dataSource;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private AppCredentialsDetailService appCredentialsDetailService;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager)
                    .tokenStore(tokenStore())
                    .accessTokenConverter(jwtTokenEnhancer())
                    .userDetailsService(appCredentialsDetailService);
        }


        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.tokenKeyAccess("isAnonymous()") // permitAll()
                    .checkTokenAccess("hasRole('TRUSTED_CLIENT')"); // isAuthenticated()
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.jdbc(dataSource);//.withClient("apikey").secret("123").authorizedGrantTypes("client_credentials", "password", "refresh_token").scopes("payment_sale", "payment_refund", "payment_execute");
        }
    }
}
