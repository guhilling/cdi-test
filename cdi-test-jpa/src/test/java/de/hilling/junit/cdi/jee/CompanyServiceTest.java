package de.hilling.junit.cdi.jee;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.hilling.junit.cdi.CdiTestJunitExtension;

@ExtendWith(CdiTestJunitExtension.class)
class CompanyServiceTest {

    @PersistenceContext(unitName = "cdi-test-local")
    private EntityManager entityManager;

    @Inject
    private CompanyService companyService;

    @Test
    void storeCompany() {
        CompanyEntity company = new CompanyEntity();
        companyService.storeCompany(company);
        assertNotNull(companyService.loadCompany(company.getId()));
    }

    @Test
    void storeCompanyNotCommited() {
        CompanyEntity company = new CompanyEntity();
        companyService.storeCompany(company);
        assertFalse(entityManager.getTransaction().isActive());
    }
}
