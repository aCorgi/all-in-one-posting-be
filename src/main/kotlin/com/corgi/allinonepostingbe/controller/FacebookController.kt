package com.corgi.allinonepostingbe.controller

import com.corgi.allinonepostingbe.dto.FacebookPageAccountsResponse
import com.corgi.allinonepostingbe.dto.FacebookPostingRequest
import com.corgi.allinonepostingbe.service.FacebookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/facebook")
class FacebookController(
    private val facebookService: FacebookService
) {

    @GetMapping("/accounts/by-user-id/{userId}")
    fun getPageAccounts(
        @PathVariable userId: String,
        @RequestParam accessToken: String
    ): FacebookPageAccountsResponse {
        return facebookService.getPageAccounts(userId, accessToken)
    }

    @PostMapping("/post")
    fun post(
        @RequestBody facebookPostingRequest: FacebookPostingRequest
    ) {
        return facebookService.post()
    }
}
