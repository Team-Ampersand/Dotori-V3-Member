package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo

interface MemberService {
    suspend fun queryUserById(id: Long): MemberInfo
}