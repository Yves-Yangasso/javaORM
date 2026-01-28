package yang.bao.app_java_diti4.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import yang.bao.app_java_diti4.Entity.TypeAssurance;
import yang.bao.app_java_diti4.repository.TypeAssuranceRepository;

public class TypeAssuranceController {

    @FXML
    private TextField tfLabel;

    @FXML
    private TableView<TypeAssurance> tableTypeAssurance;

    @FXML
    private TableColumn<TypeAssurance, Integer> colId;

    @FXML
    private TableColumn<TypeAssurance, String> colLabel;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    private TypeAssuranceRepository typeAssuranceRepository;
    private ObservableList<TypeAssurance> typeAssuranceList;
    private TypeAssurance selectedType;

    @FXML
    public void initialize() {
        typeAssuranceRepository = new TypeAssuranceRepository();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLabel.setCellValueFactory(new PropertyValueFactory<>("label"));

        loadTypeAssurances();

        tableTypeAssurance.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedType = newSelection;
                        tfLabel.setText(newSelection.getLabel());
                    }
                }
        );
    }

    private void loadTypeAssurances() {
        typeAssuranceList = FXCollections.observableArrayList(typeAssuranceRepository.findAll());
        tableTypeAssurance.setItems(typeAssuranceList);
    }

    @FXML
    void handleAjouter(ActionEvent event) {
        String label = tfLabel.getText().trim();
        if (label.isEmpty()) {
            System.out.println("Le label est obligatoire !");
            return;
        }

        TypeAssurance type = new TypeAssurance(label);
        typeAssuranceRepository.insert(type);
        loadTypeAssurances();
        tfLabel.clear();
    }

    @FXML
    void handleModifier(ActionEvent event) {
        if (selectedType == null) {
            System.out.println("Aucun type sélectionné !");
            return;
        }

        String label = tfLabel.getText().trim();
        if (label.isEmpty()) {
            System.out.println("Le label est obligatoire !");
            return;
        }

        selectedType.setLabel(label);
        typeAssuranceRepository.update(selectedType);
        loadTypeAssurances();
        tfLabel.clear();
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        if (selectedType == null) {
            System.out.println("Aucun type sélectionné !");
            return;
        }

        typeAssuranceRepository.delete(selectedType.getId());
        loadTypeAssurances();
        tfLabel.clear();
        selectedType = null;
    }


    @FXML
    void handleEffacer(ActionEvent event) {

    }
}
