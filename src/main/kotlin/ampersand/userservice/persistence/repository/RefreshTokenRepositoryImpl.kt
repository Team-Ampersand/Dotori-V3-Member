package ampersand.userservice.persistence.repository

import ampersand.userservice.infrastructure.error.MemberException
import ampersand.userservice.infrastructure.security.jwt.JwtProperties
import ampersand.userservice.persistence.RefreshTokenEntity
import ampersand.userservice.persistence.port.RefreshTokenRepositoryPort
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RefreshTokenRepositoryImpl(
    private val reactiveRedisOperations: ReactiveRedisOperations<String, Any>,
    private val jwtProperties: JwtProperties,
    private val objectMapper: ObjectMapper
) : RefreshTokenRepositoryPort {

    override suspend fun saveRefreshToken(refreshToken: RefreshTokenEntity): RefreshTokenEntity {
        val refreshTokenExpiration = Duration.ofHours(jwtProperties.refreshTokenProperties.expirationAsHour.toLong())

        val isSuccess = reactiveRedisOperations.opsForValue()
            .set(refreshToken.token, refreshToken, refreshTokenExpiration).awaitSingle()

        if(!isSuccess) {
            throw MemberException("Refresh token save failed!!", HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return refreshToken
    }

    override suspend fun findByRefreshToken(refreshToken: String): RefreshTokenEntity? {
        val redisToken = reactiveRedisOperations.opsForValue().get(refreshToken).awaitSingleOrNull()

        return redisToken?.let<Any, RefreshTokenEntity> { objectMapper.convertValue(it) }
    }

    override suspend fun delete(refreshToken: RefreshTokenEntity) {
        reactiveRedisOperations.delete(refreshToken.token).awaitSingle()
    }
}