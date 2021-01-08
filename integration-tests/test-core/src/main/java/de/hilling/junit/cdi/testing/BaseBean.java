package de.hilling.junit.cdi.testing;

public abstract class BaseBean {

    private String attribute;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public abstract OtherApplicationBean getOtherApplicationBean();
}
