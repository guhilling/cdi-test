package de.hilling.junit.cdi.scopedbeans;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@ConversationScoped
public class ConversationScopedBean extends ScopedBean implements Serializable {

}