package ampersand.userservice.presentation

import ampersand.userservice.application.MemberService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class MemberHandler(
    private val memberService: MemberService,
) {

    suspend fun queryMemberById(serverRequest: ServerRequest): ServerResponse {
        val memberId = serverRequest.pathVariable("memberId")

        val response = memberService.queryUserById(memberId.toLong())

        return ServerResponse.ok().bodyValueAndAwait(response)
    }

}