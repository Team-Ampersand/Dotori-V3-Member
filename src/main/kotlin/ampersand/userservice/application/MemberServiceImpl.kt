package ampersand.userservice.application

import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.application.dto.SignUpMemberRequest
import ampersand.userservice.infrastructure.error.MemberException
import ampersand.userservice.persistence.Authority
import ampersand.userservice.persistence.MemberEntity
import ampersand.userservice.persistence.port.MemberRepositoryPort
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepositoryPort: MemberRepositoryPort,
    private val passwordEncoder: BCryptPasswordEncoder
) : MemberService {

    override suspend fun queryUserById(id: Long): MemberInfo {
        val findMember = memberRepositoryPort.findById(id)
            ?: throw MemberException("not found member", HttpStatus.NOT_FOUND)

        return mapToInfo(findMember)
    }

    override suspend fun signUp(request: SignUpMemberRequest) {
        val isExists = memberRepositoryPort.existsByEmail(request.email)

        if(isExists){
            throw MemberException("already exists member", HttpStatus.ALREADY_REPORTED)
        }

        // TODO Email 인증 검사

        memberRepositoryPort.saveMember(
            MemberEntity(
                name = request.memberName,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                grade = request.stuNum.take(1).toInt(),
                classNum = request.stuNum.substring(1, 2).toInt(),
                number = request.stuNum.takeLast(2).toInt(),
                profileImage = "",
                authority = Authority.ROLE_MEMBER
            )
        )
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