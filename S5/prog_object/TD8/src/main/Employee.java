public class Employee implements IndexedObject, Searchable, Cloneable {
    private final String email;
    private final int index;
    private String name;

    public Employee(String name, String email) {
        this(0, name, email);
    }

    public Employee(int index, String name, String email) {
        this.name = name;
        this.email = email;
        this.index = index;
    }

    public String getName() {
        return name == null ? "unknown" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int getIndex() {
        return index;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getIndex() == employee.getIndex();
    }

    @Override
    public int hashCode() {
        return 31 + getIndex();
    }

    @Override
    public boolean match(String query) {
        if (query == null) return false;
        return getName().contains(query) || getEmail().contains(query) || String.valueOf(getIndex()).equals(query);
    }

    @Override
    public Employee clone() {
        Employee employee;
        try {
            employee = (Employee) super.clone();
        } catch (CloneNotSupportedException e) {
            employee = new Employee(this.index, this.name, this.email);
        }
        return employee;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "email='" + email + '\'' +
                ", index=" + index + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
