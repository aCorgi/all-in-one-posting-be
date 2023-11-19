package com.corgi.allinonepostingbe.configuration.oauth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class UserPrincipal(
    private val attributes: Map<String, Any>,
    private val authorities: MutableCollection<out GrantedAuthority>,
    private val id: String,
    private val name: String,
    private val accessToken: String
) : OAuth2User, UserDetails {
    override fun getName(): String = name

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun getPassword(): String {
        return id
    }

    override fun getUsername(): String {
        return accessToken
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
