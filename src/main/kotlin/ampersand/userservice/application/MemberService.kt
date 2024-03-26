package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.application.dto.SignUpUserRequest

interface MemberService {
    suspend fun queryUserById(id: Long): MemberInfo
    fun signUp(request: SignUpUserRequest)
}