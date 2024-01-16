/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familytree;

import java.time.LocalDate;

/**
 *
 * @author szeke
 */
public final class Generator {

    private LocalDate today = LocalDate.now();
    
    private Generator() {
        
    }
    
    public static String generateSzemelyiSzam(boolean lany, String szulido ){
        String elsoSzam = (lany) ? "1" : "2";
        String szemelyiSzam = elsoSzam + szulido;
        
        return szemelyiSzam;
    }
    
    
}
