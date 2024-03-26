package ampersand.userservice.persistence.port

import ampersand.userservice.persistence.RefreshTokenEntity

interface RefreshTokenRepositoryPort {
    suspend fun saveRefreshToken(refreshToken: RefreshTokenEntity): RefreshTokenEntity
    suspend fun findByRefreshToken(refreshToken: String): RefreshTokenEntity?
    suspend fun delete(refreshToken: RefreshTokenEntity)
}