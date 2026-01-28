package yang.bao.app_java_diti4.repository;

import jakarta.persistence.EntityManager;
import yang.bao.app_java_diti4.Entity.TypeAssurance;
import yang.bao.app_java_diti4.repository.interfaceRepo.IInterface;

import java.util.List;

public class TypeAssuranceRepository implements IInterface<TypeAssurance> {

    private EntityManager entityManager;

    // Singleton
    private static TypeAssuranceRepository instance;

    public TypeAssuranceRepository() {
        this.entityManager = yang.bao.app_java_diti4.utils.JpaUtil.getEntityManagerFactory().createEntityManager();
    }

    public static TypeAssuranceRepository getInstance() {
        if (instance == null) {
            instance = new TypeAssuranceRepository();
        }
        return instance;
    }

    @Override
    public void insert(TypeAssurance typeAssurance) {
        entityManager.getTransaction().begin();
        entityManager.persist(typeAssurance);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(TypeAssurance typeAssurance) {
        entityManager.getTransaction().begin();
        entityManager.merge(typeAssurance);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        TypeAssurance typeAssurance = findById(id);
        if (typeAssurance != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(typeAssurance);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public TypeAssurance findById(int id) {
        return entityManager.find(TypeAssurance.class, id);
    }

    @Override
    public List<TypeAssurance> findAll() {
        return entityManager.createQuery("SELECT t FROM TypeAssurance t", TypeAssurance.class)
                .getResultList();
    }
}
