package com.wespot.image.out


interface S3Port {

    fun getPresignedUrl(imageName: String, expirationTime: Long): String

    fun delete(imageUrlWithCloudFrontUrl: String)

}
