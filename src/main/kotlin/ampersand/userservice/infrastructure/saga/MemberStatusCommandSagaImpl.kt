package ampersand.userservice.infrastructure.saga

import ampersand.userservice.persistence.port.MemberRepositoryPort
import org.springframework.stereotype.Component

@Component
class MemberStatusCommandSagaImpl(
    private val memberRepositoryPort: MemberRepositoryPort
) : MemberStatusCommandSaga {

    override fun applySelfStudy() {

    }

    override fun cancelSelfStudy() {
        TODO("Not yet implemented")
    }

    override fun applyMassage() {
        TODO("Not yet implemented")
    }

    override fun cancelMassage() {
        TODO("Not yet implemented")
    }

}
