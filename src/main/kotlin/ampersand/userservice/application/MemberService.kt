package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.application.dto.SignUpMemberRequest

interface MemberService {
    suspend fun queryUserById(id: Long): MemberInfo
    suspend fun signUp(request: SignUpMemberRequest)
}