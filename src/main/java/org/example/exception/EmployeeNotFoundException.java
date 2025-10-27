package org.example.exception;

/**
 * Исключение, которое выбрасывается при попытке найти сотрудника по ID, если он не существует.
 */
public class EmployeeNotFoundException extends RuntimeException {

    /**
     * Конструктор с сообщением об ошибке
     *
     * @param message описание ошибки
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
