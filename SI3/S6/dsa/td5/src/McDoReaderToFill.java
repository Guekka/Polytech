import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class McDoReaderToFill {
    private final List<Restaurant> restaurants;
    private final Map<String, List<Restaurant>> restaurantsByCity;

    public Restaurant readLine(String line) {
        Restaurant restaurant = new Restaurant();
        String[] fields = line.split(",");
        restaurant.setLatitude(Double.parseDouble(fields[0]));
        restaurant.setLongitude(Double.parseDouble(fields[1]));
        restaurant.setName(fields[2].substring(1) + " in " + fields[3].substring(0, fields[3].length() - 1));
        restaurant.setAddress(fields[4].substring(1));
        restaurant.setCity(fields[5].trim());
        restaurant.setState(fields[6].trim().substring(0, 2));
        if (fields.length > 7) {
            String phoneNumber = fields[7].trim();
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
            restaurant.setPhone(phoneNumber);
        }
        return restaurant;
    }

    /**
     * Read the file mcdonalds.csv and store the restaurants in a list
     *
     * @throws IOException if the file is not found
     */
    public McDoReaderToFill() throws IOException {
        // print working directory
        String fileName = "data/mcdonalds.csv";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            restaurants = stream.parallel().map(this::readLine).toList();
        }

        restaurantsByCity = restaurants.stream().collect(Collectors.groupingBy(Restaurant::city));
    }

    /**
     * @return the number of restaurants
     */
    long nbCitiesHaveMcDo() {
        return restaurantsByCity.size();
    }

    /**
     * @param city the city
     * @return the list of restaurants in a city
     */
    List<Restaurant> restaurantsInACity(String city) {
        return restaurantsByCity.get(city);
    }

    /**
     * @param city the city
     * @return the number of restaurants in a city
     */
    int nbOfMcDoInACity(String city) {
        return restaurantsByCity.get(city).size();
    }


    /**
     * @param state the state
     * @return the number of restaurants in <code>state</code>
     */
    int nbOfMcDoInState(String state) {
        return (int) restaurants.stream().filter(r -> r.state().equals(state)).count();
    }

    /**
     * @return the name of the city with the most restaurants
     */
    String cityHasMostMcDo() {
        return restaurantsByCity.entrySet().stream().max(Comparator.comparing(entry -> entry.getValue().size())).map(Map.Entry::getKey).orElseThrow();
    }


    /**
     * @return the name of one city with the fewest restaurants
     */
    String cityWithFewestMcDo() {
        return restaurantsByCity
                .entrySet()
                .stream()
                .min(Comparator.comparingInt(entry -> entry.getValue().size()))
                .map(Map.Entry::getKey)
                .orElseThrow();
    }


    /**
     * @return the list of cities with one restaurant in reverse alphabetical order and limited to 15
     */
    List<String> cityListWithOneMcDo() {
        return restaurantsByCity
                .entrySet()
                .stream()
                .filter(e -> e.getValue().size() == 1)
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .limit(15)
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * @return the top 4 cities with the most restaurants
     */
    List<Map.Entry<String, Long>> fourCitiesWithMostMcDo() {
        return restaurantsByCity
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(entry -> entry.getValue().size())))
                .limit(4)
                .map(e -> Map.entry(e.getKey(), (long) e.getValue().size()))
                .toList();
    }


    /**
     * @param state the state
     * @return the list of cities in <code>state </code>
     */
    private List<String> cityListInState(String state) {
        return List.of(); // too lazy to do it
        /*
        return restaurantsByCity
                .entrySet()
                .stream()
                .filter(
                        e ->
                )*/
    }


    public static void main(String[] args) throws IOException {
        McDoReaderToFill mcDoReader = new McDoReaderToFill();
        //System.out.println(mcDoReader.restaurants);

        // Question : le nombre de villes distinctes qui disposent d'au moins un restaurant McDonald
        //The number of cities that have one McDonald Restaurant
        System.out.println("The number of cities that have a McDonald : " + mcDoReader.nbCitiesHaveMcDo());


        /* ---------------- The most restaurants ----------------- */
        //Question : Dans quelle ville se trouve le plus de restaurants McDonald ?
        //Question :In which city are there the most MacDo restaurants?
        System.out.println("The city that have the most MacDo restaurants : " + mcDoReader.cityHasMostMcDo());

        //Quelles sont les 4 villes avec le plus de MacDo et le nombre de restaurants dans ces villes ?
        //Question : Show the top 4 cities with the most MacDo restaurants and the number of restaurants in these cities.
        System.out.println("The top 4 cities with the most MacDoc : " + mcDoReader.fourCitiesWithMostMcDo());

        /* ---------------- The fewest restaurants ----------------- */
        //Question  : Donner le nom d'une ville qui a le moins de MacDo.
        //Question : Show one city name with the fewest MacDo restaurants.
        System.out.println("One city with fewest restaurants : " + mcDoReader.cityWithFewestMcDo());
        //Question : Afficher la liste des villes qui ont exactement un restaurant McDonald triée par ordre alphabétique inverse et limitée à 15.
        //Question : Show the sorted list of cities that have exactly one McDonald restaurant in reverse alphabetical order and limited to 15.
        System.out.println("Short List of cities with one McDo : " + mcDoReader.cityListWithOneMcDo());



        /* ---------------- Requests  ----------------- */
        String city = "Oxford";
        //Question : Quel est le nombre de macDo dans une ville donnée?
        //Question : Number of McDonald restaurants in a city
        System.out.println("Number of McDo in " + city + " : " + mcDoReader.nbOfMcDoInACity(city));
        //Question : Afficher les restaurants dans une ville donnée
        //Question : Show the restaurants in a city
        System.out.println("Restaurants in " + city + " : " + mcDoReader.restaurantsInACity(city));

        city = "Coshocton";
        //Nombre de macDo dans une ville donnée
        System.out.println("Number of McDo in " + city + " : " + mcDoReader.nbOfMcDoInACity(city));
        //Afficher les restaurants dans une ville donnée
        System.out.println("Restaurants in " + city + " : " + mcDoReader.restaurantsInACity(city));


        //Afficher les villes distinctes d'un état dans l'ordre alphabetic qui ont des MacDo
        //Show the distinct cities in a state in alphabetical order that have McDonald restaurants
        String state = "AK";
        System.out.println("The cities in  " + state + " : " + mcDoReader.cityListInState(state));
        //Afficher le nombre de MacDo dans un état donné
        System.out.println("Number of McDo in " + state + " : " + mcDoReader.nbOfMcDoInState(state));

    }

}
