/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tabele;

import domen.KursnaLista;
import domen.StavkaKursneListe;
import domen.Zemlja;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author User
 */
public class StavkaTableModel extends AbstractTableModel{
    KursnaLista kursnaLista;
    String[] columnNames = {"Zemlja", "Valuta", "Vazi za", "Kupovni", "Prodajni"};
    Class[] columnClass = {Zemlja.class, String.class, Integer.class, Double.class, Double.class};
    
    public StavkaTableModel() {
        kursnaLista = new KursnaLista();
    }

    @Override
    public int getRowCount() {
        return kursnaLista.getStavke().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaKursneListe s = kursnaLista.getStavke().get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return s.getZemlja();
            case 1:
                return s.getValuta();
            case 2:
                return s.getVaziZa();
            case 3:
                return s.getKupovni();
            case 4:
                return s.getProdajni();
            default:
                return "n/a";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        StavkaKursneListe s = kursnaLista.getStavke().get(rowIndex);
        
        switch(columnIndex){
            case 0:
                s.setZemlja((Zemlja) aValue);
                s.setValuta(s.getZemlja().getValuta());
                fireTableCellUpdated(rowIndex, 1);
                break;
            case 2:
                s.setVaziZa((int) aValue);
                break;
            case 3:
                s.setKupovni((double) aValue);
                break;
            case 4:
                s.setProdajni((double) aValue);
                s.setSrednji((double)((s.getKupovni() + s.getProdajni()) / 2));
                break;
        }
    }

    public void dodaj(StavkaKursneListe s) {
        kursnaLista.getStavke().add(s);
        fireTableDataChanged();
    }

    public void obrisi(int red) {
        kursnaLista.getStavke().remove(red);
        fireTableDataChanged();
    }
    
    public List<StavkaKursneListe> vratiListu(){
        return kursnaLista.getStavke();
    }
    
    public int vratiRB(){
        return kursnaLista.getStavke().size() + 1;
    }
    
    
}
