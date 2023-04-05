package fmanuel98.com.github.profile.core.auth

import fmanuel98.com.github.profile.users.User
import fmanuel98.com.github.profile.users.Users
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JpaUserDetailsService(private val users: Users) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): AuthUser {
        val user = users.findByEmail(username).orElseThrow {
            UsernameNotFoundException("Usuário ou senha errada")
        }
        return AuthUser(user.id, user.email, user.password, getAuthorities(user))
    }

    private fun getAuthorities(user: User) =
        user.roles?.let { roles -> roles.map { SimpleGrantedAuthority(it.nome.uppercase()) } }
}

class AuthUser(val id: Long, val email: String, val passWord: String, val roles: List<SimpleGrantedAuthority>?) :
    UserDetails {
    override fun getAuthorities() = roles

    override fun getPassword() =passWord

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}

