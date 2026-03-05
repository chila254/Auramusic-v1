/**
 * Auramusic Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.auramusic.app.listentogether

import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Message format for encoding/decoding
 */
enum class MessageFormat {
    JSON
}

/**
 * Codec for encoding and decoding messages in JSON format
 */
class MessageCodec(
    var format: MessageFormat = MessageFormat.JSON,
    var compressionEnabled: Boolean = false
) {
    companion object {
        private const val TAG = "MessageCodec"
        private const val COMPRESSION_THRESHOLD = 100 // Only compress if > 100 bytes
        
        /**
         * Detect message format by inspecting first byte
         */
        fun detectMessageFormat(data: ByteArray): MessageFormat {
            if (data.isEmpty()) return MessageFormat.JSON
            // JSON messages start with '{'
            if (data[0] == '{'.code.toByte()) return MessageFormat.JSON
            return MessageFormat.JSON
        }
    }
    
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    
    /**
     * Encode a message with the codec's format and compression settings
     */
    fun encode(msgType: String, payload: Any?): ByteArray {
        return encodeJson(msgType, payload)
    }
    
    /**
     * Decode a message, automatically detecting format
     */
    fun decode(data: ByteArray): Pair<String, ByteArray> {
        return decodeJson(data)
    }
    
    /**
     * Encode message as JSON
     */
    private fun encodeJson(msgType: String, payload: Any?): ByteArray {
        val msg = Message(
            type = msgType,
            payload = if (payload != null) json.encodeToJsonElement(serializer(payload), payload) else null
        )
        
        var data = json.encodeToString(msg).toByteArray()
        
        if (compressionEnabled && data.size > COMPRESSION_THRESHOLD) {
            val compressed = compressData(data)
            if (compressed.size < data.size) {
                data = compressed
            }
        }
        
        return data
    }
    
    /**
     * Decode JSON message
     */
    private fun decodeJson(data: ByteArray): Pair<String, ByteArray> {
        // Try to decompress if it looks compressed (gzip magic bytes)
        val actualData = if (compressionEnabled && data.size > 2 && 
                             data[0] == 0x1f.toByte() && data[1] == 0x8b.toByte()) {
            decompressData(data) ?: data
        } else {
            data
        }
        
        val msg = json.decodeFromString<Message>(actualData.decodeToString())
        val payloadBytes = msg.payload?.toString()?.toByteArray() ?: byteArrayOf()
        
        return Pair(msg.type, payloadBytes)
    }
    
    /**
     * Compress data using GZIP
     */
    private fun compressData(data: ByteArray): ByteArray {
        val outputStream = ByteArrayOutputStream()
        GZIPOutputStream(outputStream).use { gzip ->
            gzip.write(data)
        }
        return outputStream.toByteArray()
    }
    
    /**
     * Decompress GZIP data
     */
    private fun decompressData(data: ByteArray): ByteArray? {
        return try {
            val inputStream = ByteArrayInputStream(data)
            GZIPInputStream(inputStream).use { gzip ->
                gzip.readBytes()
            }
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Failed to decompress data")
            null
        }
    }
    
    /**
     * Decode payload from JSON
     */
    fun decodePayload(msgType: String, payloadBytes: ByteArray, format: MessageFormat): Any? {
        if (payloadBytes.isEmpty()) return null
        
        return decodeJsonPayload(msgType, payloadBytes)
    }
    
    /**
     * Decode JSON payload
     */
    private fun decodeJsonPayload(msgType: String, payloadBytes: ByteArray): Any? {
        val payloadString = payloadBytes.decodeToString()
        
        return when (msgType) {
            MessageTypes.ROOM_CREATED -> json.decodeFromString<RoomCreatedPayload>(payloadString)
            MessageTypes.JOIN_REQUEST -> json.decodeFromString<JoinRequestPayload>(payloadString)
            MessageTypes.JOIN_APPROVED -> json.decodeFromString<JoinApprovedPayload>(payloadString)
            MessageTypes.JOIN_REJECTED -> json.decodeFromString<JoinRejectedPayload>(payloadString)
            MessageTypes.USER_JOINED -> json.decodeFromString<UserJoinedPayload>(payloadString)
            MessageTypes.USER_LEFT -> json.decodeFromString<UserLeftPayload>(payloadString)
            MessageTypes.SYNC_PLAYBACK -> json.decodeFromString<PlaybackActionPayload>(payloadString)
            MessageTypes.BUFFER_WAIT -> json.decodeFromString<BufferWaitPayload>(payloadString)
            MessageTypes.BUFFER_COMPLETE -> json.decodeFromString<BufferCompletePayload>(payloadString)
            MessageTypes.ERROR -> json.decodeFromString<ErrorPayload>(payloadString)
            MessageTypes.HOST_CHANGED -> json.decodeFromString<HostChangedPayload>(payloadString)
            MessageTypes.KICKED -> json.decodeFromString<KickedPayload>(payloadString)
            MessageTypes.SYNC_STATE -> json.decodeFromString<SyncStatePayload>(payloadString)
            MessageTypes.RECONNECTED -> json.decodeFromString<ReconnectedPayload>(payloadString)
            MessageTypes.USER_RECONNECTED -> json.decodeFromString<UserReconnectedPayload>(payloadString)
            MessageTypes.USER_DISCONNECTED -> json.decodeFromString<UserDisconnectedPayload>(payloadString)
            MessageTypes.SUGGESTION_RECEIVED -> json.decodeFromString<SuggestionReceivedPayload>(payloadString)
            MessageTypes.SUGGESTION_APPROVED -> json.decodeFromString<SuggestionApprovedPayload>(payloadString)
            MessageTypes.SUGGESTION_REJECTED -> json.decodeFromString<SuggestionRejectedPayload>(payloadString)
            else -> null
        }
    }
    
    @Suppress("UNCHECKED_CAST")
    private fun <T> serializer(value: T): kotlinx.serialization.KSerializer<T> {
        return when (value) {
            is CreateRoomPayload -> CreateRoomPayload.serializer()
            is JoinRoomPayload -> JoinRoomPayload.serializer()
            is ApproveJoinPayload -> ApproveJoinPayload.serializer()
            is RejectJoinPayload -> RejectJoinPayload.serializer()
            is PlaybackActionPayload -> PlaybackActionPayload.serializer()
            is BufferReadyPayload -> BufferReadyPayload.serializer()
            is KickUserPayload -> KickUserPayload.serializer()
            is SuggestTrackPayload -> SuggestTrackPayload.serializer()
            is ApproveSuggestionPayload -> ApproveSuggestionPayload.serializer()
            is RejectSuggestionPayload -> RejectSuggestionPayload.serializer()
            is ReconnectPayload -> ReconnectPayload.serializer()
            is TransferHostPayload -> TransferHostPayload.serializer()
            else -> throw IllegalArgumentException("Unknown type: ${value!!::class.simpleName}")
        } as kotlinx.serialization.KSerializer<T>
    }
}
