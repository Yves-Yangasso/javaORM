package yang.bao.app_java_diti4.repository;

import jakarta.persistence.EntityManager;
import yang.bao.app_java_diti4.Entity.Assurance;
import yang.bao.app_java_diti4.repository.interfaceRepo.IInterface;
import java.util.List;

public class AssuranceRepository implements IInterface<Assurance> {

    private EntityManager entityManager;

    public AssuranceRepository() {

        this.entityManager = yang.bao.app_java_diti4.utils.JpaUtil.getEntityManagerFactory().createEntityManager();
    }


    @Override
    public void insert(Assurance assurance) {
        entityManager.getTransaction().begin(); //demarrage une transction
        entityManager.persist(assurance); //insert
        entityManager.getTransaction().commit(); //persiter la transction4
    }

    @Override
    public void update(Assurance assurance) {
        entityManager.getTransaction().begin();
        entityManager.merge(assurance); //update
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Assurance assurance = this.findById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(assurance); //delete
        entityManager.getTransaction().commit();
    }

    @Override
    public Assurance findById(int id) {
        return entityManager.find(Assurance.class, id);
    }

    @Override
    public List<Assurance> findAll() {
        List<Assurance> assurances ;
        return entityManager.createQuery("SELECT a FROM Assurance a", Assurance.class).getResultList();  //JPQL
    }
}