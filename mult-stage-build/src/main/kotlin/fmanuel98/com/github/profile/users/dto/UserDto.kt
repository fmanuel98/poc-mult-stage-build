package fmanuel98.com.github.profile.users.dto

import fmanuel98.com.github.profile.users.User
import fmanuel98.com.github.profile.users.roles.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class UserModel(val id: Long, val name: String, val email: String) {
    constructor(user: User) : this(user.id, user.name, user.email)
}

open class UserInput(
    @field:NotBlank val name: String, @field:Email val email: String
) {
    open fun toDomain() = User(name = name, email = email)
}

class UserWithPassword(name: String, email: String, @field:ValidPassword val password: String) :
    UserInput(name, email) {
    override fun toDomain() = super.toDomain().copy(password = password)
}

@Size(min = 5)
@NotBlank
@Target(AnnotationTarget.FIELD)
private annotation class ValidPassword