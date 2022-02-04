package de.hilling.junit.cdi.testing;

public class ProducedBean {
    private final String name;

    public ProducedBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
