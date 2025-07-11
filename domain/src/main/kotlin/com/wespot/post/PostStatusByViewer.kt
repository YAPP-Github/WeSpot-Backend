package com.wespot.post

data class PostStatusByViewer(
    val postId: Long,
    val isViewerPushedLike: Boolean = false,
    val isViewerPushedNotification: Boolean = false,
    val isViewerPushedScrap: Boolean = false,
) {
}
