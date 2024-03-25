package ampersand.userservice.configuration

import ampersand.userservice.infrastructure.datasource.DataSourceProperties
import ampersand.userservice.infrastructure.security.jwt.JwtProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = [DataSourceProperties::class, JwtProperties::class])
class PropertiesScanConfig
