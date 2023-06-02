package io.security.securitybasic

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {

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

        http
            // 로그아웃 사용
            .logout()

            // 로그아웃 URL
            .logoutUrl("/logout")

            // 로그아웃 성공 시 리다이렉트 할 URL
            .logoutSuccessUrl("/login")

            // 로그아웃 핸들러
            .addLogoutHandler { request, response, authentication ->
                request.session.invalidate()
            }

            // 로그아웃 성공 후 핸들러
            .logoutSuccessHandler { request, response, authentication ->
                response.sendRedirect("/login")
            }

            // 로그아웃 후 쿠키 삭제
            .deleteCookies("remember-me")

        http
            // Remember Me 사용
            .rememberMe()

            // Remember Me 적용 여부 파라미터 이름(기본: "remember-me")
            .rememberMeParameter("remember")

            // Remember Me 쿠키에 담긴 토큰의 유효시간(기본: 14일)
            .tokenValiditySeconds(3600)

            // 파라미터가 없더라도 항상 Remember Me를 적용할지 여부
            .alwaysRemember(true)

            // Remember Me를 인증하기 위해 User의 정보를 가져오는 Service
            .userDetailsService(userDetailsService)

        http
            // 세션 정책 설정
            .sessionManagement()

            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

            // 세션 고정 보호 정책 지정
            .sessionFixation().migrateSession()

            // 세션이 유효하지 않을 때 이동 할 페이지
            .invalidSessionUrl("/login")

            // 동시 세션 제어 설정
            // 최대 허용 가능 세션 수 (-1: 무제한 로그인 세션 허용)
            .maximumSessions(1)

            // 동시 로그인 차단 (기본값: false - 기존 세션 만료 정책 적용)
            .maxSessionsPreventsLogin(true)

            // 세션이 만료된 경우 이동 할 페이지
            .expiredUrl("/expired")
    }


}