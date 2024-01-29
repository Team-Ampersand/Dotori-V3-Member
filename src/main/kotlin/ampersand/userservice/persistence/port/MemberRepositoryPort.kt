package ampersand.userservice.persistence.port

import ampersand.userservice.persistence.MemberEntity

interface MemberRepositoryPort {
    suspend fun saveMember(member: MemberEntity): MemberEntity
    suspend fun findById(id: Long): MemberEntity?
}