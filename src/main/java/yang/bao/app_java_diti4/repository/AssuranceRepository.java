package yang.bao.app_java_diti4.repository;

import jakarta.persistence.EntityManager;
import yang.bao.app_java_diti4.Entity.Assurance;
import yang.bao.app_java_diti4.repository.interfaceRepo.IInterface;

import java.util.List;

public class AssuranceRepository implements IInterface<Assurance> {

    private static AssuranceRepository instance;
    private EntityManager entityManager;

    public AssuranceRepository() {
        this.entityManager = yang.bao.app_java_diti4.utils.JpaUtil.getEntityManagerFactory().createEntityManager();
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
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.persist(assurance);
        entityManager.getTransaction().commit();
    }


    @Override
    public void update(Assurance assurance) {
        entityManager.getTransaction().begin(); // démarrage une transaction
        entityManager.merge(assurance);         // update
        entityManager.getTransaction().commit(); // persister la transaction
    }

    @Override
    public void delete(int id) {
        Assurance assurance = findById(id);
        if (assurance != null) {
            entityManager.getTransaction().begin(); // démarrage une transaction
            entityManager.remove(assurance);       // delete
            entityManager.getTransaction().commit(); // persister la transaction
        }
    }

    @Override
    public Assurance findById(int id) {
        return entityManager.find(Assurance.class, id);
    }

    @Override
    public List<Assurance> findAll() {
        return entityManager.createQuery("SELECT a FROM Assurance a", Assurance.class).getResultList();
    }
}
