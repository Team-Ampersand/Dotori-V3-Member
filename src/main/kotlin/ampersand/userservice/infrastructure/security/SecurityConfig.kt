package ampersand.userservice.infrastructure.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()


    @Bean
    protected fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .httpBasic { httpBasic -> httpBasic.disable() }
            .formLogin { formLogin -> formLogin.disable() }
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .authorizeExchange { auth ->
                auth
                    .anyExchange().permitAll()
            }
            .build()
    }
}
