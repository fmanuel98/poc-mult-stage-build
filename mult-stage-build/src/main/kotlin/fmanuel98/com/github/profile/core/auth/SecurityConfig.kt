package fmanuel98.com.github.profile.core.auth

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: JpaUserDetailsService,
    private val auth: AuthenticationManagerBuilder
) {
    private val allowUrs = arrayOf(
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**", "/users"
    )


    fun configure(auth: AuthenticationManagerBuilder) =
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())


    @Bean
    fun resourceServerFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.cors().and().csrf().disable().authorizeRequests().antMatchers(*allowUrs).permitAll().anyRequest()
            .authenticated().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}