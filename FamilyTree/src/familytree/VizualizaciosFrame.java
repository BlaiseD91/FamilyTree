/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package familytree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author szeke
 */
public class VizualizaciosFrame extends JFrame{

    private Szemely szemely;
    private ArrayList<Szemely> szulok;
    private ArrayList<Szemely> testverek;
    private ArrayList<Szemely> gyerekek;
    private int state = 0;
    
    public VizualizaciosFrame(Szemely szemely, ArrayList<Szemely> szulok, ArrayList<Szemely> testverek, ArrayList<Szemely> gyerekek){
        this.szemely = szemely;
        this.szulok = szulok;
        this.testverek = testverek;
        this.gyerekek = gyerekek;
        setTitle("Családfa");
        setSize(1500, 600);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        
    }
    
    public void paint(Graphics g){
        //Személy kirajzolása
        g.setColor(Color.BLUE);
        g.drawRect(10, 350, 200, 50);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.BLACK);
        g.drawString(this.szemely.getNev(), 20, 380);
        //Szulok kirajzolása
        System.out.println(szulok.get(0));
        System.out.println(szulok.get(1));
        if(szulok.get(0).getNev() != null || szulok.get(1).getNev() != null){
            g.setColor(Color.BLACK);
            g.drawLine(105, 400, 105, 450);
            g.drawLine(105, 450, 1395, 450);
            if(this.szemely.getAnya() != null){//430
                g.drawLine(535, 450, 535, 500);
                g.setColor(Color.RED);
                g.drawRect(435, 500, 200, 50);
                g.setColor(Color.BLACK);
            }
            if(this.szemely.getApa() != null){
                g.setColor(Color.BLACK);
                g.drawLine(965, 450, 965, 500);
                g.setColor(Color.RED);
                g.drawRect(865, 500, 200, 50);
            }
        }
        //Testvérek kirajzolása
        if(testverek.size() > 0){
            int x = 210;
            int y = 350;
            g.setColor(Color.BLACK);
            g.drawLine(105, 450, 1395, 450);
            g.drawLine(105, 400, 105, 450);
            for (Szemely teso : testverek) {
                g.setColor(Color.GREEN);
                x += 50;
                g.drawRect(x, y, 200, 50);
                x += 200;
                g.setColor(Color.BLACK);
                g.drawLine(x-100, 400, x-100, 450);
                g.drawString(teso.getNev(), x-190, 380);
            }  
        }
        //Gyerekek kirajzolása
        if(gyerekek.size() > 0){
            int x = 105;
            int y = 200;
            g.setColor(Color.BLACK);
            g.drawLine(105, 350, 105, 300);
            g.drawLine(105, 300, 1395, 300);
            for (Szemely gy : gyerekek) {
                g.setColor(Color.ORANGE);
                x += 50;
                g.drawRect(x, y, 200, 50);
                x += 200;
                g.setColor(Color.BLACK);
                g.drawLine(x-100, 250, x-100, 300);
                g.drawString(gy.getNev(), x-190, 230);
            }
        }
    }
    
}
