package service;

import org.example.exception.FileLoadException;
import org.example.model.Employee;
import org.example.service.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;
    private List<Employee> employees;
    private static final String TEST_FILE = "test_employees.txt";

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        employees = List.of(
                new Employee(1, "Иван", "Петров", 50000),
                new Employee(2, "Мария", "Иванова", 75000),
                new Employee(3, "Алексей", "Сидоров", 60000)
        );
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) file.delete();
    }

    @Test
    @DisplayName("Сохранение и загрузка сотрудников из файла")
    void testSaveAndLoadEmployees() {
        fileService.saveEmployeesToFile(employees, TEST_FILE);
        List<Employee> loaded = fileService.loadEmployeesFromFile(TEST_FILE);

        assertEquals(employees.size(), loaded.size());
        assertEquals(employees.get(0).getFirstName(), loaded.get(0).getFirstName());
        assertEquals(employees.get(1).getLastName(), loaded.get(1).getLastName());
    }

    @Test
    @DisplayName("Попытка загрузки несуществующего файла должна выбросить FileLoadException")
    void testLoadNonExistentFile() {
        assertThrows(FileLoadException.class,
                () -> fileService.loadEmployeesFromFile("nonexistent_file.txt"));
    }

    @Test
    @DisplayName("Загрузка файла с некорректными строками — пропуск некорректных данных")
    void testLoadFileWithInvalidLines() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write("1,Иван,Петров,50000\n"); // корректная
            writer.write("2,Мария,Иванова,not_a_number\n"); // некорректная зарплата
            writer.write("invalid,line\n"); // некорректный формат
        }

        List<Employee> loaded = fileService.loadEmployeesFromFile(TEST_FILE);

        // Должен загрузить только одну корректную строку
        assertEquals(1, loaded.size());
        assertEquals("Иван", loaded.get(0).getFirstName());
    }
}
