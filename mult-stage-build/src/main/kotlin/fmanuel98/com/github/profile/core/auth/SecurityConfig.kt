package fmanuel98.com.github.profile.core.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: JpaUserDetailsService,
    private val auth: AuthenticationManagerBuilder
) {
    private val allowUrs = arrayOf(
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**", "/users", "/login"
    )


    fun configure(auth: AuthenticationManagerBuilder) =
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())


    @Bean
    fun resourceServerFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests {
            it.requestMatchers(*allowUrs).permitAll().anyRequest().authenticated()
        }.csrf().disable().cors().and().build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}