package ampersand.userservice.persistence

data class RefreshTokenEntity(
    val token: String,
    val memberId: Long
)
