package com.corgi.allinonepostingbe.service

import com.corgi.allinonepostingbe.configuration.oauth.OAuth2UserInfo
import com.corgi.allinonepostingbe.configuration.oauth.OAuth2UserInfoFactory
import com.corgi.allinonepostingbe.configuration.oauth.ProviderType
import com.corgi.allinonepostingbe.configuration.oauth.UserPrincipal
import com.corgi.allinonepostingbe.util.logger
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class Oauth2Service(
    private val oAuth2UserInfoFactory: OAuth2UserInfoFactory
) : DefaultOAuth2UserService() {
    val log = logger()

    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val user = super.loadUser(userRequest)
        log.info("on loadUser function")

        return processToAuthenticationUser(
            userRequest = userRequest,
            user = user
        )
    }

    private fun processToAuthenticationUser(userRequest: OAuth2UserRequest, user: OAuth2User): UserPrincipal {
        val providerType: ProviderType = ProviderType.valueOf(userRequest.clientRegistration.registrationId.uppercase())
        val oAuth2UserInfo: OAuth2UserInfo = oAuth2UserInfoFactory.getOAuth2UserInfoByProviderType(providerType, user.attributes)

        val userAuthorities = user.authorities
//        val authorities: List<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER"))

        val userPrincipal = UserPrincipal(
            attributes = emptyMap(),
            accessToken = userRequest.accessToken.tokenValue,
            authorities = userAuthorities,
            id = oAuth2UserInfo.id,
            name = oAuth2UserInfo.name
        )

        return userPrincipal
    }
}
