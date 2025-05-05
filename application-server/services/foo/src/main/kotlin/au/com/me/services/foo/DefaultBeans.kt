package au.com.me.services.foo

import au.com.me.commonapi.GpsCoordinate
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class DefaultBeans {
   val store: MutableList<GpsCoordinate> = mutableListOf()
}
