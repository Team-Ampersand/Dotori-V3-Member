package ampersand.userservice.application.dto

import jakarta.validation.constraints.NotEmpty

data class MemberIds(
    @field:NotEmpty
    val ids: List<Long>
)
