package ampersand.userservice.infrastructure.datasource

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy

class DataSourcePropertyBuilder(
    private val dataSourceProperties: DataSourceProperties
) {

    companion object {
        private const val DB_URL_PROPERTY = "javax.persistence.jdbc.url"
        private const val DB_USERNAME_PROPERTY = "javax.persistence.jdbc.user"
        private const val DB_PASSWORD_PROPERTY = "javax.persistence.jdbc.password"
        private const val DB_POOL_SIZE_PROPERTY = "hibernate.connection.pool_size"
        private const val DDL_AUTO_MODE_PROPERTY = "javax.persistence.schema-generation.database.action"
        private const val SHOW_SQL_PROPERTY = "hibernate.show_sql"
        private const val FORMAT_SQL_PROPERTY = "hibernate.format_sql"
        private const val HIGHLIGHT_SQL_PROPERTY = "hibernate.highlight_sql"
        private const val IMPLICIT_NAMING_STRATEGY = "hibernate.implicit_naming_strategy"
        private const val PHYSICAL_NAMING_STRATEGY = "hibernate.physical_naming_strategy"
    }

    fun buildEntityManagerPropertiesFromDatasourceProperties() =
        HashMap<String, String>().apply {
            put(DB_URL_PROPERTY, getDatasourceUrlFromProperties())
            put(DB_USERNAME_PROPERTY, dataSourceProperties.username)
            put(DB_PASSWORD_PROPERTY, dataSourceProperties.password)
            put(DB_POOL_SIZE_PROPERTY, dataSourceProperties.poolSize.toString())
            put(DDL_AUTO_MODE_PROPERTY, dataSourceProperties.ddlAuto)
            put(SHOW_SQL_PROPERTY, dataSourceProperties.showSql.toString())
            put(FORMAT_SQL_PROPERTY, dataSourceProperties.formatSql.toString())
            put(HIGHLIGHT_SQL_PROPERTY, dataSourceProperties.highlightSql.toString())
            put(IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy::class.java.name)
            put(PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy::class.java.name)
        }

    private fun getDatasourceUrlFromProperties() =
        "jdbc:${dataSourceProperties.dbms}://${dataSourceProperties.host}:${dataSourceProperties.port}/${dataSourceProperties.database}?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul&tinyInt1isBit=false"
}