/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class PokreniServerNit extends Thread{
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            System.out.println("server je pokrenut");
            Socket s = ss.accept();
            System.out.println("klijent je povezan");
            ObradiKlijentaNit okn = new ObradiKlijentaNit(s);
            okn.start();
        } catch (IOException ex) {
            Logger.getLogger(PokreniServerNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
