package com.wespot.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.wespot.config.security.CustomUrlFilter
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus

@Configuration
class FirebaseConfig {

    @PostConstruct
    fun init() {
        try {
            val serviceAccount = javaClass.classLoader.getResourceAsStream("config/serviceAccountKey.json")
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        } catch (e: Exception) {
            throw CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionView.DIALOG, "Firebase APP 연결에 실패했습니다.")
        }
    }

}
