package com.example.csc_325;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.io.IOException;

public class signupViewController extends Application
{
    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Button signupButton;



    private static Scene scene;





    @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(com.example.csc_325.HelloApplication.class.getResource("signupView.fxml"));
            scene = new Scene(fxmlLoader.load(), 540, 440);
           // stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        }


    static void setRoot(String fxml) throws IOException
    {
        scene.setRoot(loadFXML(fxml));
    }

     private static Parent loadFXML(String fxml) throws IOException
     {
         FXMLLoader fxmlLoader = new FXMLLoader(signupViewController.class.getResource(fxml + ".fxml"));
         return fxmlLoader.load();
     }


    @FXML
    private void change() throws IOException
    {
        signupViewController.setRoot("hello-view");
    }

    public static void main(String[] args) {
        launch();
    }

}
