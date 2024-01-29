package ampersand.userservice.infrastructure.error.handler

data class ErrorResponse(
    val errorMessage: String
) {
    companion object {
        fun of(cause: Throwable): ErrorResponse =
            ErrorResponse(cause.message!!)
    }
}
