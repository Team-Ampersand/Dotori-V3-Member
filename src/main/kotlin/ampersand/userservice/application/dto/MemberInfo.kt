package ampersand.userservice.application.dto

import ampersand.userservice.persistence.Authority
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class MemberInfo(
    val id: Long,

    val name: String,

    val email: String,

    val grade: Int?,

    val classNum: Int?,

    val number: Int?,

    @Enumerated(EnumType.STRING)
    val authority: Authority,

    val profileImage: String?
)
