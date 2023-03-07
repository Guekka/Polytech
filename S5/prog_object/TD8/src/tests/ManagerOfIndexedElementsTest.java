import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerOfIndexedElementsTest {
    @Test
    public void testAddElement() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        Employee employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        Employee employee2 = new Employee(2, "Jane Doe", "jane.doe@company.com");
        Employee employee3 = new Employee(1, "Bob Johnson", "bob.johnson@company.com");

        manager.addElement(employee1);
        manager.addElement(employee2);
        manager.addElement(employee3);

        assertEquals(2, manager.get(1).size());
        assertEquals(1, manager.get(2).size());
    }

    @Test
    public void testFindElements_itemIsNull() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        assertNull(manager.findElements(null));
    }

    @Test
    public void testFindElements_itemExists() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        Employee employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        Employee employee2 = new Employee(2, "Jane Doe", "jane.doe@company.com");
        Employee employee3 = new Employee(1, "Bob Johnson", "bob.johnson@company.com");

        manager.addElement(employee1);
        manager.addElement(employee2);
        manager.addElement(employee3);

        assertEquals(2, manager.findElements(employee1).size());
        assertEquals(1, manager.findElements(employee2).size());
    }

    @Test
    public void testFindElements_itemDoesNotExist() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        Employee employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        Employee employee2 = new Employee(2, "Jane Doe", "jane.doe@company.com");

        manager.addElement(employee1);
        manager.addElement(employee2);

        assertNull(manager.findElements(new Employee(3, "Foo Bar", "foo.bar@company.com")));
    }


    @Test
    public void testRemoveElement_elementIsNull() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        assertFalse(manager.removeElement(null));
    }

    @Test
    public void testRemoveElement_indexNotFound() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        Employee employee = new Employee(1, "John", "john@company.com");
        assertFalse(manager.removeElement(employee));
    }

    @Test
    public void testRemoveElement_elementNotFound() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        Employee employee1 = new Employee(1, "John", "john@company.com");
        Employee employee2 = new Employee(1, "Jane", "jane@company.com");
        manager.addElement(employee1);
        assertFalse(manager.removeElement(employee2));
    }

    @Test
    public void testRemoveElement_elementFound() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        Employee employee1 = new Employee(1, "John", "john@company.com");
        Employee employee2 = new Employee(1, "Jane", "jane@company.com");
        manager.addElement(employee1);
        manager.addElement(employee2);
        //before remove
        assertEquals(2, manager.get(1).size());
        assertTrue(manager.removeElement(employee2));
        //after remove
        assertEquals(1, manager.get(1).size());
        List<Employee> list = manager.get(1);
        assertTrue(list.contains(employee1));
        //due to equals we prefer to check
        assertEquals("john@company.com", list.get(0).getEmail());
    }

    @Test
    public void testRemoveElement_lastElementFound() {
        ManagerOfIndexedElements<Employee> manager = new ManagerOfIndexedElements<>();
        Employee employee2 = new Employee(1, "Jane", "jane@company.com");
        manager.addElement(employee2);
        //before remove
        assertEquals(1, manager.get(1).size());
        assertTrue(manager.removeElement(employee2));
        //after remove
        assertFalse(manager.containsKey(1));
    }
}