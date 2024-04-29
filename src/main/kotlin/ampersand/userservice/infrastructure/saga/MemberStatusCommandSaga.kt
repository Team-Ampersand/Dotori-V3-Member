package ampersand.userservice.infrastructure.saga

interface MemberStatusCommandSaga {
    fun applySelfStudy(id: Long)
    fun cancelSelfStudy(id: Long)
    fun applyMassage(id: Long)
    fun cancelMassage(id: Long)
}