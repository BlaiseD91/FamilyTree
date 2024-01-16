/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familytree;

/**
 *
 * @author szeke
 */
public class Szemely {
    
    private String szemelyiSzam;
    private String nev;
    private boolean lany;
    private String szulhely;
    private String szulido;
    private String anya;
    private String apa;
    private String halalido;
    private String halalhely;

    public Szemely(String szemelyiSzam, String nev, boolean lany, String szulhely, String szulido, String anya, String apa, String halalido, String halalhely) {
        this.szemelyiSzam = szemelyiSzam;
        this.nev = nev;
        this.lany = lany;
        this.szulhely = szulhely;
        this.szulido = szulido;
        this.anya = anya;
        this.apa = apa;
        this.halalido = halalido;
        this.halalhely = halalhely;
    }

    public Szemely() {
    }
    
    
    

    @Override
    public String toString() {
        return "Szemely{" + "szemelyiSzam=" + szemelyiSzam + ", nev=" + nev + ", lany=" + lany + ", szulhely=" + szulhely + ", szulido=" + szulido + ", anya=" + anya + ", apa=" + apa + ", halalido=" + halalido + ", halalhely=" + halalhely + '}';
    }

    
    
    public String getSzemelyiSzam() {
        return szemelyiSzam;
    }

    public String getNev() {
        return nev;
    }

    public boolean isLany() {
        return lany;
    }

    public String getSzulhely() {
        return szulhely;
    }

    public String getSzulido() {
        return szulido;
    }

    public String getAnya() {
        return anya;
    }

    public String getApa() {
        return apa;
    }

    public String getHalalido() {
        return halalido;
    }

    public String getHalalhely() {
        return halalhely;
    }

    public void setSzemelyiSzam(String szemelyiSzam) {
        this.szemelyiSzam = szemelyiSzam;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setLany(boolean lany) {
        this.lany = lany;
    }

    public void setSzulhely(String szulhely) {
        this.szulhely = szulhely;
    }

    public void setSzulido(String szulido) {
        this.szulido = szulido;
    }

    public void setAnya(String anya) {
        this.anya = anya;
    }

    public void setApa(String apa) {
        this.apa = apa;
    }

    public void setHalalido(String halalido) {
        this.halalido = halalido;
    }

    public void setHalalhely(String halalhely) {
        this.halalhely = halalhely;
    }
    
    
    
    
    
}
