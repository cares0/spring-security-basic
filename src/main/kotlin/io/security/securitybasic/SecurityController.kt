package io.security.securitybasic

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController {

    @GetMapping("/")
    fun index(): String {
        return "home"
    }

    @GetMapping("/loginPage")
    fun loginPage(): String {
        return "loginPage"
    }

    @GetMapping("/user")
    fun user(): String {
        return "user"
    }

    @GetMapping("/admin/pay")
    fun adminPay(): String {
        return "admin pay"
    }

    @GetMapping("/admin/**")
    fun admin(): String {
        return "admin all"
    }

}