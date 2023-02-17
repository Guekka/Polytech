import java.util.Optional;

public class Book implements Identifiable, Matchable {
    public static final String UNKNOWN = "unknown";
    public static final String UNDEFINED = "undefined";
    private final String isbn;
    private final String author;
    private final int year;
    private String title;


    public Book(String title, String author, int year, String isbn) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
    }

    public Book(String title, String author, int year) {
        this(title, author, year, UNKNOWN);
    }

    public String getTitle() {
        if (title == null) {
            return UNDEFINED;
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        if (author == null) {
            return UNKNOWN;
        }
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book other = (Book) o;
        if (getId().equals(UNKNOWN) && other.getId().equals(UNKNOWN)) {
            return getAuthor().equals(other.getAuthor()) && getTitle().equals(other.getTitle());
        }
        return getId().equals(other.getId());
    }

    public Book copy() {
        return new Book(getTitle(), getAuthor(), getYear(), getId());
    }

    @Override
    public String getId() {
        if (isbn == null) {
            return UNKNOWN;
        }
        return isbn;
    }

    @Override
    public boolean match(String query) {
        return getTitle().contains(query) || getAuthor().contains(query)
                || getId().contains(query) || String.valueOf(getYear()).contains(query);
    }


    /**
     * Deux livres peuvent être fusionnés uniquement si
     * - aucun des deux n'est null,
     * - ET ils ont le même ISBN ou l'un des deux identifiants a pour valeur Unknown,
     * - ET ils sont de la même année,
     * si ce n'est pas le cas la méthode retourne un Optional empty
     * <p>
     * Si une fusion est possible, le nouveau livre retourné a
     * - pour ISBN, celui qui est différent de "unknown" ou "unknown" dans le cas où les deux sont inconnus;
     * - pour année, celle des deux livres puisqu'elle doit être égale;
     * - pour titre :
     * - Si le titre de l'objet courant contient déjà le titre de other ou vice-versa, le titre qui contient l'autre
     * - Si le titre de l'objet courant ou le titre de other est "undefined", le titre qui n'est pas "undefined";
     * - Si aucun de ces cas ne s'applique, le titre de l'objet courant et le titre de other sont retournés concaténés et séparés par la chaîne " or ".
     * pour auteur, la même règle s'applique que pour le titre, en prenant en compte un "unknown" au lieu d'un "undefined".
     */
    public Optional<Book> merge(Book other) {
        if (other == null) {
            return Optional.empty();
        }

        var sameYear = getYear() == other.getYear();
        var badId = !getId().equals(UNKNOWN) && !other.getId().equals(UNKNOWN) && !getId().equals(other.getId());

        if (!sameYear || badId) {
            return Optional.empty();
        }

        String isbn = getId().equals(UNKNOWN) ? other.getId() : getId();
        int year = getYear();

        String title = mergeField(getTitle(), other.getTitle(), UNDEFINED);
        String author = mergeField(getAuthor(), other.getAuthor(), UNKNOWN);

        return Optional.of(new Book(title, author, year, isbn));
    }

    private String mergeField(String lhs, String rhs, String undefined) {
        if (lhs.contains(rhs)) {
            return lhs;
        } else if (rhs.contains(lhs)) {
            return rhs;
        } else if (lhs.equals(undefined)) {
            return rhs;
        } else if (rhs.equals(undefined)) {
            return lhs;
        }
        return lhs + " or " + rhs;
    }


    /* ---- METHODES QUI VOUS SONT DONNEES -- NE PAS MODIFIER -------- */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getId().hashCode();
        result = 31 * result + this.getTitle().hashCode();
        result = 31 * result + this.getAuthor().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", year=" + getYear() +
                ", isbn='" + getId() + '\'' +
                '}';
    }
}