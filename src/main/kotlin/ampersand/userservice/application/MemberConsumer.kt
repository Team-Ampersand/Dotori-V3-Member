package ampersand.userservice.application

import ampersand.userservice.infrastructure.saga.MemberStatusCommandSaga
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MemberConsumer(
    private val memberStatusCommandSaga: MemberStatusCommandSaga
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @KafkaListener(topics = ["apply-selfstudy"], groupId = "group-01")
    suspend fun applySelfStudy(memberId: Long) {
        log.info("========== Apply SelfStudy MemberId {} ==========", memberId)
        memberStatusCommandSaga.applySelfStudy(memberId)
    }

    @KafkaListener(topics = ["cancel-selfstudy"], groupId = "group-01")
    suspend fun cancelSelfStudy(memberId: Long) {
        log.info("========== Cancel SelfStudy MemberId {} ==========", memberId)
        memberStatusCommandSaga.cancelSelfStudy(memberId)
    }

    @KafkaListener(topics = ["apply-massage"], groupId = "group-01")
    suspend fun applyMassage(memberId: Long) {
        log.info("========== Apply Massage MemberId {} ==========", memberId)
        memberStatusCommandSaga.applyMassage(memberId)
    }

    @KafkaListener(topics = ["cancel-massage"], groupId = "group-01")
    suspend fun cancelMassage(memberId: Long) {
        log.info("========== Cancel Massage MemberId {} ==========", memberId)
        memberStatusCommandSaga.cancelMassage(memberId)
    }

}
