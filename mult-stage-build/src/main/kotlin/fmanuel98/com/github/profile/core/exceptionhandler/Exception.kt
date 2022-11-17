package fmanuel98.com.github.profile.core.exceptionhandler

open class BussinesException(val mensage: String) : RuntimeException(mensage)
class EntityNotFoundException(mensage: String) : BussinesException(mensage)