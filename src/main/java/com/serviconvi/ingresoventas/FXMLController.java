package com.serviconvi.ingresoventas;

import com.serviconvi.ingresoventas.dao.CatClienteVentaDAO;
import com.serviconvi.ingresoventas.dao.CatTipoDocumentoDAO;
import com.serviconvi.ingresoventas.dao.CatTipoVentaDAO;
import com.serviconvi.scentidades.CatClienteVenta;
import com.serviconvi.scentidades.CatClienteVentaPK;
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
import com.serviconvi.scentidades.CatTipoVenta;
import com.serviconvi.util.MyLogger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import org.apache.logging.log4j.LogManager;

public class FXMLController implements Initializable {
    
    boolean exiteCodCliente=true;
    boolean exiteNitCliente=true;
    Alert alertaConfirm = new Alert(AlertType.CONFIRMATION);
    Alert alertaInfo = new Alert(AlertType.INFORMATION);
    Alert alertaError = new Alert(AlertType.ERROR);
    boolean perdioFocoAlerta = false;

    @FXML
    private TitledPane tpCliente, tpServicio;
    @FXML
    private Label label;
    @FXML
    private TextField tfFecha, tfSerie, tfDe, tfAl, tfCodCliente, tfCodServicio, tfNIT, tfNombreCliVta, tfMontoBruto, tfMontoNeto, tfImpuesto;
    @FXML
    private ComboBox cbTipoDoc, cbTipoVenta;
    MyLogger log = new MyLogger(LogManager.getLogger(FXMLController.class));
    
    CatTipoVenta catTipoVenta;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        log.info("You clicked me!", "");
        label.setText("Hello World!");
    }
    
    @FXML
    private void showSelection(ActionEvent event){
        CatTipoDocumento catTipoDoc = (CatTipoDocumento) cbTipoDoc.getValue();
        log.debug("catTipoDoc.getCodigo()", catTipoDoc.getCodigo());
        if (!tpCliente.isDisable()){
            tfCodCliente.requestFocus();
        }else{
            tfCodServicio.requestFocus();
        }
    }
    
    @FXML
    private void seleccionTipoVenta(ActionEvent event){
        catTipoVenta = (CatTipoVenta) cbTipoVenta.getValue();
        log.debug("El impuesto es", catTipoVenta.getImpuesto());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            tpCliente.setDisable(true);
            tpServicio.setDisable(true);
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

            tfAl.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue){
                    if (!newValue.isEmpty() || newValue == null ) {
                        tpCliente.setDisable(false);
                        tpServicio.setDisable(true);
                    }else{
                        tpCliente.setDisable(true);
                        tpServicio.setDisable(false);
                        tfCodCliente.clear();
                        tfNIT.clear();
                        tfNombreCliVta.clear();
                    }
                }
            });

            tfAl.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
                                    Boolean newPropertyValue){
                    if (oldPropertyValue){
                        if (tfAl.getText() == null || tfAl.getText().isEmpty() || "1".equals(tfAl.getText())) {
                            log.debug("1 tfAl.getText() : ", tfAl.getText());
                            tpCliente.setDisable(false);
                            tpServicio.setDisable(true);
                        }else{
                            log.debug("2 tfAl.getText() : ", tfAl.getText());
                            tpCliente.setDisable(true);
                            tpServicio.setDisable(false);
                            tfCodCliente.clear();
                            tfNIT.clear();
                            tfNombreCliVta.clear();
                        }
                    }
                }
            });

            tfCodCliente.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue){
                    if (newValue.isEmpty()){
                        tfNIT.clear();                        
                        tfNombreCliVta.clear();                        
                    }
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
                    if(!newPropertyValue && !tfCodCliente.getText().isEmpty()){
                        exiteCodCliente=true;
                        log.info("Buscando cliente", tfCodCliente.getText());
                        CatClienteVentaDAO catClienteDAO = new CatClienteVentaDAO();
                        CatClienteVenta clienteVenta = catClienteDAO.encontrarPorCodigo(Integer.parseInt(tfCodCliente.getText()));
                        if (clienteVenta == null){
                            log.info("No existe codigo", tfCodCliente.getText());
                            exiteCodCliente=false;
                        }else{
                            tfNIT.setText(clienteVenta.getCatClienteVentaPK().getNit());
                            tfNombreCliVta.setText(clienteVenta.getNombreCliente());
                            tfNombreCliVta.requestFocus();
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
                        exiteNitCliente=true;
                        log.info("Buscando NIT", tfNIT.getText());
                        CatClienteVentaDAO catClienteDAO = new CatClienteVentaDAO();
                        CatClienteVenta clienteVenta = catClienteDAO.encontrarPorNit(tfNIT.getText());
                        if (clienteVenta == null){
                            log.info("No existe NIT", tfNIT.getText());
                            exiteNitCliente=false;
                        }else{
                            tfCodCliente.setText(String.valueOf(clienteVenta.getCatClienteVentaPK().getCodCliente()));
                            tfNombreCliVta.setText(clienteVenta.getNombreCliente());
                            tfNombreCliVta.requestFocus();
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

            tfMontoBruto.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if (perdioFocoAlerta){
                        perdioFocoAlerta=false;
                        return;
                    }
                    if(!newPropertyValue){
                        if (!tfMontoBruto.getText().matches("(?=.)^ ?(([1-9][0-9]{0,2}(,[0-9]{3})*)|0)?(\\.[0-9]{1,2})?$")){
                            log.info(" MontoBruto sin comas. ", "");
                            Double montoBrutoTmp = Double.parseDouble(tfMontoBruto.getText());
                            tfMontoBruto.setText(NumberFormat.getNumberInstance(Locale.US).format(montoBrutoTmp));
                            //tfMontoBruto.setStyle("-fx-border-color: red ;");
                        }else{
                            //tfMontoBruto.setStyle("");
                        }
                        BigDecimal temporal = new BigDecimal(tfMontoBruto.getText().replace(",", ""));
                        BigDecimal montoBruto = temporal.setScale(2,  RoundingMode.HALF_UP);
                        
                        if (catTipoVenta.getImpuesto().signum() > 0 ){
                            BigDecimal iva = catTipoVenta.getImpuesto().setScale(2, RoundingMode.HALF_UP);
                            log.debug("iva",iva);
                            log.debug("montoBruto", montoBruto);
                            temporal = montoBruto.divide(iva.add(new BigDecimal("1")), 2, RoundingMode.HALF_UP);
                            log.debug("temporal", temporal);
                            BigDecimal montoNeto = temporal.setScale(2,  RoundingMode.HALF_UP);
                            log.debug("montoNeto", montoNeto);
                            tfMontoNeto.setText(NumberFormat.getNumberInstance(Locale.US).format(montoNeto));
                            temporal = montoNeto.multiply(catTipoVenta.getImpuesto());
                            BigDecimal impuesto = temporal.setScale(2, RoundingMode.HALF_UP);
                            tfImpuesto.setText(NumberFormat.getNumberInstance(Locale.US).format(impuesto));
                        }else{
                            tfMontoNeto.setText(tfMontoBruto.getText());
                            tfImpuesto.setText("0.00");
                        }
                    }else{
                        if (catTipoVenta == null){
                            perdioFocoAlerta = true;
                            alertaInfo.setTitle("Informacion requerida");
                            alertaInfo.setHeaderText(null);
                            alertaInfo.setContentText("Por favor, seleccione \"TIPO DE VENTA\" antes de ingresar valores.");
                            alertaInfo.showAndWait();
                            cbTipoVenta.requestFocus();
                        }
                    }
                }
            });
            
            tfNombreCliVta.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if(!newPropertyValue){
                        if (!exiteCodCliente && !exiteNitCliente){
                            log.debug("¿Ingresar Cliente?", tfNombreCliVta.getText());
                            alertaConfirm.setTitle("Confirmacion");
                            String msgAlerta = "El codigo \""+tfCodCliente.getText()+"\" y el NIT \""+tfNIT.getText()+"\" no fueron encontrados";
                            alertaConfirm.setHeaderText(msgAlerta);
                            alertaConfirm.setContentText("¿Desea agregarlo?");
                            
                            Optional<ButtonType> decideIngresoCliVta = alertaConfirm.showAndWait();
                            if (decideIngresoCliVta.get() == ButtonType.OK){
                                log.info("Se debe agregar el NIT", tfNIT.getText());
                                CatClienteVentaPK catClienteVentaPK = new CatClienteVentaPK(0, tfNIT.getText());
                                CatClienteVenta catClienteVenta = new CatClienteVenta(catClienteVentaPK, tfNombreCliVta.getText());
                                CatClienteVentaDAO catClienteDAO = new CatClienteVentaDAO();
                                if(catClienteDAO.agregarClienteVta(catClienteVenta)){
                                    msgAlerta="Cliente con NIT \""+tfNIT.getText()+"\" ingresado correctamente.";
                                    alertaInfo.setTitle("Informacion");
                                    alertaInfo.setHeaderText(null);
                                    alertaInfo.setContentText(msgAlerta);
                                    alertaInfo.showAndWait();
                                }else{
                                    alertaError.setTitle("Error");
                                    alertaError.setHeaderText(null);
                                    alertaError.setContentText("No fue posible agregar el Cliente. ");
                                    alertaError.showAndWait();
                                }
                                
                            }else{
                                log.info("Rechazo el ingreo del NIT", tfNIT.getText());
                            }
                        }
                    }
                }
            });
            /* Incializa los Tipos de Documentos */
            CatTipoDocumentoDAO catTipoDocDAO = new CatTipoDocumentoDAO();
            List<CatTipoDocumento> tipoDeDocumentos = catTipoDocDAO.obtenerTipoDocumento();
            cbTipoDoc.setItems(FXCollections.observableArrayList(tipoDeDocumentos));
            catTipoDocDAO = null;
            /* Incializa los Tipos de Ventas */
            CatTipoVentaDAO catTipoVtaDAO = new CatTipoVentaDAO();
            List<CatTipoVenta> tipoVentas = catTipoVtaDAO.obtenerTipoVenta();
            cbTipoVenta.setItems(FXCollections.observableArrayList(tipoVentas));
            catTipoVtaDAO = null;

        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println("ERROR : ["+ex.getMessage()+"]");
        }
    }
}
