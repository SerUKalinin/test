package service;

import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
        employees = List.of(
                new Employee(1, "Иван", "Петров", 50000),
                new Employee(2, "Мария", "Иванова", 75000),
                new Employee(3, "Алексей", "Сидоров", 60000)
        );
    }

    @Test
    @DisplayName("Поиск сотрудника по ID — найден сотрудник")
    void testGetEmployeeById_found() {
        Employee emp = employeeService.getEmployeeById(2, employees);
        assertNotNull(emp);
        assertEquals(2, emp.getId());
        assertEquals("Мария", emp.getFirstName());
    }

    @Test
    @DisplayName("Поиск сотрудника по ID — сотрудник не найден, выбрасывается исключение")
    void testGetEmployeeById_notFound() {
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(999, employees));
    }

    @Test
    @DisplayName("Фильтрация сотрудников по зарплате — есть совпадения")
    void testGetEmployeesBySalaryGreaterThan() {
        List<Employee> highEarners = employeeService.getEmployeesBySalaryGreaterThan(70000, employees);
        assertEquals(1, highEarners.size());
        assertEquals("Мария", highEarners.get(0).getFirstName());
    }

    @Test
    @DisplayName("Фильтрация сотрудников по зарплате — нет совпадений")
    void testGetEmployeesBySalaryGreaterThan_noMatches() {
        List<Employee> none = employeeService.getEmployeesBySalaryGreaterThan(80000, employees);
        assertTrue(none.isEmpty());
    }
}
