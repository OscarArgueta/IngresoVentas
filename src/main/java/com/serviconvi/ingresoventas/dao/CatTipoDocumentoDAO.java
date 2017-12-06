/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.ingresoventas.dao;

import com.serviconvi.ingresoventas.controlador.CatTipoDocumentoJpaController;
import com.serviconvi.scentidades.CatTipoDocumento;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author oscar
 */
public class CatTipoDocumentoDAO {

    private final CatTipoDocumentoJpaController catTipoDocJpaCtrl;
    private final EntityManagerFactory emf;
    
    public CatTipoDocumentoDAO() {
        emf = Persistence.createEntityManagerFactory("SERVICONVI_PU");
        catTipoDocJpaCtrl = new CatTipoDocumentoJpaController(emf);
    }
 
    public List<CatTipoDocumento> obtenerTipoDocumento(){
        return catTipoDocJpaCtrl.findCatTipoDocumentoEntities();
    }
    
}
