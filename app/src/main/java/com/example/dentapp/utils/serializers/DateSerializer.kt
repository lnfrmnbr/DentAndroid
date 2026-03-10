package com.example.dentapp.utils.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(formatter.format(value))
    }

    override fun deserialize(decoder: Decoder): Date {
        return formatter.parse(decoder.decodeString())!!
    }
}