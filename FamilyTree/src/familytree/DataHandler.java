/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familytree;

import java.util.ArrayList;

/**
 *
 * @author szeke
 */
public class DataHandler {
    
        private String url = "jdbc:mysql://localhost:3306/";
        private String databaseName = "familytree";
        private String user = "user";
        private String pass = "Almafa12;";
        private Database db;
        private boolean connected = false;
        public Database test = new Database(url, databaseName, user, pass);

    public DataHandler() {
        this.db = new Database(url, databaseName, user, pass);
        this.connected = this.db.isConnected();
        
    }

    public boolean isConnected() {
        return connected;
    }
    
    public boolean updateSzemely(Szemely sz, boolean voltSzulo){
        return this.db.updateSzemely(sz, voltSzulo);
    }
    
    public Szemely szemelyLekerdezes(String keresoSzoveg){
        return this.db.szemelyLekerdezes(keresoSzoveg);
    }
    
    public void ujSzemely(Szemely sz){
        this.db.ujSzemely(sz);
    }
    
    public ArrayList<Szemely> nevSzamKereso(String keresoSzoveg){
        return this.db.nevSzamKereso(keresoSzoveg);
    }
    
    
        
        
        
    
}
