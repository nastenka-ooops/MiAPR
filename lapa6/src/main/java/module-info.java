module com.example.lapa6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lapa6 to javafx.fxml;
    exports com.example.lapa6;
}