package ampersand.userservice.infrastructure.security.jwt

import java.time.LocalDateTime

interface JwtGenerator {
    fun generateToken(subject: String, params: Map<String, Any>, tokenType: TokenType): String
    fun getExpiration(tokenType: TokenType): LocalDateTime
}