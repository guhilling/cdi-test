package de.hilling.junit.cdi.beans;

import javax.annotation.Resource;

public class ResourceInjected {

    @Resource
    private Request request;

    public Request getRequest() {
        return request;
    }
}
