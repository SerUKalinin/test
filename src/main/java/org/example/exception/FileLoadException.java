package org.example.exception;

/**
 * Исключение, которое выбрасывается при ошибках работы с файлами
 * (например, файл не найден или ошибка при чтении/записи).
 */
public class FileLoadException extends RuntimeException {

    /**
     * Конструктор с сообщением об ошибке и причиной
     *
     * @param message описание ошибки
     * @param cause причина возникновения исключения
     */
    public FileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
