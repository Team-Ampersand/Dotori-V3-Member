package ampersand.userservice.application

import ampersand.userservice.application.dto.LoginRequest
import ampersand.userservice.application.dto.MemberIds
import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.application.dto.SignUpMemberRequest
import ampersand.userservice.infrastructure.security.jwt.TokenResponse

interface MemberService {
    suspend fun queryUserById(id: Long): MemberInfo
    suspend fun queryUsersByIds(request: MemberIds): List<MemberInfo>
    suspend fun signUp(request: SignUpMemberRequest)
    suspend fun login(request: LoginRequest): TokenResponse
}