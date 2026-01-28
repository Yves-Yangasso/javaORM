package yang.bao.app_java_diti4.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import yang.bao.app_java_diti4.Entity.Assurance;
import yang.bao.app_java_diti4.repository.interfaceRepo.IInterface;

import java.util.List;

public class AssuranceRepository implements IInterface<Assurance> {

    private static AssuranceRepository instance;
    private EntityManager entityManager;

    public AssuranceRepository() {
        this.entityManager =
                yang.bao.app_java_diti4.utils.JpaUtil
                        .getEntityManagerFactory()
                        .createEntityManager();
    }

    // Singleton
    public static AssuranceRepository getInstance() {
        if (instance == null) {
            synchronized (AssuranceRepository.class) {
                if (instance == null) {
                    instance = new AssuranceRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void insert(Assurance assurance) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            entityManager.persist(assurance);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void update(Assurance assurance) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            entityManager.merge(assurance);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(int id) {
        Assurance assurance = findById(id);
        if (assurance == null) return;

        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            entityManager.remove(assurance);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Assurance findById(int id) {
        return entityManager.find(Assurance.class, id);
    }

    @Override
    public List<Assurance> findAll() {
        return entityManager
                .createQuery("SELECT a FROM Assurance a", Assurance.class)
                .getResultList();
    }
}
