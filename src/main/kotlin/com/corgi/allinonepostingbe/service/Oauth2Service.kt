package com.corgi.allinonepostingbe.service

import com.corgi.allinonepostingbe.util.logger
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class Oauth2Service: DefaultOAuth2UserService() {
    val log = logger()

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user = super.loadUser(userRequest)
//        log.info("on loadUser function")
        val authorities = user.authorities
        val accessToken = userRequest?.accessToken
        return user
    }

//    private fun process(userRequest: OAuth2UserRequest, user: OAuth2User): OAuth2User {
//        val providerType: ProviderType = ProviderType.valueOf(userRequest.clientRegistration.registrationId.uppercase(Locale.getDefault()))
//        val userInfo: OAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.attributes)
//        var savedUser: User? = userRepository.findByUserId(userInfo.getId())
//        if (savedUser != null) {
//            if (providerType !== savedUser.getProviderType()) {
//                throw OAuthProviderMissMatchException(
//                        ("Looks like you're signed up with " + providerType +
//                                " account. Please use your " + savedUser.getProviderType()).toString() + " account to login."
//                )
//            }
//            updateUser(savedUser, userInfo)
//        } else {
//            savedUser = createUser(userInfo, providerType)
//        }
//        return UserPrincipal.create(savedUser, user.attributes)
//    }
}