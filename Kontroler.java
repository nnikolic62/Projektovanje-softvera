/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import db.DBBroker;
import domen.KursnaLista;
import domen.Zemlja;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class Kontroler {
    private static Kontroler instance;
    DBBroker db;

    public Kontroler() {
        db = new DBBroker();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public List<Zemlja> vratiZemlje() {
        List<Zemlja> list = new ArrayList<>();
        db.ucitajDrajver();
        db.otvoriKonekciju();
        try {
            list = db.vratiSveZemlje();
            db.commit();
        } catch (Exception e) {
            db.rollback();
        } finally {
            db.zatvoriKonekciju();
        }
        return list;
    }

    public int vratiRB() {
        int sifra = 0;
        db.ucitajDrajver();
        db.otvoriKonekciju();
        try {
            sifra = db.vratiTacanRB();
            db.commit();
        } catch (Exception e) {
            db.rollback();
        } finally {
            db.zatvoriKonekciju();
        }
        return sifra;
    }

    public String unesi(KursnaLista kl) {
        db.ucitajDrajver();
        db.otvoriKonekciju();
        try {
            db.unesiListu(kl);
            db.commit();
            return "Uspesno uneto";
        } catch (Exception e) {
            db.rollback();
        } finally{
            db.zatvoriKonekciju();
        }
        return "Neuspesno";
    }
}
