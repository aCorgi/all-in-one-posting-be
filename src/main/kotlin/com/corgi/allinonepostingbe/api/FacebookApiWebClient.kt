package com.corgi.allinonepostingbe.api

import com.corgi.allinonepostingbe.dto.FacebookPageAccountsResponse
import com.corgi.allinonepostingbe.properties.FacebookApiProperties
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class FacebookApiWebClient(
    private val facebookGraphWebClient: WebClient,
    private val facebookApiProperties: FacebookApiProperties
) {
    fun getPageAccounts(
        userId: String,
        accessToken: String
    ): Mono<FacebookPageAccountsResponse> {
        return facebookGraphWebClient
            .mutate()
            .build()
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/$userId/accounts")
//                    .queryParam("state", "${UUID.randomUUID()}")
                    .build()
            }
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono<FacebookPageAccountsResponse>()
            .doOnError {
                println(it.message)
            }
    }
}
