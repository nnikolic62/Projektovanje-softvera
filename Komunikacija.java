/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Komunikacija {
    private static Komunikacija instance;
    Socket s;

    public Komunikacija() {
        try {
            s = new Socket("127.0.0.1", 9000);
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Komunikacija getInstance(){
        if(instance == null){
            instance = new Komunikacija();
        }
        return instance;
    }
    
    public ServerskiOdgovor prihvatiOdgovor() {
        try {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            return (ServerskiOdgovor) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void posaljiZahtev(KlijentskiZahtev kz) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(kz);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
