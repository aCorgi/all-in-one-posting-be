package com.corgi.allinonepostingbe.configuration

import com.corgi.allinonepostingbe.properties.FacebookApiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    private val facebookApiProperties: FacebookApiProperties
) {

    @Bean
    fun facebookGraphWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(facebookApiProperties.graph.rootUrl)
            .build()
    }
}
