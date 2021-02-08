/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class ServerskiOdgovor implements Serializable{
    Object rezultat;
    boolean uspesno;
    String poruka;

    public ServerskiOdgovor() {
    }

    public ServerskiOdgovor(Object rezultat, boolean uspesno, String poruka) {
        this.rezultat = rezultat;
        this.uspesno = uspesno;
        this.poruka = poruka;
    }



    public Object getRezultat() {
        return rezultat;
    }

    public void setRezultat(Object rezultat) {
        this.rezultat = rezultat;
    }

    public boolean isUspesno() {
        return uspesno;
    }

    public void setUspesno(boolean uspesno) {
        this.uspesno = uspesno;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
}
