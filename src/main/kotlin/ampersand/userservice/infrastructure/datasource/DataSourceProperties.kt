package ampersand.userservice.infrastructure.datasource

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.datasource")
data class DataSourceProperties(
    val dbms: String,
    val host: String,
    val port: String,
    val database: String,
    val username: String,
    val password: String,
    val showSql: Boolean,
    val formatSql: Boolean,
    val highlightSql: Boolean,
    val ddlAuto: String,
    val poolSize: Int
)