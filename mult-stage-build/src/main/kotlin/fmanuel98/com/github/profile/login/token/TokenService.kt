package fmanuel98.com.github.profile.login.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import fmanuel98.com.github.profile.core.auth.AuthUser

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {
    fun create(user: AuthUser): Token {
        val token = JWT.create().withIssuer("Produtos").withSubject(user.username).withClaim("id", user.id)
            .withExpiresAt(LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.UTC))
            .sign(Algorithm.HMAC256("Secreat"))
        return Token(token)
    }
}

class Token(val token: String)