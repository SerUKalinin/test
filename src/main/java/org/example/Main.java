package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.EmployeeNotFoundException;
import org.example.exception.FileLoadException;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.example.service.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Главный класс приложения для демонстрации работы с сотрудниками.
 * <p>
 * Показывает работу сервисов EmployeeService и FileService:
 * поиск сотрудников по ID, фильтрацию по зарплате, преобразование в Map,
 * сохранение и загрузку из файла, а также обработку исключений.
 * </p>
 *
 * @author Сергей Калинин
 * @version 1.0
 */
@Slf4j
public class Main {

    /**
     * Точка входа в приложение.
     * <p>
     * Создаёт тестовых сотрудников, демонстрирует различные операции:
     * поиск, фильтрацию, сохранение/загрузку из файла и обработку исключений.
     * </p>
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        log.info("=== Демонстрация работы приложения управления сотрудниками ===");

        EmployeeService employeeService = new EmployeeService();
        FileService fileService = new FileService();

        List<Employee> employees = createTestEmployees();

        log.info("1. Список всех сотрудников:");
        employees.forEach(e -> log.info("  {}", e));

        demonstrateSearchById(employeeService, employees);
        demonstrateSalaryFilter(employeeService, employees);
        demonstrateEmployeeMap(employeeService, employees);
        demonstrateSaveToFile(fileService, employees);
        demonstrateLoadFromFile(fileService);
        demonstrateExceptionHandling(employeeService, fileService, employees);

        log.info("=== Демонстрация завершена ===");
    }

    /**
     * Создаёт список тестовых сотрудников для демонстрации.
     *
     * @return список сотрудников
     */
    private static List<Employee> createTestEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Иван", "Петров", 50000));
        employees.add(new Employee(2, "Мария", "Иванова", 75000));
        employees.add(new Employee(3, "Алексей", "Сидоров", 60000));
        employees.add(new Employee(4, "Елена", "Козлова", 85000));
        employees.add(new Employee(5, "Дмитрий", "Новиков", 45000));
        employees.add(new Employee(6, "Ольга", "Морозова", 70000));
        employees.add(new Employee(7, "Павел", "Волков", 55000));
        return employees;
    }

    /**
     * Демонстрирует поиск сотрудника по ID и обработку исключения, если сотрудник не найден.
     *
     * @param employeeService сервис для работы с сотрудниками
     * @param employees список сотрудников
     */
    private static void demonstrateSearchById(EmployeeService employeeService, List<Employee> employees) {
        try {
            Employee found = employeeService.getEmployeeById(3, employees);
            log.info("Найден сотрудник с ID 3: {}", found);
        } catch (EmployeeNotFoundException e) {
            log.warn("Ошибка: {}", e.getMessage());
        }

        try {
            employeeService.getEmployeeById(999, employees);
        } catch (EmployeeNotFoundException e) {
            log.warn("✓ Обработано исключение: {}", e.getMessage());
        }
    }

    /**
     * Демонстрирует фильтрацию сотрудников по минимальной зарплате.
     *
     * @param employeeService сервис для работы с сотрудниками
     * @param employees список сотрудников
     */
    private static void demonstrateSalaryFilter(EmployeeService employeeService, List<Employee> employees) {
        List<Employee> filtered = employeeService.getEmployeesBySalaryGreaterThan(65000, employees);
        log.info("Сотрудники с зарплатой >= 65000:");
        filtered.forEach(e -> log.info("  {}", e));
    }

    /**
     * Демонстрирует преобразование списка сотрудников в Map с ключом "id" + ID.
     *
     * @param employeeService сервис для работы с сотрудниками
     * @param employees список сотрудников
     */
    private static void demonstrateEmployeeMap(EmployeeService employeeService, List<Employee> employees) {
        Map<String, Employee> employeeMap = employeeService.getEmployeeMap(employees);
        log.info("Преобразованный список в Map:");
        employeeMap.forEach((key, value) -> log.info("  {} -> {}", key, value));
    }

    /**
     * Демонстрирует сохранение списка сотрудников в файл.
     *
     * @param fileService сервис для работы с файлами
     * @param employees список сотрудников для сохранения
     */
    private static void demonstrateSaveToFile(FileService fileService, List<Employee> employees) {
        fileService.saveEmployeesToFile(employees, "employees.txt");
    }

    /**
     * Демонстрирует загрузку сотрудников из файла и обработку исключений.
     *
     * @param fileService сервис для работы с файлами
     */
    private static void demonstrateLoadFromFile(FileService fileService) {
        try {
            List<Employee> loaded = fileService.loadEmployeesFromFile("employees.txt");
            log.info("Загружено {} сотрудников:", loaded.size());
            loaded.forEach(e -> log.info("  {}", e));
        } catch (FileLoadException e) {
            log.error("Ошибка загрузки: {}", e.getMessage());
        }
    }

    /**
     * Демонстрирует обработку ошибки при попытке загрузки несуществующего файла.
     *
     * @param employeeService сервис для работы с сотрудниками
     * @param fileService сервис для работы с файлами
     * @param employees список сотрудников
     */
    private static void demonstrateExceptionHandling(EmployeeService employeeService,
                                                     FileService fileService,
                                                     List<Employee> employees) {
        try {
            fileService.loadEmployeesFromFile("nonexistent.txt");
        } catch (FileLoadException e) {
            log.error("✓ Обработано исключение: {}", e.getMessage());
        }
    }
}
