package fmanuel98.com.github.profile.login

import fmanuel98.com.github.profile.core.auth.AuthUser
import fmanuel98.com.github.profile.login.token.Token
import fmanuel98.com.github.profile.login.token.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/login")
class LoginController(val auth: AuthenticationManager, val token: TokenService) {

    @PostMapping
    fun login(@RequestBody login: Login): Token {
        val userPassword = UsernamePasswordAuthenticationToken(login.email, login.password)
       var authetictin= auth.authenticate(userPassword)
        val user = authetictin.principal as AuthUser
        return token.create(user)
    }
}