package ampersand.userservice.application

import ampersand.userservice.application.dto.LoginRequest
import ampersand.userservice.application.dto.MemberIds
import ampersand.userservice.application.dto.MemberInfo
import ampersand.userservice.application.dto.SignUpMemberRequest
import ampersand.userservice.infrastructure.error.MemberException
import ampersand.userservice.infrastructure.security.jwt.JwtGenerator
import ampersand.userservice.infrastructure.security.jwt.TokenResponse
import ampersand.userservice.infrastructure.security.jwt.TokenType
import ampersand.userservice.persistence.Authority
import ampersand.userservice.persistence.MassageStatus
import ampersand.userservice.persistence.MemberEntity
import ampersand.userservice.persistence.RefreshTokenEntity
import ampersand.userservice.persistence.SelfStudyStatus
import ampersand.userservice.persistence.port.MemberRepositoryPort
import ampersand.userservice.persistence.port.RefreshTokenRepositoryPort
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import kotlin.collections.HashMap

@Service
class MemberServiceImpl(
    private val memberRepositoryPort: MemberRepositoryPort,
    private val refreshTokenRepositoryPort: RefreshTokenRepositoryPort,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtGenerator: JwtGenerator
) : MemberService {

    override suspend fun queryUserById(id: Long): MemberInfo {
        val findMember = memberRepositoryPort.findById(id)
            ?: throw MemberException("not found member", HttpStatus.NOT_FOUND)

        return mapToInfo(findMember)
    }

    override suspend fun queryUsersByIds(request: MemberIds): List<MemberInfo> {
        val findMembers = memberRepositoryPort.findAllByIds(request.ids)

        return findMembers.map { mapToInfo(it) }
    }

    override suspend fun signUp(request: SignUpMemberRequest) {
        val isExists = memberRepositoryPort.existsByEmail(request.email)

        if(isExists){
            throw MemberException("already exists member", HttpStatus.ALREADY_REPORTED)
        }

        // TODO Email 인증 검증

        memberRepositoryPort.saveMember(
            MemberEntity(
                name = request.memberName,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                grade = request.stuNum.take(1).toInt(),
                classNum = request.stuNum.substring(1, 2).toInt(),
                number = request.stuNum.takeLast(2).toInt(),
                profileImage = null,
                authority = Authority.ROLE_MEMBER,
                selfStudyStatus = SelfStudyStatus.CAN,
                massageStatus = MassageStatus.CAN
            )
        )
    }

    override suspend fun login(request: LoginRequest): TokenResponse {
        val member = memberRepositoryPort.findByEmail(request.email)
            ?: throw MemberException("Not Found Member Exception", HttpStatus.NOT_FOUND)

        if(!passwordEncoder.matches(request.password, member.password))
            throw MemberException("password not matched.", HttpStatus.BAD_REQUEST)

        return getTokenResponse(member)
    }

    private suspend fun getTokenResponse(member: MemberEntity): TokenResponse {
        val accessToken = jwtGenerator.generateToken(member.id.toString(), buildParams(member), TokenType.ACCESS_TOKEN)
        val expiresAt = jwtGenerator.getExpiration(TokenType.ACCESS_TOKEN)
        val refreshToken = saveRefreshToken(member, buildParams(member))

        return TokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken.token,
            expiresAt = expiresAt,
            roles = mutableListOf(member.authority)
        )
    }

    private suspend fun buildParams(member: MemberEntity): MutableMap<String, Any> {
        return HashMap<String, Any>()
            .apply {
                put("authority", member.authority)
            }
    }

    private suspend fun saveRefreshToken(member: MemberEntity, params: Map<String, Any>): RefreshTokenEntity {
        val refreshToken = jwtGenerator.generateToken(member.id.toString(), params, TokenType.REFRESH_TOKEN)

        return refreshTokenRepositoryPort.saveRefreshToken(
            RefreshTokenEntity(
                token = refreshToken,
                memberId = member.id
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
        profileImage = member.profileImage,
        selfStudyStatus = member.selfStudyStatus,
        massageStatus = member.massageStatus
    )
}