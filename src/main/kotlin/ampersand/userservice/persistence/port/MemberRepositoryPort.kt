package ampersand.userservice.persistence.port

import ampersand.userservice.persistence.MemberEntity

interface MemberRepositoryPort {
    suspend fun saveMember(member: MemberEntity): MemberEntity
    suspend fun findById(id: Long): MemberEntity?
    suspend fun findAllByIds(id: List<Long>): List<MemberEntity>
    suspend fun findByEmail(email: String): MemberEntity?
    suspend fun existsByEmail(email: String): Boolean
    fun findByIdSync(id: Long): MemberEntity?
    fun saveSync(member: MemberEntity): MemberEntity
}