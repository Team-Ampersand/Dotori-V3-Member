package ampersand.userservice.configuration

import ampersand.userservice.infrastructure.datasource.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = [DataSourceProperties::class])
class PropertiesScanConfig
