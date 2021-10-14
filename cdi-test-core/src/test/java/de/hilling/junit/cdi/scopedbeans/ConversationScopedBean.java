package de.hilling.junit.cdi.scopedbeans;

import java.io.Serializable;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@ConversationScoped
public class ConversationScopedBean extends ScopedBean implements Serializable {

}