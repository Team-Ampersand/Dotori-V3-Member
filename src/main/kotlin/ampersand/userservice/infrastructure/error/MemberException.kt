package ampersand.userservice.infrastructure.error

import org.springframework.http.HttpStatus

data class MemberException(val errorMessage: String, val status: HttpStatus) : RuntimeException(errorMessage)

