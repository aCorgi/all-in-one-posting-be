package com.corgi.allinonepostingbe.dto

data class JwtDto(
        val grantType: String,
        val accessToken: String,
        val refreshToken: String,
        val accessTokenExpiresIn: Long
)