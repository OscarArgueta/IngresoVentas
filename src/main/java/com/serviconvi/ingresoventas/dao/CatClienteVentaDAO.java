/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.ingresoventas.dao;

import com.serviconvi.ingresoventas.controlador.CatClienteVentaJpaController;
import com.serviconvi.scentidades.CatClienteVenta;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author oscar
 */
public class CatClienteVentaDAO {
    private final CatClienteVentaJpaController catClienteVentajpaCtrl;
    private final EntityManagerFactory emf;
    
    public CatClienteVentaDAO() {
        emf = Persistence.createEntityManagerFactory("SERVICONVI_PU");
        catClienteVentajpaCtrl = new CatClienteVentaJpaController(emf);
    }

    public CatClienteVenta encontrarPorCodigo(Integer codigoCliente){
        return catClienteVentajpaCtrl.findCatClienteVenta(codigoCliente);
    }
}
