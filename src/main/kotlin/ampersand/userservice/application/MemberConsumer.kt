package ampersand.userservice.application

import ampersand.userservice.infrastructure.saga.MemberStatusCommandSaga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MemberConsumer(
    private val memberStatusCommandSaga: MemberStatusCommandSaga
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @KafkaListener(topics = ["apply-selfstudy"], groupId = "group-01")
    fun applySelfStudy(record: ConsumerRecord<String, String>) {
        val memberId = record.value().toLong()
        log.info("========== Apply SelfStudy MemberId {} ==========", memberId)
        GlobalScope.launch(Dispatchers.IO) {
            processApply(memberId)
        }
    }

    private suspend fun processApply(memberId: Long) {
        coroutineScope {
            launch {
                memberStatusCommandSaga.applySelfStudy(memberId)
            }
        }
    }

    @KafkaListener(topics = ["cancel-selfstudy"], groupId = "group-01")
    suspend fun cancelSelfStudy(memberId: String) {
        log.info("========== Cancel SelfStudy MemberId {} ==========", memberId)
        memberStatusCommandSaga.cancelSelfStudy(memberId.toLong())
    }

    @KafkaListener(topics = ["apply-massage"], groupId = "group-01")
    suspend fun applyMassage(memberId: String) {
        log.info("========== Apply Massage MemberId {} ==========", memberId)
        memberStatusCommandSaga.applyMassage(memberId.toLong())
    }

    @KafkaListener(topics = ["cancel-massage"], groupId = "group-01")
    suspend fun cancelMassage(memberId: String) {
        log.info("========== Cancel Massage MemberId {} ==========", memberId)
        memberStatusCommandSaga.cancelMassage(memberId.toLong())
    }

}
