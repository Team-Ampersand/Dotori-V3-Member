package ampersand.userservice.persistence.port

import ampersand.userservice.persistence.MemberEntity
import reactor.core.publisher.Mono

interface MemberRepositoryPort {
    suspend fun saveMember(member: MemberEntity): Mono<MemberEntity>
    suspend fun findById(id: Long): Mono<MemberEntity>
}