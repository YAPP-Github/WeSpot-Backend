package com.wespot

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventUtils private constructor(applicationEventPublisher: ApplicationEventPublisher) {

    init {
        Companion.applicationEventPublisher = applicationEventPublisher
    }

    companion object {

        private var applicationEventPublisher: ApplicationEventPublisher? = null

        fun publish(event: Any) {
            if (applicationEventPublisher == null) {
                return
            }
            applicationEventPublisher!!.publishEvent(event)
        }

    }

}
