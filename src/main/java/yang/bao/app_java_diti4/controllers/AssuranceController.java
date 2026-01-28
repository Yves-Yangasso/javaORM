package yang.bao.app_java_diti4.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import yang.bao.app_java_diti4.Entity.Assurance;
import yang.bao.app_java_diti4.Entity.TypeAssurance;
import yang.bao.app_java_diti4.repository.AssuranceRepository;
import yang.bao.app_java_diti4.repository.TypeAssuranceRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class AssuranceController implements Initializable {

    @FXML
    private TextField tfNomClient;
    @FXML
    private TextField tfMontant;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfRecherche;
    @FXML
    private ComboBox<TypeAssurance> cbTypeAssurance;

    @FXML
    private TableView<Assurance> tableAssurance;
    @FXML
    private TableColumn<Assurance, Integer> colId;
    @FXML
    private TableColumn<Assurance, String> colNumero;
    @FXML
    private TableColumn<Assurance, String> colNomClient;
    @FXML
    private TableColumn<Assurance, Double> colMontant;
    @FXML
    private TableColumn<Assurance, String> colTypeAssurance;

    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnEffacer;
    @FXML
    private Button btnActualiser;

    private AssuranceRepository assuranceRepository;
    private TypeAssuranceRepository typeAssuranceRepository;
    private ObservableList<Assurance> assuranceList;
    private FilteredList<Assurance> filteredAssurances;
    private Assurance selectedAssurance;

    public AssuranceController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.assuranceRepository = new AssuranceRepository();
        this.typeAssuranceRepository = new TypeAssuranceRepository();

        configurerTable();
        loadTypeAssurances();
        printAllAssurance();
        setupSearchFilter();
        setupTableSelection();
    }

    private void configurerTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNomClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colTypeAssurance.setCellValueFactory(cellData -> {
            Assurance assurance = cellData.getValue();
            if (assurance.getTypeAssurance() != null) {
                return new javafx.beans.property.SimpleStringProperty(assurance.getTypeAssurance().getLabel());
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        colMontant.setCellFactory(column -> new TableCell<Assurance, Double>() {
            @Override
            protected void updateItem(Double montant, boolean empty) {
                super.updateItem(montant, empty);
                if (empty || montant == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f FCFA", montant));
                }
            }
        });
    }

    public void printAllAssurance() {
        assuranceList = FXCollections.observableArrayList(assuranceRepository.findAll());
        filteredAssurances = new FilteredList<>(assuranceList, p -> true);
        tableAssurance.setItems(filteredAssurances);
    }

    private void loadTypeAssurances() {
        ObservableList<TypeAssurance> typeAssuranceList = FXCollections.observableArrayList(typeAssuranceRepository.findAll());
        cbTypeAssurance.setItems(typeAssuranceList);

        cbTypeAssurance.setConverter(new StringConverter<TypeAssurance>() {
            @Override
            public String toString(TypeAssurance type) {
                return type != null ? type.getLabel() : "";
            }

            @Override
            public TypeAssurance fromString(String string) {
                return typeAssuranceList.stream()
                        .filter(type -> type.getLabel().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void setupSearchFilter() {
        if (tfRecherche != null) {
            tfRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredAssurances.setPredicate(assurance -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (assurance.getNumero() != null && assurance.getNumero().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (assurance.getNomClient() != null && assurance.getNomClient().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (String.valueOf(assurance.getMontant()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (assurance.getTypeAssurance() != null && assurance.getTypeAssurance().getLabel().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
        }
    }

    private void setupTableSelection() {
        tableAssurance.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedAssurance = newSelection;
                        displayAssurance(newSelection);
                    }
                });
    }

    private void displayAssurance(Assurance assurance) {
        tfNumero.setText(assurance.getNumero());
        tfNumero.setEditable(false);
        tfNomClient.setText(assurance.getNomClient());
        tfMontant.setText(String.valueOf(assurance.getMontant()));
        if (assurance.getTypeAssurance() != null) {
            cbTypeAssurance.setValue(assurance.getTypeAssurance());
        } else {
            cbTypeAssurance.setValue(null);
        }
    }

    @FXML
    void handleAjouter(ActionEvent event) {
        if (validateFields()) {
            Assurance assurance = new Assurance(tfNomClient.getText().trim(), Double.parseDouble(tfMontant.getText().trim()));
            TypeAssurance selectedType = cbTypeAssurance.getValue();
            assurance.setTypeAssurance(selectedType);
            assuranceRepository.insert(assurance);
            printAllAssurance();
            handleEffacer(event);
        }
    }

    @FXML
    void handleModifier(ActionEvent event) {
        if (selectedAssurance != null && validateFields()) {
            selectedAssurance.setNomClient(tfNomClient.getText().trim());
            selectedAssurance.setMontant(Double.parseDouble(tfMontant.getText().trim()));
            TypeAssurance selectedType = cbTypeAssurance.getValue();
            selectedAssurance.setTypeAssurance(selectedType);
            assuranceRepository.update(selectedAssurance);
            printAllAssurance();
            handleEffacer(event);
        }
    }

    @FXML
    void handleSupprimer(ActionEvent event) {
        if (selectedAssurance != null) {
            assuranceRepository.delete(selectedAssurance.getId());
            printAllAssurance();
            handleEffacer(event);
        }
    }

    @FXML
    void handleEffacer(ActionEvent event) {
        tfNumero.clear();
        tfNomClient.clear();
        tfMontant.clear();
        cbTypeAssurance.setValue(null);
        selectedAssurance = null;
        tfNumero.setEditable(true);
        tableAssurance.getSelectionModel().clearSelection();
    }

    @FXML
    void handleActualiser(ActionEvent event) {
        printAllAssurance();
        loadTypeAssurances();
        handleEffacer(event);
        if (tfRecherche != null) {
            tfRecherche.clear();
        }
    }

    @FXML
    void handleRecherche(KeyEvent event) {

    }

    private boolean validateFields() {
        if (tfNomClient.getText() == null || tfNomClient.getText().trim().isEmpty()) {
            return false;
        }
        if (tfMontant.getText() == null || tfMontant.getText().trim().isEmpty()) {
            return false;
        }
        try {
            double montant = Double.parseDouble(tfMontant.getText().trim());
            if (montant < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        if (cbTypeAssurance.getValue() == null) {
            return false;
        }
        return true;
    }
}