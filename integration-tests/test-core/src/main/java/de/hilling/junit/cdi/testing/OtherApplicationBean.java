package de.hilling.junit.cdi.testing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OtherApplicationBean extends BaseBean {

    @Override
    public OtherApplicationBean getOtherApplicationBean() {
        return null;
    }
}
