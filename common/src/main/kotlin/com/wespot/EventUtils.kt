package com.wespot

import org.springframework.context.ApplicationEventPublisher

class EventUtils(applicationEventPublisher: ApplicationEventPublisher) {

    init {
        EventUtils.applicationEventPublisher = applicationEventPublisher
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
