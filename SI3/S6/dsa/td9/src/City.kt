package graphs.roadApplication

import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.math.sqrt

class City(
    val nom: String, // -180 < long < 180
    // -90 < lat < 90
    val latitude: Double, val longitude: Double, val date: String = "0"
) {

    override fun toString(): String {
        return "$nom\tLatitude: $latitude\tLongitude: $longitude"
    }

    private val roundLatitude: Long
        get() = (latitude * 10.0.pow(precision)).roundToLong()
    private val roundLongitude: Long
        get() = (longitude * 10.0.pow(precision)).roundToLong()

    private fun tLat(latitude: Double, latmin: Double, latmax: Double, h: Int): Int {
        return h - ((latitude - latmin) / (latmax - latmin) * h).toInt()
    }

    private fun tLong(longitude: Double, lonmin: Double, lonmax: Double, w: Int): Int {
        return ((longitude - lonmin) / (lonmax - lonmin) * w).toInt()
    }

    // distance entre deux villes en mètres
    fun distance(dest: City): Double {
        // utilise la distance ellipsoidale de vincenty
        val R = 6371000.0 // rayon de la terre
        return R * sqrt((latitude - dest.latitude).pow(2) + (longitude - dest.longitude).pow(2)) / 180.0 * Math.PI
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val city = other as City
        return city.latitude == latitude && city.longitude == longitude && city.nom == nom
    }

    override fun hashCode(): Int {
        val x = roundLatitude
        val y = roundLongitude
        return ((x * (29 * 10.0.pow(precision)) + y) % Int.MAX_VALUE).toInt()
    }

    companion object {
        const val precision = 2
    }
}
