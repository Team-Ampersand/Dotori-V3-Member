package ampersand.userservice.infrastructure.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    @NestedConfigurationProperty
    val accessTokenProperties: AccessTokenProperties,
    @NestedConfigurationProperty
    val refreshTokenProperties: RefreshTokenProperties
) {
    fun getAccessTokenExpirationAsHour() =
        this.accessTokenProperties.expirationAsHour.toLong()

    fun getRefreshTokenExpirationAsHour() =
        this.refreshTokenProperties.expirationAsHour.toLong()
}

interface BaseTokenProperties {
    val expirationAsHour: Int
}

data class AccessTokenProperties(
    override val expirationAsHour: Int
) : BaseTokenProperties

data class RefreshTokenProperties(
    override val expirationAsHour: Int
) : BaseTokenProperties