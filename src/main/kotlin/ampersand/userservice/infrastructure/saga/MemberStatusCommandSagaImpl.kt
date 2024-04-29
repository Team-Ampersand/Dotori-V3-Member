package ampersand.userservice.infrastructure.saga

import ampersand.userservice.infrastructure.error.MemberException
import ampersand.userservice.persistence.port.MemberRepositoryPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class MemberStatusCommandSagaImpl(
    private val memberRepositoryPort: MemberRepositoryPort
) : MemberStatusCommandSaga {

    override fun applySelfStudy(id: Long) {
        val member = (memberRepositoryPort.findByIdSync(id)
            ?: throw MemberException("Not Found Member Exception - method [ applySelfStudy() ]",HttpStatus.NOT_FOUND))
        memberRepositoryPort.saveSync(member.applySelfStudy())
    }

    override fun cancelSelfStudy(id: Long) {
        val member = memberRepositoryPort.findByIdSync(id)
            ?: throw MemberException("Not Found Member Exception - method [ cancelSelfStudy() ]", HttpStatus.NOT_FOUND)
        memberRepositoryPort.saveSync(member.cancelSelfStudy())
    }

    override fun applyMassage(id: Long) {
        val member = memberRepositoryPort.findByIdSync(id)
            ?: throw MemberException("Not Found Member Exception - method [ applyMassage() ]", HttpStatus.NOT_FOUND)
        memberRepositoryPort.saveSync(member.applyMassage())
    }

    override fun cancelMassage(id: Long) {
        val member = memberRepositoryPort.findByIdSync(id)
            ?: throw MemberException("Not Found Member Exception - method [ cancelMassage() ]", HttpStatus.NOT_FOUND)
        memberRepositoryPort.saveSync(member.cancelMassage())
    }

}
