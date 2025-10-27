package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.EmployeeNotFoundException;
import org.example.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для работы с сотрудниками.
 * Содержит бизнес-логику для поиска и фильтрации сотрудников.
 *
 * @author Сергей Калинин
 * @version 1.1
 */
@Slf4j
public class EmployeeService {

    /**
     * Находит сотрудника по его ID в переданном списке.
     *
     * @param id ID сотрудника
     * @param employees список сотрудников
     * @return найденный сотрудник
     * @throws EmployeeNotFoundException если сотрудник с указанным ID не найден
     */
    public Employee getEmployeeById(int id, List<Employee> employees) {
        log.info("Поиск сотрудника с ID: {}", id);

        Employee employee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Сотрудник с ID {} не найден", id);
                    return new EmployeeNotFoundException("Сотрудник с ID " + id + " не найден");
                });

        log.info("Найден сотрудник: {} {}", employee.getFirstName(), employee.getLastName());
        return employee;
    }

    /**
     * Возвращает список сотрудников, чья зарплата больше или равна переданной.
     *
     * @param targetSalary целевая зарплата
     * @param employees список сотрудников
     * @return список сотрудников с зарплатой >= targetSalary
     */
    public List<Employee> getEmployeesBySalaryGreaterThan(int targetSalary, List<Employee> employees) {
        log.info("Фильтрация сотрудников с зарплатой >= {}", targetSalary);

        List<Employee> result = employees.stream()
                .filter(e -> e.getSalary() >= targetSalary)
                .collect(Collectors.toList());

        log.info("Найдено {} сотрудников с зарплатой >= {}", result.size(), targetSalary);
        return result;
    }

    /**
     * Преобразует список сотрудников в Map (HashMap).
     * Ключ: строка "id" + id сотрудника (например, "id5").
     * Значение: объект сотрудника.
     *
     * @param employees список сотрудников
     * @return Map с сотрудниками, где ключ - строка "id" + ID
     */
    public Map<String, Employee> getEmployeeMap(List<Employee> employees) {
        log.info("Преобразование списка сотрудников ({} элементов) в Map", employees.size());

        Map<String, Employee> employeeMap = new HashMap<>();
        for (Employee employee : employees) {
            employeeMap.put("id" + employee.getId(), employee);
        }

        log.info("Создана Map с {} элементами", employeeMap.size());
        return employeeMap;
    }
}
