package ampersand.userservice.infrastructure.saga

interface MemberStatusCommandSaga {
    fun applySelfStudy()
    fun cancelSelfStudy()
    fun applyMassage()
    fun cancelMassage()
}