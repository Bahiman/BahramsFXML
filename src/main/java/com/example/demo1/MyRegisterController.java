package com.example.demo1;


import com.example.demo1.database.AddClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MyRegisterController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="reg"
    private Button regg; // Value injected by FXMLLoader

    @FXML // fx:id="passswordfield"
    private TextField passswordfield; // Value injected by FXMLLoader

    @FXML // fx:id="userfield"
    private TextField userfield; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert passswordfield != null : "fx:id=\"passswordfield\" was not injected: check your FXML file 'Register.fxml'.";
        assert regg != null : "fx:id=\"reg\" was not injected: check your FXML file 'Register.fxml'.";
        assert userfield != null : "fx:id=\"userfield\" was not injected: check your FXML file 'Register.fxml'.";

        // this button only registers
        regg.setOnAction(actionEvent -> {

            AddClass addClass = new AddClass();

            int r = addClass.insertValues(userfield.getText(), passswordfield.getText());

            if (r == 0) {
                System.out.println("Successfully registered");

            } else {
                System.out.println("Registration was not successful");
            }
        });
        }


    }


