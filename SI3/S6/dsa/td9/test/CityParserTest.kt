package graphs.roadApplication

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CityParserTest {
    @Test
    fun readAllAndRemove() {
        val cp = CityParser(path + "fr.txt")
        val cities: MutableList<City> = ArrayList()
        cp.readAll(cities)
        val afterRemoving: List<City> = CityParser.removeDuplicates(cities)

        //Testing reading the cities
        Assertions.assertTrue(afterRemoving.stream().anyMatch { city: City -> city.nom == "Nice" })
        Assertions.assertTrue(afterRemoving.stream().anyMatch { city: City -> city.nom == "Valbonne" })
        Assertions.assertTrue(afterRemoving.stream().anyMatch { city: City -> city.nom == "Biot" })
        Assertions.assertFalse(afterRemoving.stream().anyMatch { city: City -> city.nom == "Sophia Antipolis" })
        Assertions.assertTrue(afterRemoving.stream().anyMatch { city: City -> city.nom == "Antibes" })
        val nice = getCity(afterRemoving, "Nice")
        val cagnes = getCity(afterRemoving, "Cagnes-sur-Mer")
        val villeneuve = getCity(afterRemoving, "Villeneuve-Loubet")
        val valbonne = getCity(afterRemoving, "Valbonne")
        val biot = getCity(afterRemoving, "Biot")
        val antibes = getCity(afterRemoving, "Antibes")
        val paris = getCity(afterRemoving, "Paris")
        val sainte_foy = getCity(afterRemoving, "Saint-Foy")
        val laMadeleine = getCity(afterRemoving, "La Madeleine")
        val Laroque = getCity(afterRemoving, "Laroque")
        val saintJean = getCity(afterRemoving, "Saint-Jean")
        val marseille = getCity(afterRemoving, "Marseille")
        val saint_raphael = getCity(afterRemoving, "Saint-Raphael")
        val toulon = getCity(afterRemoving, "Toulon")
        //   System.out.println("Saint-Philippe :" + saint_philippe);
        println("------------- VILLES --------------")
        println("Nice:$nice")
        Assertions.assertEquals(43.5, nice.latitude, 0.5)
        Assertions.assertEquals(7.25, nice.longitude, 0.5)
        println("Cagnes:$cagnes")
        println("Villeneuve:$villeneuve")
        println("Biot:$biot")
        println("Valbonne:$valbonne")
        println("Antibes:$antibes")
        println("Paris:$paris")
        println("La Madeleine :$sainte_foy")
        println("Laroque :$Laroque")
        println("Saint-Jean (Roquette sur Siagnes!:$saintJean")
        println("Saint-Foy :$sainte_foy")
        println("Marseille :$marseille")
        Assertions.assertEquals(43.0, marseille.latitude, 0.5)
        Assertions.assertEquals(5.0, marseille.longitude, 0.5)
        println("Saint-Raphael :$saint_raphael")
        Assertions.assertEquals(43.0, saint_raphael.latitude, 0.5)
        Assertions.assertEquals(6.9, saint_raphael.longitude, 0.5)
        println("Toulon :$toulon")
        Assertions.assertEquals(43.1, toulon.latitude, 0.5)
        Assertions.assertEquals(5.9, toulon.longitude, 0.5)
        println("------------- Distances --------------")
        Assertions.assertEquals(0.0, nice.distance(nice), 0.5)
        var distance = nice.distance(cagnes)
        println("De Nice à Cagnes :$distance")
        Assertions.assertEquals(11.0, distance / 1000, 2.0)
        distance = cagnes.distance(villeneuve)
        println("De Cagnes à Villeneuve :$distance")
        Assertions.assertEquals(5.0, distance / 1000, 2.0)
        println("De Villeneuve à Antibes :" + villeneuve.distance(antibes))
        println("De Antibes à Biot :" + antibes.distance(biot))
        println("De Biot à Valbonne :" + biot.distance(valbonne))
        println("De Valbonne à Biot :" + valbonne.distance(biot))
        distance = nice.distance(toulon)
        println("De Nice à Toulon :$distance")
        //Too much.... perhaps problem with the data
        Assertions.assertEquals(150.0, distance / 1000, 20.0)
        println("De Nice à Marseille :" + nice.distance(marseille))
        println("De Nice à Saint-Foy :" + nice.distance(sainte_foy))
        println("De Nice à Valbonne :" + nice.distance(valbonne))
        println("De Biot à Valbonne :" + biot.distance(valbonne))
        println("De Nice à Biot :" + nice.distance(biot))
        println("De Nice à La Madeleine :" + nice.distance(laMadeleine))
        println("De La Madeleine à Laroque :" + laMadeleine.distance(Laroque))
        println("De Valbonne à Paris :" + valbonne.distance(paris))
        println("De Nice à Paris :" + nice.distance(paris))
        println("De Nice à Marseille :" + nice.distance(marseille))
        Assertions.assertTrue(nice.distance(marseille) < 220000)
        println("De Marseille à Saint-Raphael :" + marseille.distance(saint_raphael))
        println("De Saint-Raphael à Saint-Jean :" + saint_raphael.distance(saintJean))
    }

    companion object {
        private fun getCity(cities: List<City>, cityName: String): City {
            return cities.stream().filter { city: City -> city.nom == cityName }.findFirst().orElse(null)
        }

        const val path = "resources/"
    }
}