package ampersand.userservice.infrastructure.error.handler

data class ErrorResponse(
    val errorMessage: String,
    val status: Int
)
