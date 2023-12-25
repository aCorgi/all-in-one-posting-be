package com.corgi.allinonepostingbe.dto

data class FeedPublishingRequest(
    val accessToken: String,
    val pageId: String,
    val message: String,
    val link: String?,
    val published: Boolean = true
)
