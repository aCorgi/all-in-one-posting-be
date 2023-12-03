package com.corgi.allinonepostingbe.service

import com.corgi.allinonepostingbe.api.FacebookApiWebClient
import com.corgi.allinonepostingbe.dto.FacebookPageAccountsResponse
import com.corgi.allinonepostingbe.dto.FacebookUserAccountResponse
import com.corgi.allinonepostingbe.exception.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class FacebookService(
    private val facebookApiWebClient: FacebookApiWebClient
) {

    fun getUserAccount(
        accessToken: String
    ): FacebookUserAccountResponse {
        return facebookApiWebClient.getUserAccount(accessToken)
            .blockOptional()
            .orElseThrow { ResourceNotFoundException("사용자 계정을 가져올 수 없습니다.") }
    }

    fun getPageAccounts(
        userId: String,
        accessToken: String
    ): FacebookPageAccountsResponse {
        return facebookApiWebClient.getPageAccounts(userId, accessToken)
            .blockOptional()
            .orElseThrow { ResourceNotFoundException("페이지 계정을 가져올 수 없습니다.") }
    }

    fun post() {
    }
}
