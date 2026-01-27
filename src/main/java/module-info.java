module yang.bao.app_java_diti4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens yang.bao.app_java_diti4 to javafx.fxml;
    exports yang.bao.app_java_diti4;
}