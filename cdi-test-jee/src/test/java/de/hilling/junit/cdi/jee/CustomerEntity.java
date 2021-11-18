package de.hilling.junit.cdi.jee;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@EntityListeners(TestEntityListener.class)
public class CustomerEntity {

    public long getId() {
        return id;
    }

    public String getName() { return name; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name = "test";

}
