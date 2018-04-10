package de.hilling.junit.cdi.jpa;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class UserEntity extends BaseEntity implements User {

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
