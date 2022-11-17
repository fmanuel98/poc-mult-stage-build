package fmanuel98.com.github.profile.core.auth

import fmanuel98.com.github.profile.users.Users
import fmanuel98.com.github.profile.users.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User as Auth
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class JpaUserDetailsService(private val users: Users) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = users.findByEmail(username).orElseThrow {
            UsernameNotFoundException("Usuário ou senha errada")
        }
        return Auth(user.email, user.password, getAuthorities(user))
    }

    private fun getAuthorities(user: User) =
        user.roles?.let { roles -> roles.map { SimpleGrantedAuthority(it.nome.uppercase()) } }

}