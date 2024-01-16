/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familytree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author szeke
 */
public class Database {
    private String url;
    private String databaseName;
    private String user;
    private String pass;
    private Connection conn;

    public Database(String url, String databaseName, String user, String pass) {
        this.url = url;
        this.databaseName = databaseName;
        this.user = user;
        this.pass = pass;
        this.conn = null;
        
        //Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Baj van! Nincs meg a driver! " +ex);
        }
        
        //kapcsolódás az adatbázishoz
        try {
            this.conn = DriverManager.getConnection(this.url+this.databaseName, this.user, this.pass);
        } catch (SQLException ex) {
            System.out.println("Az adatbázis vagy az adattábla nem található...");
        }
        
        if(this.conn != null) System.out.println("Sikeres kapcsolódás");
        
    }
    
    public boolean isConnected(){
        if(this.conn!=null){
            return true;
        }
        else return false;
    }
    
    
    public boolean updateSzemely(Szemely sz, boolean voltSzulo){
        int lany = (sz.isLany()) ? 1 : 0;
        
        if(this.conn != null){
            String sql = "UPDATE szemelyek SET nev = '" + sz.getNev() + "', szulhely = '" + sz.getSzulhely() + "', szulido = '" + sz.getSzulido() + "', halalhely = '"
                    + sz.getHalalhely() + "', halalido = '" + sz.getHalalido() + "', lany = " + lany + " WHERE szemelyiszam LIKE '" + sz.getSzemelyiSzam() + "';";
            
            
            Statement stmt = null;
            
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    stmt.executeUpdate(sql);
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            }
            
            String sql2;
            if(this.conn != null){
                String anya = "";
                String apa = "";
                String gyerekId = sz.getSzemelyiSzam();
                if(!(sz.getAnya().equals(""))){
                    anya = sz.getAnya();
                }
                if(!(sz.getApa().equals(""))){
                    apa = sz.getApa();
                }
                System.out.println(voltSzulo);
                System.out.println(anya);
                System.out.println(gyerekId);
                if(voltSzulo){
                    sql2 = "UPDATE szulok SET anyaId = '" + anya + "', apaId = '" + apa + "' WHERE gyerekId LIKE '"+ gyerekId + "';";
                }
                else {
                    sql2 = "INSERT INTO `szulok` (`gyerekId`, `anyaId`, `apaId`) VALUES('" + gyerekId + "', '" + anya + "', '" + apa + "');";
                }
                Statement stmt2 = null;
                try {
                    stmt2 = conn.createStatement();
                } catch (SQLException ex) {
                    System.out.println("Baj van! Hiba a statement létrehozásában! " + ex);
                }

                if(stmt2 != null){
                    try {    
                        stmt2.executeUpdate(sql2);
                    } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
                }
            }
        }
        return true;
    }
    
    public Szemely szemelyLekerdezes(String keresoSzoveg){
        Szemely sz = new Szemely();
        ResultSet rs;
        
        if(this.conn != null){
            String sql = "SELECT * FROM szemelyek LEFT JOIN szulok ON szemelyiszam=szulok.gyerekId WHERE szemelyiszam LIKE'" + keresoSzoveg + "';";
    
            Statement stmt = null;
            
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        sz.setSzemelyiSzam(rs.getString("szemelyiszam"));
                        sz.setNev(rs.getString("nev"));
                        sz.setLany(rs.getBoolean("lany"));
                        sz.setSzulhely(rs.getString("szulhely"));
                        sz.setSzulido(rs.getString("szulido"));
                        sz.setAnya(rs.getString("anyaId"));
                        sz.setApa(rs.getString("apaId"));
                        sz.setHalalido(rs.getString("halalido"));
                        sz.setHalalhely(rs.getString("halalhely"));
                        
                        System.out.println(sz);
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
        System.out.println("Adatlekérdezés történt");
        
        return sz;
    }
    
    public ArrayList<Szemely> nevSzamKereso(String keresoSzoveg){
        ArrayList<Szemely> result = new ArrayList<>();
        ResultSet rs;
        
        if(this.conn != null){
            String sql = "SELECT * FROM szemelyek LEFT JOIN szulok ON szemelyiszam=szulok.gyerekId WHERE nev LIKE'%" + keresoSzoveg +
                    "%' OR szemelyiszam LIKE '%" + keresoSzoveg + "%';";
    
            Statement stmt = null;
            
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        Szemely sz = new Szemely(rs.getString("szemelyiszam"), rs.getString("nev"), 
                                rs.getBoolean("lany"), rs.getString("szulhely"), rs.getString("szulido"), 
                                rs.getString("anyaId"), rs.getString("apaId"), rs.getString("halalido"), rs.getString("halalhely"));
                        
                        
                        System.out.println(sz);
                        result.add(sz);
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
        System.out.println("Adatlekérdezés történt");
        
        return result;
    }
    public ArrayList<Szemely> gyerekKereso(String szemelyiSzam){
        ArrayList<Szemely> result = new ArrayList<>();
        ResultSet rs;
        
        if(this.conn != null){
            String sql = "SELECT * FROM szulok LEFT JOIN szemelyek ON gyerekId=szemelyek.szemelyiszam WHERE anyaId LIKE'%" + szemelyiSzam +
                    "%' OR apaId LIKE '%" + szemelyiSzam + "%';";
    
            Statement stmt = null;
            
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        Szemely sz = new Szemely(rs.getString("szemelyiszam"), rs.getString("nev"), 
                                rs.getBoolean("lany"), rs.getString("szulhely"), rs.getString("szulido"), 
                                rs.getString("anyaId"), rs.getString("apaId"), rs.getString("halalido"), rs.getString("halalhely"));

                        result.add(sz);
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
        
        System.out.println("Adatlekérdezés történt");
        
        return result;
    }
    public ArrayList<Szemely> testverKereso(String anyaId, String apaId, String gyerekId){
        ArrayList<Szemely> result = new ArrayList<>();
        ResultSet rs;
        
        if(this.conn != null){
            String sql = "SELECT * FROM szulok LEFT JOIN szemelyek ON gyerekId=szemelyek.szemelyiszam WHERE (anyaId LIKE '" + anyaId + "' OR anyaId LIKE '" + apaId 
                    + "' OR apaId LIKE '" + anyaId +"' OR apaId LIKE '" + apaId + "') AND gyerekId NOT LIKE '" + gyerekId + "';";
    
            Statement stmt = null;
            
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        Szemely sz = new Szemely(rs.getString("szemelyiszam"), rs.getString("nev"), 
                                rs.getBoolean("lany"), rs.getString("szulhely"), rs.getString("szulido"), 
                                rs.getString("anyaId"), rs.getString("apaId"), rs.getString("halalido"), rs.getString("halalhely"));

                        result.add(sz);
                        System.out.println(sz);
                    }
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            } 
        }
        
        System.out.println("Adatlekérdezés történt");
        
        return result;
    }
    
    public void ujSzemely(Szemely sz){
        //Új személy létrehozása az adatázisban
        int lany = (sz.isLany()) ? 1 : 0;
        
        if(this.conn != null){
            String sql = "INSERT INTO `szemelyek` (`szemelyiszam`, `nev`, `szulhely`, `szulido`, `halalhely`, `halalido`, `lany`) " + 
                        "VALUES('" + sz.getSzemelyiSzam() + "', '" + sz.getNev() + "', '" + sz.getSzulhely() + "', '" + sz.getSzulido() + 
                        "', '" + sz.getHalalhely() + "', '" + sz.getHalalido() + "', " + lany + ");";
            
            Statement stmt = null;
            
            try {
                stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    stmt.executeUpdate(sql);
                } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
            }
            
            
            if(!(sz.getAnya().equals("")) || !(sz.getApa().equals(""))){
                String gyerekId = sz.getSzemelyiSzam();
                String anyaId = sz.getAnya();
                String apaId = sz.getApa();
            
                if(this.conn != null){
                    String sql2 = "INSERT INTO `szulok` (`gyerekId`, `anyaId`, `apaId`) VALUES('" + gyerekId + "', '" + anyaId + "', '" + apaId + "');";

                    Statement stmt2 = null;

                    try {
                        stmt2 = conn.createStatement();
                    } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }

                        if(stmt2 != null){
                            try {    
                                stmt2.executeUpdate(sql2);
                            } catch(SQLException ex){ System.out.println("Baj van! Hiba a query futtatásánál! " + ex); }
                        } 
                }
            }
        
            
        }
        
    }
    
}
