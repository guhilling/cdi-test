package de.hilling.junit.cdi.beans;

import javax.annotation.Resource;

public class ResourceConstructorInjected {

    private  Request request;

    @Resource
    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
