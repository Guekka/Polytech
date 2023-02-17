import java.util.*;
import java.util.stream.Collectors;

//Definissez bien votre entête de classe vous n'aurez qu'une seule méthode à implémenter.
//Define your class header so that you only have to implement one method.
public class Library extends ItemDictionnary<Book> {
    private final List<Book> addedBooks = new ArrayList<>();

    @Override
    public void addItem(Book book) {
        addedBooks.add(book);
        super.addItem(book);
    }

    @Override
    public boolean removeItem(Book book) {
        addedBooks.remove(book);
        return super.removeItem(book);
    }

    /**
     * qui renvoie les livres qui ont été ajoutés par addItem dans
     * la bibliothèque, dans l'ordre d'ajout dans la bibliothèque (le plus ancien en premier) et qui sont toujours présents.
     */
    public List<Book> listAddedBooks() {
        return addedBooks;
    }

    /**
     * Cette méthode recherche tous les livres qui ont le même ISBN que le livre passé en argument.
     * Si aucun livre n'est trouvé, la méthode renvoie un objet Optional contenant une copie du livre passé en argument.
     * Sinon, la méthode itère sur la liste des livres trouvés et essaie de les fusionner avec le livre passé en argument.
     * Si la fusion est réussie, le livre fusionné remplace le livre passé en argument et le processus continue.
     * Enfin, la méthode renvoie un objet Optional contenant le livre résultant de toutes les fusions.
     */
    public Optional<Book> mergeBooks(Book book) {
        var similarBooks = findItems(book);
        if (similarBooks == null || book == null)
            return Optional.ofNullable(book);

        return Optional.of(similarBooks.stream().reduce(book, (acc, newBook) -> {
            var merged = newBook.merge(acc);
            return merged.orElse(acc);
        }));
    }

    private List<Book> sortBy(Comparator<Book> cmp) {
        return values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(cmp)
                .collect(Collectors.toList());
    }

    public List<Book> sortByTitle() {
        return sortBy(Comparator.comparing(Book::getTitle));
    }

    public List<Book> sortByAuthor() {
        return sortBy(Comparator.comparing(Book::getAuthor));
    }

    public static class Main {
        /**
         * true : if we can obtain sum as a sum of integers contained in the array tab, each of these integers can be taken 0, 1 or several times
         * false : otherwise
         * Exemples:
         * sum(new int[]{5, 11, 3}, 8) -> true
         * sum(new int[]{5, 11, 3}, 13) -> true
         * sum(new int[]{4, 10, 6}, 19) -> false
         */
        public static boolean sum(int[] tab, int sum) {
            if (sum == 0)
                return true;
            if (tab.length == 0)
                return false;

            // do not use Arrays.copyOfRange
            var newTab = new int[tab.length - 1];
            System.arraycopy(tab, 1, newTab, 0, tab.length - 1);
            
            return sum(newTab, sum - tab[0]) || sum(newTab, sum);
        }

        public static void main(String[] args) {
            System.out.println(sum(new int[]{5, 11, 3}, 8));
            System.out.println(sum(new int[]{5, 11, 3}, 13));
            System.out.println(sum(new int[]{4, 10, 6}, 19));
        }
    }
}
