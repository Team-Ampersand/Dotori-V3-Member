package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.infrastructure.error.MemberException
import ampersand.userservice.persistence.MemberEntity
import ampersand.userservice.persistence.port.MemberRepositoryPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepositoryPort: MemberRepositoryPort
) : MemberService {

    override suspend fun queryUserById(id: Long): MemberInfo {
        val findMember = memberRepositoryPort.findById(id)
            ?: throw MemberException("not found member", HttpStatus.NOT_FOUND)

        return mapToInfo(findMember)
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