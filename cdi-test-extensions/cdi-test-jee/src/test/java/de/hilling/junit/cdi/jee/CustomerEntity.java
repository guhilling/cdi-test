package de.hilling.junit.cdi.jee;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
