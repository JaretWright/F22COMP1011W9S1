module com.example.f22comp1011w9s1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.f22comp1011w9s1 to javafx.fxml;
    exports com.example.f22comp1011w9s1;
}