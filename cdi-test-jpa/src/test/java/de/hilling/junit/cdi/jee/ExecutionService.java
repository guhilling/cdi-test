package de.hilling.junit.cdi.jee;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.function.Function;

@RequestScoped
public class ExecutionService {

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceContext(unitName = "cdi-test-localcopy")
    private EntityManager entityManagerLocal;

    @Transactional(Transactional.TxType.NEVER)
    public <T> T executeTxNever(Function<EntityManager, T> function) {
        return function.apply(entityManager);
    }

    @Transactional(Transactional.TxType.NEVER)
    public <T> T executeTxNeverLocal(Function<EntityManager, T> function) {
        return function.apply(entityManagerLocal);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public <T> T executeRequiresNew(Function<EntityManager, T> function) {
        return function.apply(entityManager);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public <T> T executeRequiresNewLocal(Function<EntityManager, T> function) {
        return function.apply(entityManagerLocal);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public <T> T executeRequired(Function<EntityManager, T> function) {
        return function.apply(entityManager);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public <T> T executeRequiredLocal(Function<EntityManager, T> function) {
        return function.apply(entityManagerLocal);
    }

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public <T> T executeNotSupported(Function<EntityManager, T> function) {
        return function.apply(entityManager);
    }

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public <T> T executeNotSupportedLocal(Function<EntityManager, T> function) {
        return function.apply(entityManagerLocal);
    }

}
