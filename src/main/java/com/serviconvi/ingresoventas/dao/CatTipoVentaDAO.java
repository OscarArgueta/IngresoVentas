package com.serviconvi.ingresoventas.dao;

import com.serviconvi.ingresoventas.controlador.CatTipoVentaJpaController;
import com.serviconvi.scentidades.CatTipoVenta;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oscar
 */
public class CatTipoVentaDAO {

    private final CatTipoVentaJpaController catTipoVtaJpaCtrl;
    private final EntityManagerFactory emf;
    
    public CatTipoVentaDAO() {
        emf = Persistence.createEntityManagerFactory("SERVICONVI_PU");
        catTipoVtaJpaCtrl = new CatTipoVentaJpaController(emf);
    }
 
    public List<CatTipoVenta> obtenerTipoVenta(){
        return catTipoVtaJpaCtrl.findCatTipoVentaEntities();
    }
    
}
