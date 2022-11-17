package fmanuel98.com.github.profile.users

import fmanuel98.com.github.profile.core.exceptionhandler.BussinesException
import fmanuel98.com.github.profile.core.exceptionhandler.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val users: Users, private val passwordEncoder: PasswordEncoder) {

    fun save(user: User): User {
        val userInDb = users.findByEmail(user.email)
        if (userInDb.isPresent && userInDb.get() == user) throw BussinesException("Já existe um usuário cadastrado com o e-mail ${user.email}")
        if (user.isNew) user.apply { password = passwordEncoder.encode(user.password) }
        return users.save(user)
    }

    @Transactional
    fun changePassWord(userId: Long, passWord: String, newpassword: String) {
        val user = findUser(userId)
        if (!passwordEncoder.matches(passWord, user.password)) {
            throw BussinesException("Senha atual informada não coincide com a senha do usuário.")
        }
        user.copy(password = passwordEncoder.encode(newpassword))
    }

    fun findUser(userId: Long): User {
        return users.findById(userId).orElseThrow { EntityNotFoundException("$userId") }
    }
}