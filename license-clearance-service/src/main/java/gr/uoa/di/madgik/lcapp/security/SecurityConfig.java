package gr.uoa.di.madgik.lcapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomFailureHandler customFailureHandler;

    @Autowired
    CustomOidcUserService customOidcUserService;

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                    .cors()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .csrf()
                    .disable()
                    .formLogin()
                    .disable()
                    .httpBasic()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                    .authorizeRequests().antMatchers("/",
                        "/compatible-licenses",
                        "/compatibility-check",
                        "/attribute-compatibility",
                        "/attribute-questions",
                        "/sections",
                        "/vocabularies",
                        "/vocabulary",
                        "/countries",
                        "/schema",
                        "/admin-create-pdf",
                        "/licenses",
                        "/license-info",
                        "/license-summaries",
                        "/submit-clearance",
                        "/download",
                        "/favicon.ico",
                        "/contact/**" ,
                        "/error/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/login/**")
                    .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                    .redirectionEndpoint()
                    .baseUri("/login/oauth2/callback/*")
                .and()
                    .userInfoEndpoint()
                    .oidcUserService(customOidcUserService)
                .and()
                    .successHandler(customSuccessHandler)
                    .failureHandler(customFailureHandler);

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
