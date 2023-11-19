package com.corgi.allinonepostingbe.configuration.oauth

abstract class OAuth2UserInfo(protected var attributes: Map<String, Any>) {
    abstract val id: String
    abstract val name: String
    abstract val email: String
    abstract val imageUrl: String?
}

class FacebookOAuth2UserInfo(
    attributes: Map<String, Any>
) : OAuth2UserInfo(attributes) {
    override val id: String
        get() = attributes["id"] as String

    override val name: String
        get() = attributes["name"] as String

    override val email: String
        get() = attributes["email"] as String

    override val imageUrl: String?
        get() = attributes["imageUrl"] as String?
}

class GoogleOAuth2UserInfo(
    attributes: Map<String, Any>
) : OAuth2UserInfo(attributes) {
    override val id: String
        get() = attributes["sub"] as String

    override val name: String
        get() = attributes["name"] as String

    override val email: String
        get() = attributes["email"] as String

    override val imageUrl: String?
        get() = attributes["picture"] as String?
}
