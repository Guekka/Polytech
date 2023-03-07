import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


class EmployeeTest {
    @Test
    public void testGetName() {
        Employee employee = new Employee("John Smith", "john.smith@company.com");
        assertEquals("John Smith", employee.getName());
    }

    @Test
    public void testNoName() {
        Employee employee = new Employee(null, "john.smith@company.com");
        assertEquals("unknown", employee.getName());
    }

    @Test
    public void testGetEmail() {
        Employee employee = new Employee("John Smith", "john.smith@company.com");
        assertEquals("john.smith@company.com", employee.getEmail());
    }

    @Test
    public void testGetIndexatZero() {
        Employee employee = new Employee("John Smith", "john.smith@company.com");
        assertEquals(0, employee.getIndex());
    }

    @Test
    public void testEquals() {
        Employee employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        Employee employee2 = new Employee(2, "Jane Doe", "jane.doe@company.com");
        Employee employee3 = new Employee(1, "John Smith The bigest", "john.smith@company.fr");

        assertNotEquals(employee1, employee2);
        assertEquals(employee1, employee3);
    }

    @Test
    public void testEqualsWithInterface() {
        IndexedObject employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        IndexedObject employee2 = new Employee(2, "Jane Doe", "jane.doe@company.com");
        IndexedObject employee3 = new Employee(1, "John Smith The bigest", "john.smith@company.fr");

        assertNotEquals(employee1, employee2);
        assertEquals(employee1, employee3);
    }

    @Test
    public void testHash() {
        Employee employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        Employee employee2 = new Employee(2, "Jane Doe", "jane.doe@company.com");
        Employee employee3 = new Employee(1, "John Smith the bigest", "john.smith@company.com");


        assertEquals(Objects.hash(1), employee1.hashCode());
        assertEquals(Objects.hash(2), employee2.hashCode());
        assertEquals(Objects.hash(1), employee3.hashCode());
    }

    @Test
    public void testMatch_queryIsNull() {
        Employee employee = new Employee(1, "John Smith", "john.smith@company.com");
        assertFalse(employee.match(null));
    }

    @Test
    public void testMatch_queryMatchesEmail() {
        Employee employee = new Employee(1, "John Smith", "john.smith@company.com");
        assertTrue(employee.match("smith@company.com"));
    }

    @Test
    public void testMatch_queryMatchesName() {
        Employee employee = new Employee(1, "John Smith", "john.smith@company.com");
        assertTrue(employee.match("John"));
    }

    @Test
    public void testMatch_queryMatchesIndex() {
        Employee employee = new Employee(1, "John Smith", "john.smith@company.com");
        assertTrue(employee.match("1"));
    }

    @Test
    public void testMatch_queryDoesNotMatch() {
        Employee employee = new Employee(1, "John Smith", "john.smith@company.com");
        assertFalse(employee.match("foo"));
    }

    @Test
    public void testClone() {
        Employee employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        Employee employee2 = employee1.clone();
        assertEquals(employee1.getIndex(), employee2.getIndex());
        assertEquals(employee1.getName(), employee2.getName());
        assertEquals(employee1.getEmail(), employee2.getEmail());
    }

    @Test
    public void testCloneVersusAssign() {
        Employee employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        Employee employee2 = employee1.clone();

        employee1.setName("Jane");
        assertEquals(employee1.getName(), employee1.getName());
        assertNotEquals(employee1.getName(), employee2.getName());
    }
}