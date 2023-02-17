import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Un itemDictionnary permet de mémoriser les listes d'objets qui partagent le même identifiant
 * (getId()), comme par exemple les définitions différentes d'un même mot dans un dictionnaire.
 * <p>
 * Un itemDictionnary permet également de retrouver tous les items
 * qui matchent une simple String, comme rechercher toutes les définitions qui contiennent une string donnée.
 */
public class ItemDictionnary<T extends Identifiable & Matchable>
        extends HashMap<String, List<T>> {
    /**
     * ajoute l'item dans la liste associée à son identifiant.
     * Attention s'il n'existe pas encore d'Items associés à cette clef,
     * vous devez commencer par créer la liste.
     */
    public void addItem(T item) {
        var groupId = item.getId();
        putIfAbsent(groupId, new ArrayList<>());
        get(groupId).add(item);
    }

    /**
     * retire cet item du dictionnaire s'il est présent (utilisation de ==, car cette fois-ci
     * nous recherchons exactement les mêmes items; donc avoir le même identifiant ne les caractérise pas comme identiques.).
     */
    public boolean removeItem(T element) {
        if (element == null)
            return false;

        var groupId = element.getId();
        if (!containsKey(groupId))
            return false;

        boolean removed = false;
        var group = get(groupId);
        var it = group.iterator();
        while (it.hasNext()) {
            if (it.next() == element) {
                it.remove();
                removed = true;
                break;
            }
        }

        if (group.isEmpty())
            remove(groupId);

        return removed;
    }

    /**
     * renvoie la Liste des Items ayant pour identifiant celui de l'item passé en paramètre et null sinon
     */
    public List<T> findItems(T item) {
        if (item == null)
            return null;
        return getOrDefault(item.getId(), null);
    }


    /**
     * renvoie tous les items qui "matchent" la string. Elle renvoie une liste vide dans le cas où aucun item ne matche la query.
     */
    public List<T> findMatchableItems(String query) {
        return values().stream().flatMap(Collection::stream).filter(item -> item.match(query)).collect(Collectors.toList());
    }
}
