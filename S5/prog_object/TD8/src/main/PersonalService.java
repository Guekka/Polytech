import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonalService extends ManagerOfIndexedElements<Employee> {
    public List<Employee> search(String query) {
        return this.values()
                .stream()
                .flatMap(list -> list.stream().filter(e -> e.match(query)))
                .distinct()
                .toList();
    }

    public Set<Employee> searchUnique(String query) {
        return this.values()
                .stream()
                .map(list -> list.stream().filter(e -> e.match(query)).findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
