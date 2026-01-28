package yang.bao.app_java_diti4;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import yang.bao.app_java_diti4.Entity.Assurance;
import yang.bao.app_java_diti4.repository.AssuranceRepository;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        AssuranceRepository assuranceRepository = new AssuranceRepository();

        // assuranceRepository.insert(new Assurance("Fatou diop",1200000.0));

        //assuranceRepository.findAll().forEach(System.out::println);

        Assurance assurance = assuranceRepository.findById(3);
        assurance.setNomClient("Dane lo");
        assuranceRepository.update(assurance);

    }
}