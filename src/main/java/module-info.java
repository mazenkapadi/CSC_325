module com.example.csc_325 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.csc_325 to javafx.fxml;
    exports com.example.csc_325;
}