package ampersand.userservice.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpMemberRequest(
    @field:NotBlank
    val memberName: String,

    @field:NotBlank
    @field:Size(min = 4, max = 4)
    val stuNum: String,

    @field:NotBlank
    val password: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val gender: String
)
