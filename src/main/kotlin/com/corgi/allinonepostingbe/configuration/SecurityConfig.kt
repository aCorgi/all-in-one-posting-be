package com.corgi.allinonepostingbe.configuration

import com.corgi.allinonepostingbe.configuration.oauth.CookieAuthorizationRequestRepository
import com.corgi.allinonepostingbe.configuration.oauth.OAuth2AuthenticationSuccessHandler
import com.corgi.allinonepostingbe.service.Oauth2Service
import com.corgi.allinonepostingbe.util.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
// @EnableMethodSecurity
@EnableWebSecurity
class SecurityConfig(
    private val oauth2Service: Oauth2Service,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val cookieAuthorizationRequestRepository: CookieAuthorizationRequestRepository
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors {
                it.configurationSource(corsConfigurationSource())
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .formLogin { it.disable() }
            .oauth2Login { oauth2LoginConfigurer ->
                oauth2LoginConfigurer
                    .authorizationEndpoint {
                        it.logger()
                        it.baseUri("/oauth2/authorization")
                            .authorizationRequestRepository(cookieAuthorizationRequestRepository)
                    }
                    .redirectionEndpoint {
                        it.logger()
                        it.baseUri("/*/oauth2/code/*")
                    }
                    .userInfoEndpoint {
                        it.logger()
                        it.userService(oauth2Service)
                    }
                    .successHandler(oAuth2AuthenticationSuccessHandler)
            }
            .httpBasic { it.disable() }
            .headers { headerConfigurer ->
                headerConfigurer.frameOptions { it.disable() }
            }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers("/oauth2/**").permitAll()
                    .anyRequest().authenticated()
            }

        return http.build()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration().apply {
            addAllowedOriginPattern("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
            allowCredentials = true
        }

        return UrlBasedCorsConfigurationSource()
            .apply {
                registerCorsConfiguration("/**", corsConfig)
            }
    }
}
