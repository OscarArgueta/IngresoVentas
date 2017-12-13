/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.util;

import org.apache.logging.log4j.Logger;

/**
 *
 * @author oscar
 */
public class MyLogger{
    
    Logger log;

    public MyLogger(Logger logger) {
        log = logger;
    }
    
    public Logger getLogger(){
        return log;
    }
    
    public void info(String etiqueta, String valor){
       String cadena = String.format("%-30.30s : ",etiqueta) + "[{}]";
       log.info(cadena, valor);
    }
    
    public void debug(String etiqueta, Object valor){
       String cadena = String.format("%-30.30s : ",etiqueta) + "[{}]";
       log.debug(cadena, valor);
    }

    public void error(String msg, Exception ex){
       ex.printStackTrace();
       log.error(msg + " " + ex.getMessage());
    }

}
