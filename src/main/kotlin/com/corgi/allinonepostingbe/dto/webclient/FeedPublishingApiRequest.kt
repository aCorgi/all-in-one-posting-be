package com.corgi.allinonepostingbe.dto.webclient

data class FeedPublishingApiRequest(
    val message: String,
    val link: String?,
    val published: Boolean = true
)
