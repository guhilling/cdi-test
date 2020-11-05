package de.hilling.junit.cdi.jee;

import org.junit.jupiter.api.Test;

import javax.persistence.*;

@Entity
@EntityListeners(TestEntityListener.class)
public class UserEntity {

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
