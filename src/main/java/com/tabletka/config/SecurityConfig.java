package com.tabletka.config;

import com.tabletka.model.user.Permission;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int ENCRYPTION_STRENGTH = 12;
    private final UserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCRYPTION_STRENGTH);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                    //.antMatchers("/admin/**").hasAuthority(Permission.PERMISSION_LEVEL_3.getPermission())
                    //.antMatchers("/apothecary/**").hasAuthority(Permission.PERMISSION_LEVEL_2.getPermission())
                    //.antMatchers("/client/**").hasAuthority(Permission.PERMISSION_LEVEL_1.getPermission())
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/login").permitAll()
                    .defaultSuccessUrl("/").permitAll()
                .and()
                    .logout()
                .logoutUrl("/auth/logout")
                .permitAll()
                    .logoutSuccessUrl("/auth");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
