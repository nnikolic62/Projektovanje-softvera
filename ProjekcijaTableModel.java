/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tabele;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import domen.Festival;
import domen.Film;
import domen.Projekcija;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class ProjekcijaTableModel extends AbstractTableModel{

    Festival festival;
    String[] columNames = {"Film", "Reziser", "Datum i vreme projekcije"};
    Class[] columnClass = {String.class, String.class, String.class};
    public ProjekcijaTableModel() {
        festival = new Festival();
    }
    

    @Override
    public int getRowCount() {
        return festival.getProjekcije().size();
    }

    @Override
    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Projekcija p = festival.getProjekcije().get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm");
        switch(columnIndex){
            case 0:
                return p.getFilm();
            case 1:
                return p.getReziserFilma();
            case 2:
                try {
                    return sdf.format(p.getDatumVreme());
                } catch (Exception e) {
                    return "";
                }
            default:
                return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Projekcija p = festival.getProjekcije().get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm");
        switch(columnIndex){
            case 0:
                p.setFilm((Film) aValue);
                p.setReziserFilma(p.getFilm().getReziser());
                fireTableCellUpdated(rowIndex, 1);
                break;

            case 2:
        {
            try {
                p.setDatumVreme(sdf.parse((String)aValue));
            } catch (ParseException ex) {
                Logger.getLogger(ProjekcijaTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                break;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void dodaj(Projekcija p) {
        festival.getProjekcije().add(p);
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }


    public void obrisi(int red) {
        festival.getProjekcije().remove(red);
        fireTableDataChanged();
    }

    public List<Projekcija> vratiProjekcije() {
        return festival.getProjekcije();
    }

    public int vratiID(){
        return festival.getProjekcije().size() + 1;
    }
    
}
