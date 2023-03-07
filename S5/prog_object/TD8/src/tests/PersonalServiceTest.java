import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonalServiceTest {
    Employee employee1;
    Employee employee2;
    Employee employee3;
    PersonalService service;

    @BeforeEach
    void setUp() {
        service = new PersonalService();
        employee1 = new Employee(1, "John Smith", "john.smith@company.com");
        employee2 = new Employee(2, "Jane Doe", "jane.doe@etu.company.com");
        employee3 = new Employee(2, "Jane Smith", "jane.doe@company.com");
        service.addElement(employee1);
        service.addElement(employee2);
        service.addElement(employee3);
    }

    @Test
    public void testSearch_queryIsNull() {
        List<Employee> searchResults = service.search(null);
        assertEquals(0, searchResults.size());
    }

    @Test
    public void testSearch_queryMatchesName() {
        List<Employee> searchResults = service.search("John");
        assertEquals(1, searchResults.size());
        assertEquals(employee1, searchResults.get(0));
    }


    @Test
    public void testSearch_queryMatchesEmail() {
        List<Employee> searchResults = service.search("jane.doe@etu.company.com");
        assertEquals(1, searchResults.size());
        assertEquals(employee2, searchResults.get(0));
    }

    @Test
    public void testSearch_queryMatchesIndex() {
        service.addElement(employee3);
        List<Employee> searchResults = service.search("2");
        assertEquals(2, searchResults.size());
        assertEquals(employee2, searchResults.get(0));
        assertEquals(employee3, searchResults.get(1));
    }

    @Test
    public void testSearch_queryDoesNotMatch() {
        List<Employee> searchResults = service.search("foo");
        assertEquals(0, searchResults.size());
    }

    @Test
    public void testSearch_queryMatchesMultipleElements() {
        service.addElement(employee3);
        List<Employee> searchResults = service.search("Jane");
        System.out.println(searchResults);
        assertEquals(2, searchResults.size());
        assertEquals(employee2, searchResults.get(0));
        assertEquals(employee3, searchResults.get(1));
    }

    @Test
    void testSearchUnique_queryMatchesMultipleEmployees() {
        Set<Employee> searchResults = service.searchUnique("Smith");
        assertEquals(2, searchResults.size());
    }

    @Test
    void testSearchUnique_queryMatchesMultipleEmployeesButSameIndex() {
        Set<Employee> searchResults = service.searchUnique("jane");
        assertEquals(1, searchResults.size());
    }
}