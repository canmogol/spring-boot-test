package dev.canm.sbtest.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "person")
public class Employee {

    // Specifies the primary key of an entity.
    @Id
    // 'GenerationType.AUTO' Indicates that the persistence provider should pick an appropriate strategy
    // for the particular database.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // The @Size annotated element size must be between the specified boundaries (included).
    @Size(min = 3, max = 20)
    private String name;

    private Date birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
