/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.KursnaLista;
import domen.StavkaKursneListe;
import domen.Zemlja;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class DBBroker {

    Connection connection;

    public void ucitajDrajver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void otvoriKonekciju() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dokaz", "root", "");
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void zatvoriKonekciju() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Zemlja> vratiSveZemlje() {
        String sql = "SELECT * FROM zemlja";
        List<Zemlja> list = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Zemlja z = new Zemlja(rs.getInt("ZemljaID"), rs.getString("Naziv"), rs.getString("Valuta"));
                list.add(z);
            }
            rs.close();
            st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int vratiTacanRB() {
        String sql = "SELECT MAX(RB) as max FROM stavkakursneliste";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("max") + 1;
            } else {
                return 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void unesiListu(KursnaLista kl) {
        try {
            String sql = "INSERT INTO kursnalista (Dan, Izvor) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, new java.sql.Date(kl.getDana().getTime()));
            ps.setString(2, kl.getIzvor());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                int ID = rs.getInt(1);
                kl.setKursnaListaID(ID);
                sql = "INSERT INTO stavkakursneliste VALUES (?,?,?,?,?,?,?)";
                System.out.println(kl.getStavke().size());
                ps = connection.prepareStatement(sql);
                for (StavkaKursneListe s : kl.getStavke()) {
                    ps.setInt(1, kl.getKursnaListaID());
                    ps.setInt(2, s.getRB());
                    ps.setInt(3, s.getZemlja().getZemljaID());
                    ps.setInt(4, s.getVaziZa());
                    ps.setDouble(5, s.getKupovni());
                    ps.setDouble(6, s.getProdajni());
                    ps.setDouble(7, s.getSrednji());
                    System.out.println("UNETO JE");
                    ps.executeUpdate();
                }
            } else {
                throw new Exception("GRESKA U SQL");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
