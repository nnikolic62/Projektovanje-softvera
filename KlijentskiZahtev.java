/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.Serializable;
import operacija.Operacija;

/**
 *
 * @author User
 */
public class KlijentskiZahtev implements Serializable{
    Object argument;
    Operacija operacija;

    public KlijentskiZahtev() {
    }

    public KlijentskiZahtev(Object argument, Operacija operacija) {
        this.argument = argument;
        this.operacija = operacija;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public Operacija getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }
}
