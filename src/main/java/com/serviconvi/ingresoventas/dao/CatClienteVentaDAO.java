/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.ingresoventas.dao;

import com.serviconvi.ingresoventas.controlador.CatClienteVentaJpaController;
import com.serviconvi.scentidades.CatClienteVenta;
import com.serviconvi.util.MyLogger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author oscar
 */
public class CatClienteVentaDAO {
    private final CatClienteVentaJpaController catClienteVentajpaCtrl;
    private final EntityManagerFactory emf;
    
    MyLogger log = new MyLogger(LogManager.getLogger(CatClienteVentaDAO.class));
    
    public CatClienteVentaDAO() {
        emf = Persistence.createEntityManagerFactory("SERVICONVI_PU");
        catClienteVentajpaCtrl = new CatClienteVentaJpaController(emf);
    }

    public CatClienteVenta encontrarPorCodigo(Integer codigoCliente){
        return catClienteVentajpaCtrl.findCatClienteVenta(codigoCliente);
    }
    
    public CatClienteVenta encontrarPorNit(String nit){
        return catClienteVentajpaCtrl.findCatClienteVenta(nit);
    }
    
    public boolean agregarClienteVta(CatClienteVenta catClienteVenta){
        try{
            log.debug("NIT", catClienteVenta.getCatClienteVentaPK().getNit());
            log.debug("Nombre", catClienteVenta.getNombreCliente());
            catClienteVentajpaCtrl.create(catClienteVenta);
            return true;
        }catch(Exception ex){
            log.error("Al intentar ingresar el cliente", ex);   
            return false;
        }
    }
}
