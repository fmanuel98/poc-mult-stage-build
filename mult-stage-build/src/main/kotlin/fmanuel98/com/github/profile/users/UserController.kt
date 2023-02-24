package fmanuel98.com.github.profile.users

import fmanuel98.com.github.profile.users.dto.UserInput
import fmanuel98.com.github.profile.users.dto.UserModel
import fmanuel98.com.github.profile.users.dto.UserWithPassword
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
class UserControlle(private val users: Users, private val service: UserService) {
    @GetMapping
    @Operation(summary = "Lista os Usuarios")
    fun list() = users.findAll().map { UserModel(it) }

    @Operation(summary = "Busca um usuario por Id")
    @GetMapping("/{userId}")
    fun findOne(@PathVariable userId: Long) = service.run {
        this.findUser(userId)
    }.let { UserModel(it) }

    @PostMapping
    @Operation(
        summary = "Cadastra um usuario",
        description = "Cadastro de um usuario, necessita de um email e um nome válido"
    )
    fun save(@RequestBody user: UserWithPassword) = user.run {
        service.save(this.toDomain())
    }.let { UserModel(it) }

    @PutMapping("/{userId}")
    @Operation(summary = "Atualizado um usuario por ID")
    fun update(@RequestBody @Valid userInput: UserInput, @PathVariable userId: Long) = userInput.run {
        val user = this.toDomain().apply { id = userId }
        users.save(user)
    }.let { UserModel(it) }

}
