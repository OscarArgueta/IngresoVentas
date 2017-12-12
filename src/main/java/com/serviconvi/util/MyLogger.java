/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serviconvi.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author oscar
 */
public class MyLogger{
    
    static Logger log;
    
    public static Logger getLogger(Class<?> clazz){
        log = LogManager.getLogger(clazz);
        return log;
    }
    
    public static void info(String etiqueta, String valor){
       String cadena = String.format("%-30.30s : ",etiqueta) + "[{}]";
       log.info(cadena, valor);
    }
    
    public static void debug(String etiqueta, Object valor){
       String cadena = String.format("%-30.30s : ",etiqueta) + "[{}]";
       log.debug(cadena, valor);
    }
}
