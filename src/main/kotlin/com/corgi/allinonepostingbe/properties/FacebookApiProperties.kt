package com.corgi.allinonepostingbe.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("api.facebook")
data class FacebookApiProperties(
    val graph: FacebookGraph,
    val client: FacebookClient
) {
    data class FacebookGraph(
        val rootUrl: String
    )

    data class FacebookClient(
        val appId: String,
        val appSecret: String
    )
}
