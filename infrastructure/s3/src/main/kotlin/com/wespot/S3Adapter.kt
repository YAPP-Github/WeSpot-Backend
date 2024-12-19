package com.wespot

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.wespot.image.out.S3Port
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Component
class S3Adapter(
    private val s3Client: AmazonS3,
    private val s3Presigner: S3Presigner,
    @Value("\${aws.s3.bucket}")
    private val bucket: String,
) : S3Port {

    override fun getPresignedUrl(imageName: String, expirationTime: Long): String {
        val presignRequest: PutObjectPresignRequest = getPresignedRequest(imageName, expirationTime)

        return s3Presigner.presignPutObject(presignRequest)
            .url()
            .toString()
    }

    private fun getPresignedRequest(
        prefix: String,
        expirationTime: Long
    ): PutObjectPresignRequest {
        val objectRequest: PutObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(prefix)
            .build()

        return PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(expirationTime))
            .putObjectRequest(objectRequest)
            .build()
    }

    override fun delete(imageUrlWithCloudFrontUrl: String) {
        val imageName = imageUrlWithCloudFrontUrl.substring(imageUrlWithCloudFrontUrl.lastIndexOf("/") + 1)
        s3Client.deleteObject(DeleteObjectRequest(bucket, imageName))
    }

}
