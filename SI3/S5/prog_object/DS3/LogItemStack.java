public class LogItemStack<V> extends ArrayStack<LogItem<V>> {

    boolean lastAction(String action) {
        try {
            return peek().getAction().equals(action);
        } catch (EmptyStackException e) {
            return false;
        }
    }
}