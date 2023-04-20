import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class McDoReader {

    private final List<Restaurant> restaurants;

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
    public McDoReader() throws IOException {
        String fileName = "src/main/resources/mcdonalds.csv";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            restaurants = stream.parallel().map(this::readLine).toList();
        }
    }

    /**
     * @return the number of restaurants
     */
    long nbCitiesHaveMcDo() {
        return restaurants.stream()
                .map(Restaurant::city)
                .distinct()
                .count();
    }

    /**
     * @param city the city
     * @return the list of restaurants in a city
     */
    List<Restaurant> restaurantsInACity(String city) {
        return restaurants.stream()
                .filter(r -> city.equals(r.city()))
                .toList();
    }

    /**
     * @param city the city
     * @return the number of restaurants in a city
     */
    int nbOfMcDoInACity(String city) {
        return (int) restaurants.stream()
                .filter(r -> r.city().equals(city))
                .count();
    }

    //Des problemes dans le fichier font que les états sont très faux

    /**
     * @param state the state
     * @return the number of restaurants in <code>state</code>
     */
    int nbOfMcDoInState(String state) {
        return (int) restaurants.stream()
                .filter(r -> r.state().equals(state))
                .count();
    }

    //In which city are there the most MacDo restaurants?

    /**
     * @return the name of the city with the most restaurants
     */
    String cityHasMostMcDo() {
        return restaurants.stream()
                .map(Restaurant::city)
                .collect(Collectors.groupingBy(city -> city, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey())//Could be replaced by Entry::getKey as in the previous example
                .orElse("no result");

    }


    // Show the cities with the fewest MacDow's.
    //Trying something else
    //several states have only one McDo and the states are bad identified

    /**
     * @return the name of one city with the fewest restaurants
     */
    String cityWithFewestMcDo() {
        //.peek(System.out::println)
        //.peek(System.out::println)
        return restaurants.parallelStream()
                .collect(Collectors.groupingBy(Restaurant::city, Collectors.counting()))
                .entrySet()
                .stream().min(Comparator.comparingInt(e -> e.getValue().intValue()))
                .map(Map.Entry::getKey)
                .orElse("no result");
    }


    /**
     * @return the list of cities with one restaurant in reverse alphabetical order and limited to 15
     */
    List<String> cityListWithOneMcDo() {
        return restaurants.parallelStream()
                .collect(Collectors.groupingBy(Restaurant::city, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e1.getValue().intValue() - e2.getValue().intValue())//Could be replaced by ComparingInt as in the previous example
                //.peek(System.out::println)
                .takeWhile(e -> (e.getValue()).intValue() == 1)
                .map(e -> e.getKey().toLowerCase())
                .sorted(Comparator.reverseOrder())
                .limit(15)
                .toList();
    }

    /**
     * @return the top 4 cities with the most restaurants
     */
    List<Map.Entry<String, Long>> fourCitiesWithMostMcDo() {
        return restaurants.parallelStream()
                .collect(Collectors.groupingBy(Restaurant::city, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().intValue() - e1.getValue().intValue())
                //.peek(System.out::println)
                .limit(4)
                .toList();
    }


    /**
     * @param state the state
     * @return the list of cities in <code>state </code>
     */
    private List<String> cityListInState(String state) {
        return
                restaurants.parallelStream()
                        .filter(r -> state.equals(r.state()))
                        .map(r -> r.city())
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());
    }


    public static void main(String[] args) throws IOException {
        McDoReader mcDoReader = new McDoReader();
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
