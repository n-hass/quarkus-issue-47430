package au.com.me.commonapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmInline

@Serializable
public data class GpsCoordinate(
    val lat: Latitude,
    val lon: Longitude,
)

public fun GpsCoordinate.Companion.fromJson(json: String): GpsCoordinate =
    Json.decodeFromString(json)

@Serializable
@JvmInline
public value class Latitude(public val value: Double) {
    init {
        require(value in -90.0..90.0) { "value must be between -90 and 90" }
    }
}

@Serializable
@JvmInline
public value class Longitude(public val value: Double) {
    init {
        require(value in -180.0..180.0) { "value must be between -180 and 180" }
    }
}
