package com.geon.onedayonecommit.config.auth;

import com.geon.onedayonecommit.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuthUserService customOAuthUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .headers().frameOptions().disable()

                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/favicon.ico").permitAll()
                    .antMatchers("/api/**", "/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuthUserService);
    }
}
