package com.serviconvi.ingresoventas;

import com.serviconvi.ingresoventas.dao.CatTipoDocumentoDAO;
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
import com.serviconvi.scentidades.CatTipoDocumento;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField tfFecha, tfSerie, tfDe, tfAl, tfCodCliente;
    @FXML
    private ComboBox cbTipoDoc;
    static Logger log = LogManager.getLogger(FXMLController.class);

    @FXML
    private void handleButtonAction(ActionEvent event) {
        log.info("You clicked me!");
        label.setText("Hello World!");
    }
    
    @FXML
    private void showSelection(ActionEvent event){
        CatTipoDocumento catTipoDoc = (CatTipoDocumento) cbTipoDoc.getValue();
        log.debug("catTipoDoc.getCodigo() : [{}] ", catTipoDoc.getCodigo());
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

            tfCodCliente.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue){
                    if (!newValue.matches("\\d*")) {
                        tfCodCliente.setText(newValue.replaceAll("[^\\d-]", ""));
                    }else{
                        log.debug("tfCodCliente : [{}]", tfCodCliente.getText());
                    }
                }
            });
            
            tfCodCliente.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if(!newPropertyValue){
                        log.info("Buscando cliente : [{}] ", tfCodCliente.getText());
                    }
                }
            });
            CatTipoDocumentoDAO catTipoDocDAO = new CatTipoDocumentoDAO();
            List<CatTipoDocumento> tipoDeDocumentos = catTipoDocDAO.obtenerTipoDocumento();
            cbTipoDoc.setItems(FXCollections.observableArrayList(tipoDeDocumentos));
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println("ERROR : ["+ex.getMessage()+"]");
        }
    }
}
