package ampersand.userservice.persistence.repository

import ampersand.userservice.persistence.MemberEntity
import ampersand.userservice.persistence.port.MemberRepositoryPort
import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.HibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.singleQueryOrNull
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.hibernate.reactive.mutiny.Mutiny.*
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class MemberRepositoryImpl(
    private val reactiveQueryFactory: HibernateMutinyReactiveQueryFactory,
) : MemberRepositoryPort {

    override suspend fun saveMember(member: MemberEntity): Mono<MemberEntity> {

        reactiveQueryFactory.transactionWithFactory { session, _ ->
            session.persistMemberEntityConcurrently(memberEntity = member)
        }

        return Mono.just(member)
    }

    private suspend fun Session.persistMemberEntityConcurrently(memberEntity: MemberEntity) = coroutineScope {
        launch {
            this@persistMemberEntityConcurrently.persist(memberEntity).awaitSuspending()
        }
    }

    override suspend fun findById(id: Long): Mono<MemberEntity?> {
        val member = reactiveQueryFactory.withFactory { _, queryFactory ->
            queryFactory.findById(id)
        }

        return Mono.justOrEmpty(member)
    }

    private suspend fun ReactiveQueryFactory.findById(id: Long): MemberEntity? {
        return this.singleQueryOrNull<MemberEntity> {
            select(entity(MemberEntity::class))
            from(entity(MemberEntity::class))
            where(
                col(MemberEntity::id).equal(id)
            )
        }
    }
}