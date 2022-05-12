module com.example.grapio {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grapio to javafx.fxml;
    exports com.example.grapio;
}