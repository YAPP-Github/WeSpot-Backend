package com.wespot.comment.vo

data class PostCommentContent(
    val content: String
) {

    init {

        require(content.isNotBlank()) { "Content must not be blank." }

        require(content.length >= CONTENT_LENGTH_LOWER_BOUND_INCLUSIVE) {
            "Content must be longer than $CONTENT_LENGTH_LOWER_BOUND_INCLUSIVE characters."
        }

    }

    companion object {

        private const val CONTENT_LENGTH_LOWER_BOUND_INCLUSIVE = 1

        fun from(content: String): PostCommentContent {
            return PostCommentContent(content.trim())
        }

    }

}
