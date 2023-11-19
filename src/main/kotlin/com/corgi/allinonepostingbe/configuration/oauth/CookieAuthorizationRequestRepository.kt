package com.corgi.allinonepostingbe.configuration.oauth

import com.corgi.allinonepostingbe.util.Constants.COOKIE_EXPIRE_SECONDS
import com.corgi.allinonepostingbe.util.Constants.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
import com.corgi.allinonepostingbe.util.Constants.REDIRECT_URI_PARAM_COOKIE_NAME
import com.corgi.allinonepostingbe.util.Constants.REFRESH_TOKEN
import com.corgi.allinonepostingbe.util.CookieUtils
import com.nimbusds.oauth2.sdk.util.StringUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

@Component
class CookieAuthorizationRequestRepository : AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        val cookie = WebUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        return cookie?.let {
            deserializeAuthorizationRequest(cookie.value)
        }
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest, response: HttpServletResponse): OAuth2AuthorizationRequest? {
        return this.loadAuthorizationRequest(request)
    }

    override fun saveAuthorizationRequest(authorizationRequest: OAuth2AuthorizationRequest?, request: HttpServletRequest, response: HttpServletResponse) {
        authorizationRequest?.let {
            CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, serializeAuthorizationRequest(authorizationRequest), COOKIE_EXPIRE_SECONDS)

            val redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)
            if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
                CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS)
            }
        } ?: {
            CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            CookieUtils.deleteCookie(request, response, REFRESH_TOKEN)
        }
    }

    private fun serializeAuthorizationRequest(authorizationRequest: OAuth2AuthorizationRequest): String {
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
                objectOutputStream.writeObject(authorizationRequest)
                objectOutputStream.flush()

                val serializedBytes = byteArrayOutputStream.toByteArray()
                return Base64.getUrlEncoder().encodeToString(serializedBytes)
            }
        }
    }

    private fun deserializeAuthorizationRequest(cookieValue: String): OAuth2AuthorizationRequest? {
        val decodedValue = Base64.getUrlDecoder().decode(cookieValue)
        return try {
            ObjectInputStream(ByteArrayInputStream(decodedValue)).use { ois ->
                ois.readObject() as? OAuth2AuthorizationRequest
            }
        } catch (e: Exception) {
            // 예외 처리 코드 추가
            e.printStackTrace()
            null
        }
    }
}
