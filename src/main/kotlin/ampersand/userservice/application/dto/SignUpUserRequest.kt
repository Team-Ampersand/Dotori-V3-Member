package ampersand.userservice.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpUserRequest(
    @field:NotBlank
    val memberName: String,

    @field:NotBlank
    val stuNum: String,

    @field:NotBlank
    @field:Size(min = 4, max = 4)
    val password: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val gender: String
)
