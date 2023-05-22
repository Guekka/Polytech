package graphs.roadApplication

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets

class CityParser(name: String) {
    private var currentChar = 0
    private val buffer = StringBuilder()
    private var b: BufferedReader

    init {
        b = try {
            if (name.startsWith("http://")) BufferedReader(
                InputStreamReader(
                    URL(name).openStream(), StandardCharsets.UTF_8
                )
            ) else BufferedReader(
                InputStreamReader(
                    FileInputStream(name), StandardCharsets.UTF_8
                )
            )
        } catch (e: IOException) {
            throw IllegalArgumentException("unreadable file", e)
        }
    }

    @Throws(IOException::class)
    private fun skipToEOL() {
        while (currentChar != -1 && "\r\n".indexOf(currentChar.toChar()) == -1) {
            currentChar = b.read()
        }
        while (currentChar != -1 && "\r\n".indexOf(currentChar.toChar()) != -1) currentChar = b.read()
    }

    @Throws(IOException::class)
    private fun skipNextField() {
        while ("\t\u000c".indexOf(currentChar.toChar()) == -1) {
            currentChar = b.read()
        }
        currentChar = b.read()
    }

    @Throws(IOException::class)
    private fun readDouble(): Double {
        var d = 0
        var sign = 1
        if (currentChar == '-'.code) {
            sign = -1
            currentChar = b.read()
        }
        while (currentChar <= '9'.code && currentChar >= '0'.code) {
            d = 10 * d + (currentChar - '0'.code)
            currentChar = b.read()
        }
        if (currentChar != '.'.code) {
            currentChar = b.read()
            return sign.toDouble() * d
        }
        currentChar = b.read()
        var dot = 1
        while (currentChar <= '9'.code && currentChar >= '0'.code) {
            d = 10 * d + (currentChar - '0'.code)
            dot *= 10
            currentChar = b.read()
        }
        currentChar = b.read()
        return sign * d / dot.toDouble()
    }

    @Throws(IOException::class)
    private fun readString(): String {
        buffer.setLength(0)
        // On saute les espaces mais on a aussi besoin de garder les fins de ligne (nouveau)
        while ("\t\u000c\n".indexOf(currentChar.toChar()) == -1) {
            buffer.append(currentChar.toChar())
            currentChar = b.read()
        }
        return buffer.toString()
    }

    fun readAll(cities: MutableCollection<City>) {
        try {
            skipToEOL() // On saute la ligne d entete
            do {
                skipNextField() // RC : Region font code
                skipNextField() // UFI : Unique feature identifier
                // double id = readDouble(); //UNI : Unique name identifier
                readDouble() // UNI : Unique name identifier
                val latitude = readDouble()
                val longitude = readDouble()
                skipNextField() // DMS_LAT
                skipNextField() // DMS_LONG
                skipNextField() // UTM : Universal transverse Mercator
                skipNextField() // JOG : Joint operations Graphic reference
                if (currentChar != 'P'.code) {
                    skipToEOL()
                    continue
                }
                skipNextField() // FC : Feature classification
                skipNextField() // DSG : Feature designation code
                skipNextField() // PC : Populated place classification
                skipNextField() // CC1 : Primary coutry code
                skipNextField() // DSG : Feature designation code
                skipNextField() // ADM1 : First order administrative division code
                skipNextField() // ADM2
                skipNextField() // DIM : Dimension
                skipNextField() // CC2
                skipNextField() // NT : Name type
                skipNextField() // LC : Language code
                skipNextField() // SHORT_FORM
                skipNextField() // GENERIC NAME
                skipNextField() // SHORT_NAME
                val nom = readString() // FULL_NAME
                skipNextField() //FULL_NAME_ND
                //Attention modification par rapport au fichier original pour gérer les dates
                val date = readString() // MODIFY_DATE
                skipToEOL()
                cities.add(City(nom, latitude, longitude, date))
            } while (currentChar != -1)
            b.close()
        } catch (e: IOException) {
            println("Erreur de lecture")
            try {
                b.close()
            } catch (e1: IOException) { /* nothing */
            }
            throw IllegalArgumentException("invalid file", e)
        }
    }

    companion object {
        fun removeDuplicates(cities: List<City>): List<City> {
            val map = HashMap<String, MutableList<City>>()
            for (c in cities) {
                if (!map.containsKey(c.nom)) {
                    map[c.nom] = ArrayList()
                }
                map[c.nom]?.add(c)
            }
            for ((key, list) in map) {
                if (list.size > 1) {
                    list.sortWith(Comparator.comparing { obj: City -> obj.date }.reversed())
                    map[key] = list.subList(0, 1)
                }
            }
            return map.values.stream().flatMap { obj: List<City> -> obj.stream() }.toList()
        }
    }
}
