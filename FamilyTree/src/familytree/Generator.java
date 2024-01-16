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
    
    
    public static String generateSzemelyiSzam(boolean lany, String szulido ){
        int szulEv = Integer.parseInt(szulido.split("-", 3)[0]);
        
        
        String m;
        String ee = szulido.split("-", 3)[0].substring(szulido.split("-", 3)[0].length() - 2);
        String hhnn = szulido.split("-", 3)[1] + szulido.split("-", 3)[2];
        
        int sss = (int)((Math.random()*999) + 1);
        
        if(szulEv >= 1900 && szulEv <= 1999){
            if(lany) m = "2";
            else m = "1";
        }
        else{
            if(lany) m = "4";
            else m = "3";
        }

        String szemelyiSzam = m + ee + hhnn + Integer.toString(sss) + "0";
        
        /*Még nem teljesen egy valid személyi számot ad vissza, az utolsó elleõrzõ számot még generálni kellene*/
        
        return szemelyiSzam;
    }
    
    
}
