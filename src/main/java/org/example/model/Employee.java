package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель сотрудника
 * Содержит информацию о сотруднике: id, имя, фамилия, зарплата.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    /** Уникальный идентификатор */
    private int id;

    /** Имя */
    private String firstName;

    /** Фамилия */
    private String lastName;

    /** Зарплата */
    private int salary;
}
