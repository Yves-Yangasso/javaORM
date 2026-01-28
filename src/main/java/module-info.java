module yang.bao.app_java_diti4 {

    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens yang.bao.app_java_diti4 to javafx.fxml;
    opens yang.bao.app_java_diti4.controllers to javafx.fxml;

    opens yang.bao.app_java_diti4.Entity;

    opens yang.bao.app_java_diti4.repository;

    exports yang.bao.app_java_diti4;
    exports yang.bao.app_java_diti4.controllers;
}
