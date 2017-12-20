package com.serviconvi.ingresoventas;

import com.serviconvi.ingresoventas.dao.CatClienteVentaDAO;
import com.serviconvi.ingresoventas.dao.CatTipoDocumentoDAO;
import com.serviconvi.scentidades.CatClienteVenta;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import com.serviconvi.util.MyLogger;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.LogManager;

public class FXMLController implements Initializable {
    
    boolean exiteCodCliente=true;
    boolean exiteNitCliente=true;
    Alert alerta = new Alert(AlertType.CONFIRMATION);
            
    @FXML
    private Label label;
    @FXML
    private TextField tfFecha, tfSerie, tfDe, tfAl, tfCodCliente, tfNIT, tfNombreCliVta;
    @FXML
    private ComboBox cbTipoDoc;
    MyLogger log = new MyLogger(LogManager.getLogger(FXMLController.class));
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        log.info("You clicked me!", "");
        label.setText("Hello World!");
    }
    
    @FXML
    private void showSelection(ActionEvent event){
        CatTipoDocumento catTipoDoc = (CatTipoDocumento) cbTipoDoc.getValue();
        log.debug("catTipoDoc.getCodigo()", catTipoDoc.getCodigo());
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
                        log.debug("tfCodCliente", tfCodCliente.getText());
                    }
                }
            });
            
            tfCodCliente.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if(!newPropertyValue){
                        log.info("Buscando cliente", tfCodCliente.getText());
                        CatClienteVentaDAO catClienteDAO = new CatClienteVentaDAO();
                        CatClienteVenta clienteVenta = catClienteDAO.encontrarPorCodigo(Integer.parseInt(tfCodCliente.getText()));
                        if (clienteVenta == null){
                            log.info("No existe codigo", tfCodCliente.getText());
                            exiteCodCliente=false;
                        }
                    }
                }
            });

            tfNIT.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue){
                    if (!newValue.matches("\\d*")) {
                        tfNIT.setText(newValue.replaceAll("[^\\d-]", ""));
                    }else{
                        log.debug("tfNIT", tfNIT.getText());
                    }
                }
            });
            
            
            tfNIT.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if(!newPropertyValue){
                        log.info("Buscando NIT", tfNIT.getText());
                        CatClienteVentaDAO catClienteDAO = new CatClienteVentaDAO();
                        CatClienteVenta clienteVenta = catClienteDAO.encontrarPorNit(tfNIT.getText());
                        if (clienteVenta == null){
                            log.info("No existe NIT", tfNIT.getText());
                            exiteNitCliente=false;
                        }
                    }
                }
            });

            tfNombreCliVta.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue){
                    tfNombreCliVta.setText(newValue.toUpperCase());
                }
            });
            
            
            tfNombreCliVta.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if(!newPropertyValue){
                        if (!exiteCodCliente || !exiteNitCliente){
                            log.debug("¿Ingresar Cliente?", tfNombreCliVta.getText());
                            alerta.setTitle("Confirmacion");
                            String msgAlerta = "El codigo \""+tfCodCliente.getText()+"\" y el NIT \""+tfNIT.getText()+"\" no fueron encontrados";
                            alerta.setHeaderText(msgAlerta);
                            alerta.setContentText("¿Desea agregarlo?");
                            
                            Optional<ButtonType> decideIngresoCliVta = alerta.showAndWait();
                            if (decideIngresoCliVta.get() == ButtonType.OK){
                                log.info("Se debe agregar el NIT", tfNIT.getText());
                            }else{
                                log.info("Rechazo el ingreo del NIT", tfNIT.getText());
                            }
                        }
                    }
                }
            });
            
            CatTipoDocumentoDAO catTipoDocDAO = new CatTipoDocumentoDAO();
            List<CatTipoDocumento> tipoDeDocumentos = catTipoDocDAO.obtenerTipoDocumento();
            cbTipoDoc.setItems(FXCollections.observableArrayList(tipoDeDocumentos));
            catTipoDocDAO = null;
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println("ERROR : ["+ex.getMessage()+"]");
        }
    }
}
