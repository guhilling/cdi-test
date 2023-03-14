package de.hilling.junit.cdi.jee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class CompanyService {
    @PersistenceContext(unitName = "cdi-test-local")
    private EntityManager entityManager;

    public void storeCompany(CompanyEntity companyEntity) {
        entityManager.persist(companyEntity);
    }

    public CompanyEntity loadCompany(long id) {
        return entityManager.find(CompanyEntity.class, id);
    }

}
