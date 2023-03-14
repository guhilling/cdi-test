package de.hilling.junit.cdi.jee;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@Entity
@EntityListeners(TestEntityListener.class)
@NamedQuery(name="findAllUserEntity", query = "SELECT u from UserEntity u")
public class UserEntity {

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
