package io.security.securitybasic

import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
@Order(0)
class SecurityConfig1 : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .antMatcher("/admin/**")
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
    }

}

@Configuration
@EnableWebSecurity
@Order(1)
class SecurityConfig2 : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .formLogin()
    }

}