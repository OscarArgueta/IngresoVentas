package com.servicont.ingresoventas;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField tfFecha, tfSerie, tfDe, tfAl;
    @FXML
    private ComboBox cbTipoDoc;

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
                        tfFecha.setText(newValue.replaceAll("[^\\d-]", ""));
                    }
                    if (tfFecha.getLength() == 4 && !tfFecha.getText().contains("-")){
                        String añoActual = new SimpleDateFormat("yyyy").format(new Date());
                        tfFecha.setText(newValue.substring(0,2)+"-"+newValue.substring(2,4)+"-"+añoActual);
                    }
                }
            });
            
            tfSerie.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue){
                    tfSerie.setText(newValue.toUpperCase());
                }
            });
            
            tfDe.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue){
                    if (!newValue.matches("\\d*")) {
                        tfDe.setText(newValue.replaceAll("[^\\d-]", ""));
                    }
                }
            });

            List<String> strings = new ArrayList<>();
            strings.add("Test1");
            strings.add("Test2");
            cbTipoDoc.setItems(FXCollections.observableArrayList(strings));
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println("ERROR : ["+ex.getMessage()+"]");
        }
    }
}
