/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import domen.KursnaLista;
import domen.Zemlja;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.KlijentskiZahtev;
import komunikacija.ServerskiOdgovor;
import kontroler.Kontroler;

/**
 *
 * @author User
 */
public class ObradiKlijentaNit extends Thread{
    Socket s;

    public ObradiKlijentaNit(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            while (true) {
                KlijentskiZahtev kz = primiZahtev();
                ServerskiOdgovor so = new ServerskiOdgovor();

                switch(kz.getOperacija()){
                    case VRATI_ZEMLJE:
                        List<Zemlja> gradovi = Kontroler.getInstance().vratiZemlje();
                        so.setRezultat(gradovi);
                        break;
                    case VRATI_RB:
                        int RB = Kontroler.getInstance().vratiRB();
                        so.setRezultat(RB);
                        break;
                    case SACUVAJ:
                        KursnaLista kl = (KursnaLista) kz.getArgument();
                        String poruka = Kontroler.getInstance().unesi(kl);
                        so.setPoruka(poruka);
                        break;
                }
                
                posaljiOdgovor(so);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private KlijentskiZahtev primiZahtev() {
        try {
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            return (KlijentskiZahtev) in.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ObradiKlijentaNit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradiKlijentaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void posaljiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(so);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ObradiKlijentaNit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
