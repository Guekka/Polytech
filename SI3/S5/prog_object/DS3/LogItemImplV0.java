import java.util.Objects;

public class LogItemImplV0 implements LogItemV0{
    String action;
    String value;

    public LogItemImplV0(String action, String value) {
        this.action = action;
        this.value = value;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogItemImplV0 that = (LogItemImplV0) o;
        return getAction().equals(that.getAction()) && getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAction(), getValue());
    }
}
