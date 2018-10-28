package com.androidvoyage.stylabsassignment

import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Gson default date adapter converts to local time which is a problem for us
 * Hence this!
 */
class UTCDateTypeAdapter(format: String) : JsonSerializer<Date>, JsonDeserializer<Date> {
    private val dateFormat: DateFormat

    init {
        dateFormat = SimpleDateFormat(format, Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    @Synchronized
    override fun serialize(date: Date, type: Type,
                           jsonSerializationContext: JsonSerializationContext): JsonElement {
        return JsonPrimitive(dateFormat.format(date))
    }

    @Synchronized
    override fun deserialize(jsonElement: JsonElement, type: Type,
                             jsonDeserializationContext: JsonDeserializationContext): Date {
        try {
            return dateFormat.parse(jsonElement.asString)
        } catch (e: ParseException) {
            try {
                val fallbackFormat = SimpleDateFormat("yyyy-MM-dd")
                fallbackFormat.timeZone = TimeZone.getTimeZone("UTC")
                return fallbackFormat.parse(jsonElement.asString)
            } catch (fallbackException: Exception) {
            }

            throw JsonParseException(e)
        }

    }
}