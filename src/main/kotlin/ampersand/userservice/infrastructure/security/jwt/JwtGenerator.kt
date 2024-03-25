package ampersand.userservice.infrastructure.security.jwt

interface JwtGenerator {
    fun generateToken(subject: String, params: Map<String, Any>, tokenType: TokenType)
}