package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.FileLoadException;
import org.example.model.Employee;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с файлами сотрудников.
 * Предоставляет методы для сохранения и загрузки сотрудников в/из файла.
 * <p>
 * Обработка ошибок:
 * - Если файл не найден, выбрасывается FileLoadException.
 * - Если строка некорректна, она пропускается, а ошибка логируется.
 * </p>
 *
 * @author Сергей Калинин
 * @version 1.1
 */
@Slf4j
public class FileService {

    /**
     * Сохраняет список сотрудников в текстовый файл.
     *
     * @param employees список сотрудников
     * @param filename имя файла для сохранения
     */
    public void saveEmployeesToFile(List<Employee> employees, String filename) {
        log.info("Начало сохранения {} сотрудников в файл '{}'", employees.size(), filename);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {

            for (Employee employee : employees) {
                writer.write(String.format("%d,%s,%s,%d",
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary()));
                writer.newLine();
            }

            log.info("Список сотрудников успешно сохранён в файл: {}", filename);

        } catch (IOException e) {
            log.error("Ошибка при сохранении файла '{}': {}", filename, e.getMessage(), e);
            throw new FileLoadException("Ошибка при сохранении файла '" + filename + "'", e);
        }
    }

    /**
     * Загружает список сотрудников из текстового файла.
     *
     * @param filename имя файла для загрузки
     * @return список сотрудников
     * @throws FileLoadException если файл не найден или произошла ошибка при чтении
     */
    public List<Employee> loadEmployeesFromFile(String filename) {
        log.info("Загрузка сотрудников из файла '{}'", filename);
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] parts = line.split(",");
                    if (parts.length != 4) {
                        log.warn("Строка {} пропущена: неверный формат данных — {}", lineNumber, line);
                        continue;
                    }

                    int id = Integer.parseInt(parts[0].trim());
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    int salary = Integer.parseInt(parts[3].trim());

                    employees.add(new Employee(id, firstName, lastName, salary));

                } catch (NumberFormatException e) {
                    log.warn("Строка {} пропущена: некорректный формат числа — {}", lineNumber, line);
                }
            }

            log.info("Загружено сотрудников: {}", employees.size());
            return employees;

        } catch (FileNotFoundException e) {
            log.error("Файл '{}' не найден", filename);
            throw new FileLoadException("Файл '" + filename + "' не найден", e);
        } catch (IOException e) {
            log.error("Ошибка при чтении файла '{}': {}", filename, e.getMessage(), e);
            throw new FileLoadException("Ошибка при чтении файла '" + filename + "'", e);
        }
    }
}
