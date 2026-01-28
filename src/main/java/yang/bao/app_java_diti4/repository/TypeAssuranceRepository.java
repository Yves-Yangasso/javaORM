package yang.bao.app_java_diti4.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import yang.bao.app_java_diti4.Entity.TypeAssurance;
import yang.bao.app_java_diti4.repository.interfaceRepo.IInterface;

import java.util.List;

public class TypeAssuranceRepository implements IInterface<TypeAssurance> {

    private static TypeAssuranceRepository instance;
    private EntityManager entityManager;

    public TypeAssuranceRepository() {
        this.entityManager =
                yang.bao.app_java_diti4.utils.JpaUtil
                        .getEntityManagerFactory()
                        .createEntityManager();
    }

    // Singleton
    public static TypeAssuranceRepository getInstance() {
        if (instance == null) {
            synchronized (TypeAssuranceRepository.class) {
                if (instance == null) {
                    instance = new TypeAssuranceRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public void insert(TypeAssurance typeAssurance) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            entityManager.persist(typeAssurance);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void update(TypeAssurance typeAssurance) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            entityManager.merge(typeAssurance);
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
        TypeAssurance typeAssurance = findById(id);
        if (typeAssurance == null) return;

        EntityTransaction tx = entityManager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }
            entityManager.remove(typeAssurance);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public TypeAssurance findById(int id) {
        return entityManager.find(TypeAssurance.class, id);
    }

    @Override
    public List<TypeAssurance> findAll() {
        return entityManager
                .createQuery("SELECT t FROM TypeAssurance t", TypeAssurance.class)
                .getResultList();
    }
}
