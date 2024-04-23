package ampersand.userservice.infrastructure.saga

interface MemberStatusCommandSaga {
    suspend fun applySelfStudy(id: Long)
    suspend fun cancelSelfStudy(id: Long)
    suspend fun applyMassage(id: Long)
    suspend fun cancelMassage(id: Long)
}