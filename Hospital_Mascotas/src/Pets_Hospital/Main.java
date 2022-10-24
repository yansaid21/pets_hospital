/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pets_Hospital;

import Controller_pkg.Conexion;
import Views_pkg.Vista;

/**
 *
 * @author JEAN SAID
 */
public class Main {

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
        Conexion inst_conn = new Conexion();
        inst_conn.getConnection();
        Vista inst_frame = new Vista();
        inst_conn.getConnection();
        inst_frame.setVisible(true);
    }
}
    

