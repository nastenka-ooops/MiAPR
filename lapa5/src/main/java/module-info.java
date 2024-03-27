module com.example.lapa5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lapa5 to javafx.fxml;
    exports com.example.lapa5;
}