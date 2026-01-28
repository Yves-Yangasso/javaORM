package yang.bao.app_java_diti4;

import yang.bao.app_java_diti4.Entity.Assurance;
import yang.bao.app_java_diti4.repository.AssuranceRepository;

public class Main {
    public static void main(String[] args) {

        // Récupération du singleton
        AssuranceRepository assuranceRepository = AssuranceRepository.getInstance();

        // Exemple d'insertion
        // assuranceRepository.insert(new Assurance("Fatou Diop", 1200000.0));

        // Afficher toutes les assurances
        // assuranceRepository.findAll().forEach(System.out::println);

        // Mise à jour d'une assurance
        Assurance assurance = assuranceRepository.findById(3);
        if (assurance != null) {
            assurance.setNomClient("Dane Lo");
            assuranceRepository.update(assurance);
            System.out.println("Mise à jour effectuée : " + assurance);
        } else {
            System.out.println("Assurance avec id 3 non trouvée !");
        }
    }
}
