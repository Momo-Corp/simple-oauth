package com.example.authgithub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    CustomOAuth2UserService customOAuth2UserService;


     public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
         this.customOAuth2UserService = customOAuth2UserService;
     }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails admin = User.withDefaultPasswordEncoder()
    //             .username("admin") // ✅ Admin local
    //             .password("password")
    //             .roles("ADMIN") // ✅ Attribue `ROLE_ADMIN`
    //             .build();

    //     return new InMemoryUserDetailsManager(admin);
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index.html", "/static/**").permitAll()
                        .anyRequest().authenticated())
                // .formLogin(formLogin -> formLogin
                // .loginPage("/admin-login")
                // .defaultSuccessUrl("/index.html", true)
                // .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)) 
                        .defaultSuccessUrl("/index.html", true))
                // .exceptionHandling(exceptions -> exceptions
                // .authenticationEntryPoint((request, response, authException) -> response
                // .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout") // CSRF ne bloque pas POST /logout
                );
        return http.build();
    }

}
