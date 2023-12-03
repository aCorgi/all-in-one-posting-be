package com.corgi.allinonepostingbe.configuration

import com.corgi.allinonepostingbe.properties.FacebookApiProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    private val facebookApiProperties: FacebookApiProperties
) {
    @Bean
    fun facebookGraphWebClient(): WebClient {
        val objectMapper = getSnakeCaseStrategyObjectMapper()

        return WebClient.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            }
            .baseUrl(facebookApiProperties.graph.rootUrl)
            .build()
    }

    private fun getSnakeCaseStrategyObjectMapper(): ObjectMapper {
        return jacksonMapperBuilder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .build()
    }
}
