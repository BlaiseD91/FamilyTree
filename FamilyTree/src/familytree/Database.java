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
        
        if(this.conn==null){
            try {
                this.conn = DriverManager.getConnection(this.url, "root", "");
                Statement stmt = this.conn.createStatement();
                String sql = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci";
                stmt.executeUpdate(sql);
                this.conn = DriverManager.getConnection(this.url+this.databaseName, "root", "");
                createTables();
                System.out.println("...de létrehoztam! ");
                this.conn = DriverManager.getConnection(this.url+this.databaseName, "root", "");
                uploadDatas();
                
            } catch(SQLException ex) { System.out.println("Hiba! Nem is tudom létrehozni! " + ex);}
        }
        
        if(this.conn != null) System.out.println("Sikeres kapcsolódás");
        
    }
    
    private void createTables(){
        if(conn != null){
            Statement stmt = null;
            String sql = "CREATE TABLE IF NOT EXISTS `szemelyek` (" +
                "  `szemelyiszam` varchar(11) COLLATE utf8_hungarian_ci NOT NULL," +
                "  `nev` varchar(50) COLLATE utf8_hungarian_ci NOT NULL," +
                "  `szulhely` varchar(35) COLLATE utf8_hungarian_ci NOT NULL," +
                "  `szulido` varchar(10) COLLATE utf8_hungarian_ci NOT NULL," +
                "  `halalhely` varchar(35) COLLATE utf8_hungarian_ci DEFAULT NULL," +
                "  `halalido` varchar(10) COLLATE utf8_hungarian_ci DEFAULT NULL," +
                "  `lany` tinyint(1) NOT NULL," +
                "  PRIMARY KEY (`szemelyiszam`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;";
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    stmt.execute(sql);
                } catch(SQLException ex){
                    System.out.println("Baj van! Hiba a CreateTable-nél! " + ex);
                }
            }
            Statement stmt2 = null;
            sql = "CREATE TABLE IF NOT EXISTS `szulok` (" +
                "  `gyerekId` varchar(11) COLLATE utf8_hungarian_ci NOT NULL," +
                "  `anyaId` varchar(11) COLLATE utf8_hungarian_ci NOT NULL," +
                "  `apaId` varchar(11) COLLATE utf8_hungarian_ci NOT NULL," +
                "  PRIMARY KEY (`gyerekId`)," +
                "  KEY `anyaId` (`anyaId`)," +
                "  KEY `apaId` (`apaId`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;";
            
            try { stmt2 = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt2 != null){
                try {    
                    stmt2.execute(sql);
                } catch(SQLException ex){
                    System.out.println("Baj van! Hiba a CreateTable-nél! " + ex);
                }
            }
        }
    }
    private void uploadDatas(){
        if(conn != null){
            Statement stmt = null;
            String sql = "INSERT INTO `szemelyek` (`szemelyiszam`, `nev`, `szulhely`, `szulido`, `halalhely`, `halalido`, `lany`) VALUES" +
                "('11952-05-15', 'Mézga Paula', 'Budapest', '1952-05-15', '', '', 1)," +
                "('12000-12-25', 'Parlagi Anna', 'Fót', '2000-12-25', '', '', 1)," +
                "('12010-04-11', 'Minta Mária', 'Szeged', '2010-04-11', '', '', 1)," +
                "('21948-07-19', 'Mézga Géza', 'Budapest', '1948-07-19', 'Fót', '2010-02-11', 0)," +
                "('21951-04-13', 'Karacs Ilona', 'Pécs', '1951-04-13', '', '', 1)," +
                "('21972-05-28', 'Kele Tünde', 'Gyõr', '1972-05-28', '', '', 0)," +
                "('21974-12-12', 'Gerebély András', 'Gyõr', '1974-12-12', '', '', 0)," +
                "('21977-02-28', 'Mézga Viktor', 'Budapest', '1977-02-28', '', '', 0)," +
                "('21988-11-20', 'Minta Géza', 'Mintaváros', '1988-11-20', '', '', 0)," +
                "('21990-10-09', 'Kiss Gertrúd', 'Sopon', '1990-10-09', '', '', 0)," +
                "('26708307630', 'Cint Ibolya', 'Maglód', '1967-08-30', '', '', 1);";
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    stmt.execute(sql);
                } catch(SQLException ex){
                    System.out.println("Baj van! Hiba itt: UploadDatas: szemelyek! " + ex);
                }
            }
            stmt = null;
            sql = "INSERT INTO `szulok` (`gyerekId`, `anyaId`, `apaId`) VALUES" +
                "('11952-05-15', '21990-10-09', '21988-11-20')," +
                "('12010-04-11', '21990-10-09', '21988-11-20')," +
                "('21948-07-19', '', '')," +
                "('21951-04-13', '', '')," +
                "('21972-05-28', '', '21948-07-19')," +
                "('21974-12-12', '', '21948-07-19')," +
                "('21977-02-28', '', '21948-07-19')," +
                "('21988-11-20', '', '21948-07-19');";
            
            try { stmt = conn.createStatement();
            } catch (SQLException ex) { System.out.println("Baj van! Hiba a statement létrehozásában! " + ex); }
            
            if(stmt != null){
                try {    
                    stmt.execute(sql);
                } catch(SQLException ex){
                    System.out.println("Baj van! Hiba itt: uploadDatas: szulok! " + ex);
                }
            }
        }
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
