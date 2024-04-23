package ampersand.userservice.infrastructure.saga

import ampersand.userservice.infrastructure.error.MemberException
import ampersand.userservice.persistence.port.MemberRepositoryPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class MemberStatusCommandSagaImpl(
    private val memberRepositoryPort: MemberRepositoryPort
) : MemberStatusCommandSaga {

    override suspend fun applySelfStudy(id: Long) {
        val member = memberRepositoryPort.findById(id)
            ?: throw MemberException("Not Found Member Exception - method [ applySelfStudy() ]", HttpStatus.NOT_FOUND)
        memberRepositoryPort.saveMember(member.applySelfStudy())
    }

    override suspend fun cancelSelfStudy(id: Long) {
        val member = memberRepositoryPort.findById(id)
            ?: throw MemberException("Not Found Member Exception - method [ cancelSelfStudy() ]", HttpStatus.NOT_FOUND)
        memberRepositoryPort.saveMember(member.cancelSelfStudy())
    }

    override suspend fun applyMassage(id: Long) {
        val member = memberRepositoryPort.findById(id)
            ?: throw MemberException("Not Found Member Exception - method [ applyMassage() ]", HttpStatus.NOT_FOUND)
        memberRepositoryPort.saveMember(member.applyMassage())
    }

    override suspend fun cancelMassage(id: Long) {
        val member = memberRepositoryPort.findById(id)
            ?: throw MemberException("Not Found Member Exception - method [ cancelMassage() ]", HttpStatus.NOT_FOUND)
        memberRepositoryPort.saveMember(member.cancelMassage())
    }

}
