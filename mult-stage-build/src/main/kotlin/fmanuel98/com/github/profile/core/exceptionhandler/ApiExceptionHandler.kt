package fmanuel98.com.github.profile.core.exceptionhandler

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime

@ControllerAdvice
class ApiExceptionHandler(private val messageSource: MessageSource) : ResponseEntityExceptionHandler() {
    val MSG_ERRO_GENERICA_USUARIO_FINAL =
        ("Ocorreu um erro interno inesperado no sistema. Tente novamente e se " + "o problema persistir, entre em contato com o administrador do sistema.")



    private fun handleValidationInternal(
        ex: Exception, headers: HttpHeaders, status: HttpStatus, request: WebRequest, bindingResult: BindingResult
    ): ResponseEntity<Any> {
        val problemType: ProblemType = ProblemType.DADOS_INVALIDOS
        val detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."
        val problemObjects = bindingResult.allErrors.map { objectError: ObjectError ->
            val message: String = messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
            var name = objectError.objectName
            if (objectError is FieldError) name = objectError.field
            Problem.Field(name, message)
        }
        val problem = createProblemBuilder(
            status = status, problemType = problemType, detail = detail, userMessage = detail, objects = problemObjects
        )
        //return handleExceptionInternal(ex, problem, headers, status, request)
        return super.handleExceptionInternal(ex,problem,headers,status,request)!!
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntidadeNaoEncontrada(
        ex: EntityNotFoundException, request: WebRequest
    ): ResponseEntity<*>? {
        val status: HttpStatus = HttpStatus.NOT_FOUND
        val problemType: ProblemType = ProblemType.RECURSO_NAO_ENCONTRADO
        val detail: String = ex.mensage
        val problem = createProblemBuilder(status = status, problemType = problemType, detail = detail)
        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }

    @ExceptionHandler(BussinesException::class)
    fun handleNegocio(ex: BussinesException, request: WebRequest): ResponseEntity<*>? {
        val status: HttpStatus = HttpStatus.BAD_REQUEST
        val problemType: ProblemType = ProblemType.ERRO_NEGOCIO
        val detail: String = ex.mensage
        val problem = createProblemBuilder(status = status, problemType = problemType, detail = detail)
        return handleExceptionInternal(ex, problem, HttpHeaders(), status, request)
    }



    private fun createProblemBuilder(
        status: HttpStatus,
        problemType: ProblemType,
        detail: String,
        userMessage: String = "",
        objects: List<Problem.Field> = emptyList()
    ) = Problem(
        timestamp = OffsetDateTime.now(),
        status = status.value(),
        type = problemType.uri,
        title = problemType.title,
        detail = detail,
        fields = objects,
        userMessage = userMessage
    )

}