import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ManagerOfIndexedElements<T extends IndexedObject> extends HashMap<Integer, List<T>> {
    public void addElement(T element) {
        if (element == null) return;

        if (this.containsKey(element.getIndex())) {
            this.get(element.getIndex()).add(element);
        } else {
            var list = new ArrayList<T>();
            list.add(element);
            this.put(element.getIndex(), list);
        }
    }

    public boolean removeElement(T element) {
        if (element == null) return false;

        if (this.containsKey(element.getIndex())) {
            var list = this.get(element.getIndex());
            var res = list.removeIf(t -> t == element);
            if (list.isEmpty())
                this.remove(element.getIndex());
            return res;
        }
        return false;
    }

    public Collection<T> findElements(T element) {
        if (element == null) return null;

        if (this.containsKey(element.getIndex())) {
            return this.get(element.getIndex());
        }
        return null;
    }
}