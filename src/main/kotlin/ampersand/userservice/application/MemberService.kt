package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo
import reactor.core.publisher.Mono

interface MemberService {
    suspend fun queryUserById(id: Long): MemberInfo
}