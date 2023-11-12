package com.corgi.allinonepostingbe.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableMethodSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors {
                it.configurationSource(corsConfigurationSource())
            }
            .formLogin { it.disable() }
            .oauth2Login {
                it.loginProcessingUrl("/oauth2/authorization")
                it.defaultSuccessUrl("/login/oauth2/code/*")
//                it.redirectionEndpoint()
//                it.successHandler()
            }
            .httpBasic { it.disable() }
            .headers { headerConfigurer ->
                headerConfigurer.frameOptions { it.disable() }
            }
            .authorizeHttpRequests { requests ->
                requests.requestMatchers("/v1/login/**").permitAll()
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