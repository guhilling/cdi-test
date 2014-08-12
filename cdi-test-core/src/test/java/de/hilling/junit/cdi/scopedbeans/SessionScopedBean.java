package de.hilling.junit.cdi.scopedbeans;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class SessionScopedBean extends ScopedBean implements Serializable {
    private static final long serialVersionUID = 1L;

}