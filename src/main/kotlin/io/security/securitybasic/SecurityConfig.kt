package io.security.securitybasic

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().authenticated()

        http
            // 폼 로그인 사용
            .formLogin()

            // 사용자 정의 로그인 페이지
            .loginPage("/loginPage")

            // 로그인 성공 후 이동 페이지 (핸들러 지정 없을 경우)
            .defaultSuccessUrl("/")

            // 로그인 실패 후 이동 페이지 (핸들러 지정 없을 경우)
            .failureUrl("/loginPage")

            // 아이디 파라미터명 설정(폼으로부터 넘어오는 파라미터 이름, input tag의 name 등)
            .usernameParameter("userId")

            // 패스워드 파라미터명 설정
            .passwordParameter("passwd")

            // 로그인 Form Action Url (폼 클릭 시 어느 Url로 요청을 보낼 지)
            .loginProcessingUrl("/login_proc")

            // 로그인 성공 후 실행되는 핸들러
            .successHandler { request, response, authentication ->
                println("Authentication: ${authentication.name}")
                response.sendRedirect("/")
            }

            // 로그인 실패 후 실행되는 핸들러
            .failureHandler { request, response, exception ->
                println("Exception: ${exception.message}")
                response.sendRedirect("/login")
            }

            // 해당 요청은 인증 없이도 이용 가능하도록 모두 혀용
            .permitAll()
    }


}