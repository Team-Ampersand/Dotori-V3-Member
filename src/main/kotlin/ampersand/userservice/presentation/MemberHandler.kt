package ampersand.userservice.presentation

import ampersand.userservice.application.MemberService
import ampersand.userservice.application.dto.LoginRequest
import ampersand.userservice.application.dto.SignUpMemberRequest
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

@Component
class MemberHandler(
    private val memberService: MemberService,
) {

    suspend fun queryMemberById(serverRequest: ServerRequest): ServerResponse {
        val memberId = serverRequest.pathVariable("memberId")

        val response = memberService.queryUserById(memberId.toLong())

        return ServerResponse.ok().bodyValueAndAwait(response)
    }

    suspend fun signUp(serverRequest: ServerRequest): ServerResponse {
        val requestBody = serverRequest.bodyToMono<SignUpMemberRequest>().awaitSingle()

        memberService.signUp(requestBody)

        return ServerResponse.created(URI("/v2/auth")).buildAndAwait()
    }

    suspend fun login(serverRequest: ServerRequest): ServerResponse {
        val requestBody = serverRequest.bodyToMono<LoginRequest>().awaitSingle()

        val response = memberService.login(requestBody)

        return ServerResponse.ok().bodyValueAndAwait(response)
    }

}