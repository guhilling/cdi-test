package de.hilling.junit.cdi.jpa;

import java.time.LocalDate;
import java.time.Month;

public class TestUtils {

    public UserEntity createGunnar() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Gunnar");
        userEntity.setBirthDate(LocalDate.of(1971, Month.JUNE, 15));
        return userEntity;
    }
}
