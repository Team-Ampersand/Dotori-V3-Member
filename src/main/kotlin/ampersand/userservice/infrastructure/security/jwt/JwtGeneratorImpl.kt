package ampersand.userservice.infrastructure.security.jwt

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jwt.JWTClaimsSet
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class JwtGeneratorImpl(
    private val jwtProperties: JwtProperties
) : JwtGenerator {

    override fun generateToken(subject: String, params: Map<String,Any>, tokenType: TokenType) {
        val expiration = if (tokenType == TokenType.ACCESS_TOKEN) {
            Timestamp.valueOf(LocalDateTime.now().plusHours(jwtProperties.getAccessTokenExpirationAsHour()))
        } else {
            Timestamp.valueOf(LocalDateTime.now().plusHours(jwtProperties.getRefreshTokenExpirationAsHour()))
        }
        val claimsBuilder = JWTClaimsSet.Builder()
            .subject(subject)
            .expirationTime(expiration)

        for((paramKey, paramValue) in params) {
            claimsBuilder.claim(paramKey, paramValue)
        }

        val header = JWSHeader.Builder(JWSAlgorithm.HS256)
            .type(JOSEObjectType.JWT)
            .build()

    }
}