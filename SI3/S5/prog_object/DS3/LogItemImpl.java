import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LogItemImpl<V> implements LogItem<V> {
    String action;
    V value;

    public LogItemImpl(String action, V value) {
        this.action = action;
        this.value = value;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogItemImpl<?> that = (LogItemImpl<?>) o;
        return getAction().equals(that.getAction()) && getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAction(), getValue());
    }

    int sum( List<LogItem<Integer>> logs){
        return logs.stream().map(LogItem::getValue).reduce(0, Integer::sum);
    }

    //sort the logItem on their action
    public static List<LogItem> sortByAction(List<LogItem> logs){
        // create comparator
        var cmp = new Comparator<LogItem>() {
            @Override
            public int compare(LogItem o1, LogItem o2) {
                return o1.getAction().compareTo(o2.getAction());
            }
        };
        // sort the list
        logs.sort(cmp);
        return logs;
    }
}
