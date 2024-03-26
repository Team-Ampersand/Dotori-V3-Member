package ampersand.userservice.infrastructure.security.jwt

import ampersand.userservice.persistence.Authority
import java.time.LocalDateTime

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: LocalDateTime,
    val roles: List<Authority>
)
