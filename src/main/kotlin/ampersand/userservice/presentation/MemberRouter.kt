package ampersand.userservice.presentation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MemberRouter {

    @Bean
    fun memberBaseRouter(memberHandler: MemberHandler) = coRouter {
        "/member".nest {
            contentType(MediaType.APPLICATION_JSON)
            GET("/id/{memberId}", memberHandler::queryMemberById)
        }
    }
}