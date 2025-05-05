package au.com.me.services.foo

import au.com.me.commonapi.GpsCoordinate
import au.com.me.commonapi.Latitude
import au.com.me.commonapi.Longitude
import au.com.me.commonapi.fromJson
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
class DefaultBeansTest {

    @Inject
    lateinit var defaultBeans: DefaultBeans

    private val coord = GpsCoordinate(
        lat = Latitude(45.0),
        lon = Longitude(90.0)
    )

    @Test
    fun `mutable store`() {

        assertNotNull(defaultBeans)

        defaultBeans.store.add(coord)

        assertEquals(1, defaultBeans.store.size)
    }

    @Test
    fun `deserialization`() {
        val json = """
            {
                "lat": 45.0,
                "lon": 90.0
            }
        """.trimIndent()
        val coord2 = GpsCoordinate.fromJson(json)
        assertEquals(coord2, defaultBeans.store.first())
    }

    @Test
    fun `serialization`() {
        val j = Json { }
        val json = """{"lat":45.0,"lon":90.0}"""
        assertEquals(json, j.encodeToString(coord))
    }
}
