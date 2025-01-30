package com.wespot.config.security

import com.wespot.auth.service.PrincipalDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val principalDetailService: PrincipalDetailService,
    private val customUrlFilter: CustomUrlFilter,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(): AuthenticationManager? {

        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(principalDetailService)

        return ProviderManager(provider)

    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .cors { }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                it.accessDeniedHandler(jwtAccessDeniedHandler)

            }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.GET, "/").permitAll()
                    .requestMatchers(HttpMethod.POST, "/").permitAll()
                    .requestMatchers(
                        "/health", "/",
                        "/api/v1/auth/login",
                        "/api/v1/auth/signup",
                        "/api/v1/auth/reissue",
                        "/api/v1/users/backgrounds",
                        "/api/v1/users/characters",
                        "/api/v1/schools/search/**",
                        "/api/v1/schools/search",
                        "/api/v1/check-profanity",
                        "/api/v1/auth/kakao/template",
                        "/api/v1/auth/admin/login",
                        "/admin/**",
                        "/swagger-ui/index.html",
                        "/wespot/**",
                        "/v3/api-docs/**",
                        "/api/v1/image/sign-up",
                        "/api/v1/image/presigned-url"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(customUrlFilter, JwtAuthenticationFilter::class.java)

        return http.build()

    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowCredentials = true
            allowedOrigins = listOf(
                "http://localhost:8080",
            )
            allowedMethods = listOf(
                HttpMethod.POST.name(),
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
            )
            allowedHeaders = listOf("*")
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }

}
