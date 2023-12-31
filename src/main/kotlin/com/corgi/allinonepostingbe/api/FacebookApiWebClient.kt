package com.corgi.allinonepostingbe.api

import com.corgi.allinonepostingbe.dto.FacebookPageAccountsResponse
import com.corgi.allinonepostingbe.dto.FacebookUserAccountResponse
import com.corgi.allinonepostingbe.dto.FeedPublishingRequest
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
    fun getUserAccount(
        accessToken: String
    ): Mono<FacebookUserAccountResponse> {
        return facebookGraphWebClient
            .mutate()
            .build()
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/me")
                    .build()
            }
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono<FacebookUserAccountResponse>()
            .doOnError {
                println(it.message)
            }
    }

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

    fun publishFeed(
        pageId: String,
        accessToken: String,
        feedPublishingRequest: FeedPublishingRequest
    ): Mono<String> {
        return facebookGraphWebClient
            .mutate()
            .build()
            .post()
            .uri { uriBuilder ->
                uriBuilder.path("/$pageId/feed")
                    .build()
            }
            .header("Authorization", "Bearer $accessToken")
            .bodyValue(feedPublishingRequest)
            .retrieve()
            .bodyToMono<String>()
            .doOnError {
                println(it.message)
            }
    }
}
