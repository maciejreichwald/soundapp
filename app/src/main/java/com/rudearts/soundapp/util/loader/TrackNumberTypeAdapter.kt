package com.rudearts.soundapp.util.loader

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.rudearts.soundapp.model.external.TrackNumber

import java.lang.reflect.Type

class TrackNumberTypeAdapter : JsonDeserializer<TrackNumber>, JsonSerializer<TrackNumber> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)
        = TrackNumber(parseJson(json))

    private fun parseJson(json:JsonElement) = try {
        json.asInt.toLong()
    } catch (e: NumberFormatException) {
        0L
    }

    override fun serialize(src: TrackNumber, typeOfSrc: Type, context: JsonSerializationContext) = JsonPrimitive(0)
}