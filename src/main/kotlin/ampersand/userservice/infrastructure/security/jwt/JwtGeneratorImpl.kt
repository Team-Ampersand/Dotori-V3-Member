package ampersand.userservice.infrastructure.security.jwt

import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class JwtGeneratorImpl(
    private val jwtProperties: JwtProperties
) : JwtGenerator {

    override fun generateToken(subject: String, params: Map<String,Any>, tokenType: TokenType): String {
        val expiration = Timestamp.valueOf(getExpiration(tokenType))

        val claimsBuilder = JWTClaimsSet.Builder()
            .subject(subject)
            .expirationTime(expiration)

        for((paramKey, paramValue) in params) {
            claimsBuilder.claim(paramKey, paramValue)
        }

        val header = JWSHeader.Builder(JWSAlgorithm.HS256)
            .type(JOSEObjectType.JWT)
            .build()

        val signer: JWSSigner = MACSigner(jwtProperties.secretKey)
        val signedJwt = SignedJWT(header,claimsBuilder.build())
        signedJwt.sign(signer)

        return signedJwt.serialize()
    }

    override fun getExpiration(tokenType: TokenType): LocalDateTime = if (tokenType == TokenType.ACCESS_TOKEN) {
        LocalDateTime.now().plusHours(jwtProperties.getAccessTokenExpirationAsHour())
    } else {
        LocalDateTime.now().plusHours(jwtProperties.getRefreshTokenExpirationAsHour())
    }
}