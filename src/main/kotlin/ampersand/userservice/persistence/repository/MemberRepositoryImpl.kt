package ampersand.userservice.persistence.repository

import ampersand.userservice.persistence.MemberEntity
import ampersand.userservice.persistence.port.MemberRepositoryPort
import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.HibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.singleQueryOrNull
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.hibernate.reactive.mutiny.Mutiny.*
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val reactiveQueryFactory: HibernateMutinyReactiveQueryFactory,
) : MemberRepositoryPort {

    override suspend fun saveMember(member: MemberEntity): MemberEntity {

        reactiveQueryFactory.transactionWithFactory { session, _ ->
            session.persistMemberEntityConcurrently(memberEntity = member)
        }

        return member
    }

    private suspend fun Session.persistMemberEntityConcurrently(memberEntity: MemberEntity) = coroutineScope {
        launch {
            this@persistMemberEntityConcurrently.persist(memberEntity).awaitSuspending()
        }
    }

    override suspend fun findById(id: Long): MemberEntity? {
        val member = reactiveQueryFactory.withFactory { _, queryFactory ->
            queryFactory.findById(id)
        }

        return member
    }

    override suspend fun findAllByIds(ids: List<Long>): List<MemberEntity> {
        val members = reactiveQueryFactory.withFactory { _, queryFactory ->
            queryFactory.findAllByIds(ids)
        }

        return members
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

    private suspend fun ReactiveQueryFactory.findAllByIds(ids: List<Long>): List<MemberEntity> {
        return this.listQuery {
            select(entity(MemberEntity::class))
            from(entity(MemberEntity::class))
            where(
                col(MemberEntity::id).`in`(ids)
            )
        }
    }

    override suspend fun findByEmail(email: String): MemberEntity? {
        val member = reactiveQueryFactory.withFactory { _, queryFactory ->
            queryFactory.findByEmail(email)
        }

        return member
    }

    private suspend fun ReactiveQueryFactory.findByEmail(email: String): MemberEntity? {
        return this.singleQueryOrNull<MemberEntity> {
            select(entity(MemberEntity::class))
            from(entity(MemberEntity::class))
            where(
                col(MemberEntity::email).equal(email)
            )
        }
    }

    override suspend fun existsByEmail(email: String): Boolean =
        reactiveQueryFactory.withFactory { _, queryFactory ->
            queryFactory.existsByEmail(email)
        }

    private suspend fun ReactiveQueryFactory.existsByEmail(email: String): Boolean {
        val member = this.singleQueryOrNull<MemberEntity> {
            select(entity(MemberEntity::class))
            from(entity(MemberEntity::class))
            where(
                col(MemberEntity::email).equal(email)
            )
        }

        return member != null
    }


}