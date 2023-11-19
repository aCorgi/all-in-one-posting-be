package com.corgi.allinonepostingbe.configuration.oauth

import org.springframework.stereotype.Component

@Component
class OAuth2UserInfoFactory {
    fun getOAuth2UserInfoByProviderType(providerType: ProviderType, attributes: Map<String, Any>): OAuth2UserInfo {
        return when (providerType) {
            ProviderType.GOOGLE -> GoogleOAuth2UserInfo(attributes)
            ProviderType.FACEBOOK -> FacebookOAuth2UserInfo(attributes)
            else -> throw IllegalArgumentException("Invalid Provider Type.")
        }
    }
}
