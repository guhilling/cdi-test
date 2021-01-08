package de.hilling.junit.cdi.testing;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationBeanWithResource extends BaseBean {

    @Resource
    private OtherApplicationBean otherApplicationBean;

    @Override
    public OtherApplicationBean getOtherApplicationBean() {
        return otherApplicationBean;
    }
}
