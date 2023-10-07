module com.example.csc_325 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires firebase.admin;


    opens com.example.csc_325 to javafx.fxml;
    exports com.example.csc_325;
}