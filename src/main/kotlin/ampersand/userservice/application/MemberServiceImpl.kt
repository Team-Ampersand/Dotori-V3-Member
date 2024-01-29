package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.infrastructure.error.MemberException
import ampersand.userservice.persistence.MemberEntity
import ampersand.userservice.persistence.port.MemberRepositoryPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MemberServiceImpl(
    private val memberRepositoryPort: MemberRepositoryPort
) : MemberService {

    override suspend fun queryUserById(id: Long): Mono<MemberInfo> {
        val findMember = memberRepositoryPort.findById(id)
            .switchIfEmpty(Mono.error(MemberException("member not found", HttpStatus.NOT_FOUND)))
            .map {
                mapToInfo(it)
            }

        return findMember
    }

    private fun mapToInfo(member: MemberEntity) = MemberInfo(
        id = member.id,
        name = member.name,
        email = member.email,
        grade = member.grade,
        classNum = member.classNum,
        number = member.number,
        authority = member.authority,
        profileImage = member.profileImage
    )
}