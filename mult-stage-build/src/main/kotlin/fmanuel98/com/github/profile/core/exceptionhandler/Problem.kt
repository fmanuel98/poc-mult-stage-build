package fmanuel98.com.github.profile.core.exceptionhandler

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime


@JsonInclude(NON_NULL)
@Schema(name = "Problema")
class Problem(
    @Schema(example = "400") val status: Int,
    @Schema(example = "https://mult-stage.ao/dados-invalidos") val type: String = "",
    @Schema(example = "Dados inválidos") val title: String = "",
    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.") val detail: String = "",
    @Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.") val userMessage: String = "",
    @Schema(example = "2022-07-15T11:21:50.902245498Z") val timestamp: OffsetDateTime = OffsetDateTime.now(),
    @Schema(description = "Lista de objetos ou campos que geraram o erro") val fields: List<Field> = emptyList()

) {

    @JsonInclude(NON_NULL)
    @Schema(name = "ObjetoProblema")
    class Field(
        @Schema(example = "nome") val name: String, @Schema(example = "O nome é inválido") val userMessage: String
    )

}