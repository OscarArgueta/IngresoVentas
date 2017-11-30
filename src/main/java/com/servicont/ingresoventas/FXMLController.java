package com.servicont.ingresoventas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField tfFecha;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            tfFecha.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                    if (!newValue.matches("\\d*")) {
                        tfFecha.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println("ERROR : ["+ex.getMessage()+"]");
        }
    }
}
