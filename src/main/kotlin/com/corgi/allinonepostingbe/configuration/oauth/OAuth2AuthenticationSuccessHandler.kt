package com.corgi.allinonepostingbe.configuration.oauth

import com.corgi.allinonepostingbe.exception.BadRequestException
import com.corgi.allinonepostingbe.util.Constants.REDIRECT_URI_PARAM_COOKIE_NAME
import com.corgi.allinonepostingbe.util.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.web.util.WebUtils
import java.util.*

@Component
class OAuth2AuthenticationSuccessHandler(
    private val oAuth2UserInfoFactory: OAuth2UserInfoFactory
) : SimpleUrlAuthenticationSuccessHandler() {
    val log = logger()

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authentication: Authentication) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            log.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

//        clearAuthenticationAttributes(request)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): String {
        val redirectUri = WebUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)?.value
            ?: throw BadRequestException("redirect cookie uri 가 존재하지 않습니다.")

        val userPrincipal = authentication.principal as UserPrincipal

        return UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("accessToken", userPrincipal.username)
            .build().toUriString()
    }
}
