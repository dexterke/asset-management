package com.mylab.assetmanagement.security;

import com.mylab.assetmanagement.exception.AccessDeniedHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource(name = "authUserDetailsService")
    private final AuthUserDetailsService userDetailsService;

    public SecurityConfig(AuthUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // add more authenticationProvider instances
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler();
    }

    @Bean
    @Profile({"local"})
    public SecurityFilterChain addAuthSecurityFilterChain(
                HttpSecurity http, AuthenticationManager authManager,
                HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder =
                new MvcRequestMatcher.Builder(introspector);
        http.httpBasic(Customizer.withDefaults())
            .authorizeRequests(authorizationManagerRequestMatcherRegistry ->
                                       authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                            // Allow public access to index.html & user registation
                            mvcMatcherBuilder.pattern("/error")
                            , mvcMatcherBuilder.pattern("/about")
                            , mvcMatcherBuilder.pattern("/signup")
                                    ).permitAll()

                    .requestMatchers(
                            // Only members of "ROLE_ADMIN" can access below resources
                            mvcMatcherBuilder.pattern("/api/**")
                                    ).hasAnyAuthority("ROLE_ADMIN", "ROLE_APIUSER")
                    .requestMatchers(
                                // Only authenticated members of ROLE_APIUSER can query "/api/**"
                                mvcMatcherBuilder.pattern("*/*")
                                , mvcMatcherBuilder.pattern("/swagger-ui/**")
                                , mvcMatcherBuilder.pattern("/h2-console/**")
                                    ).hasAuthority("ROLE_ADMIN")

                              )
            .authenticationManager(authManager)
            .formLogin(formlogin -> formlogin.permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler()))
            .csrf(csrf -> csrf.disable());
        return http.build();
    }


//    @Bean
    @Profile({"local"})
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests.requestMatchers(
                                                                      mvcMatcherBuilder.pattern("*/*")
                                                                      , mvcMatcherBuilder.pattern("/**")
                        )
                         .permitAll())
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}
