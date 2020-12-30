package com.jasonadam.twitterapicaller.modules

import com.google.cloud.WriteChannel
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageException
import com.google.cloud.storage.StorageOptions
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.util.logging.Logger

class GcsStorageWriter : StorageWriter {

    private val storage = StorageOptions.getDefaultInstance().service
    private val bucketName = "twitter-api"

    private fun createBlob(): BlobInfo {
        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(2)
        val blobName = "extracted_tweets_${startDate.toEpochDay()}_${endDate.toEpochDay()}.json"

        val blobId = BlobId.of(bucketName, blobName)
        val blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/json").build()

        try {
            storage.create(blobInfo)
        } catch (se: StorageException) {
            log.warning("blob ${blobInfo.name} already exists")
        }

        return blobInfo
    }

    override fun loadToStorage(s: String) {
        val blobInfo = createBlob()
        val content = s.toByteArray(StandardCharsets.UTF_8)
        val writer: WriteChannel = storage.writer(blobInfo)
        try {
            log.info("writing tweets to blob: ${blobInfo.name}")
            writer.write(ByteBuffer.wrap(content, 0, content.size))
            writer.close()
            log.info("writer closed for blob: ${blobInfo.name}")
        } catch (ioe: IOException) {
            log.severe("unable to write blob: ${blobInfo.name}")
            throw ioe
        }
    }


    companion object {
        val log: Logger = Logger.getLogger(this::class.java.simpleName)
    }
}
