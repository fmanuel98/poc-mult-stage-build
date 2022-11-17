package fmanuel98.com.github.profile

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "User e Upload Files", version = "1.0", description = "Esta api foi desenvolvida coma ideia de aprender sobre mult stage build, uma tecnica para reduzir o tamanho da imagens de um container"))
@SecurityScheme(name = "User e Password", scheme = "basic", type = SecuritySchemeType.HTTP)
class UserProfileApplication

fun main(args: Array<String>) {
	runApplication<UserProfileApplication>(*args)
}
