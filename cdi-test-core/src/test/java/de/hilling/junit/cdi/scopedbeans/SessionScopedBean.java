package de.hilling.junit.cdi.scopedbeans;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;

@SessionScoped
public class SessionScopedBean extends ScopedBean implements Serializable {
    private static final long serialVersionUID = 1L;

}