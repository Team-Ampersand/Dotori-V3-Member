package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.persistence.port.MemberRepositoryPort
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MemberServiceImpl(
    private val memberRepositoryPort: MemberRepositoryPort
) : MemberService {

    override suspend fun queryUserById(id: Long): Mono<MemberInfo> {
        TODO("Not yet implemented")
    }
}