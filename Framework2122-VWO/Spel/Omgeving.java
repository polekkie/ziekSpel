
package Spel;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import Tools.*;
import java.awt.Color;
import java.awt.Image;

/**
 * In deze klasse maak je de objecten die de omgeving van het spel vormen
 * Dat kunnen enkel objecten zijn 
 * Maar dat kunnen ook lijsten van objecten zijn
 * Of zelfs lege lijsten
 * Voor lijsten maken we gebruik van de ArrayList
 *
 * @author (E.E.Koldenhof)
 * @version (V1.0)
 */
public class Omgeving {
    public int breedte, hoogte;
    public Tekenaar tekenaar;
    public Beweeg beweeg;
    public Keyboard kb;
    public Mouse muis;
    /**
     * Declareer hieronder alle objecten die je in je spel nodig hebt
     * Bijvoorbeeld:
     * public Ding d;
     * public ArrayList<AnderDing> andereDingen
     * enz.
     */
    public Achtergrond achtergrond;
    public BestuurbaarDing bd1, bd2;
    ArrayList<Lift> liften;
    public ArrayList<Obstakel> obstakels; 
    public ArrayList<Obstakel> vloeren;
    public ArrayList<Obstakel> muren;
    public ArrayList<BewegendDing> beweegObjecten;
    public ArrayList<BewegendDingExtra> specialeBeweegObjecten;
    
    /**
     * Constructor voor de objecten van de klasse Omgeving
     * @param breedte, is de breedte van het zichtbare spel
     */
    public Omgeving(int breedte, int hoogte, Tekenaar t, Beweeg b, Keyboard k, Mouse m){
        this.breedte = breedte;
        this.hoogte = hoogte;
        this.tekenaar = t;
        this.beweeg = b;
        kb = k;
        muis = m;
        // geef hier de opdracthen om de methoden uit te voeren
        // die de objecten maken.
        maakAchtergrond();
        maakBestuurbareDingen();
        maakLiften();
        maakVloeren(5);
        
        maakObstakels();
        maakMuren();
        maakBeweegObjecten();
        maakSpecialeBeweegObjecten();
        voegAanTekenaarToe();
        voegAanBeweegToe();
    }
    
    public int maakGetal(int min, int max){
        return (int)Math.random()*(max - min) + min;
    }
    
    public void maakBestuurbareDingen(){
        bd1 = new BestuurbaarDing(400, 400, 100, Laden.laadPlaatje("plaatjes/druif.jpg"));
        bd1.registreer(this);
        bd1.defineerToetsen(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        bd2 = new BestuurbaarDing(400, 400, 100, Laden.laadPlaatje("plaatjes/appel.jpg"));
        bd2.defineerToetsen(KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S);
        bd2.registreer(this);
    }
    
    public void maakLiften(){
        liften = new ArrayList<Lift>();
        String[] regels = Laden.laadTextFile("omgevingen/liften.txt").split("\n");
        int teller = 0;
        while(teller < regels.length){
            int x = Integer.parseInt(regels[teller].split(",")[0].replaceAll(" ", ""));
            int y = Integer.parseInt(regels[teller].split(",")[1].replaceAll(" ", ""));
            int br = Integer.parseInt(regels[teller].split(",")[2].replaceAll(" ", ""));
            int v = Integer.parseInt(regels[teller].split(",")[3].replaceAll(" ", ""));
            int r = Integer.parseInt(regels[teller].split(",")[4].replaceAll(" ", ""));
            int g = Integer.parseInt(regels[teller].split(",")[5].replaceAll(" ", ""));
            int b = Integer.parseInt(regels[teller].split(",")[6].replaceAll(" ", ""));
            liften.add(new Lift(hoogte, x, y, br, v, r, g, b, this));
            teller ++;
        }
    }
    
    
    
    public void maakVloeren(int aantal){
        vloeren = new ArrayList<Obstakel>();
        vloeren.add(new Obstakel(0, hoogte - 10, breedte, 10, Color.black));
        vloeren.add(new Obstakel(breedte/2 - 50, hoogte/2 - 10, 100, 10, Color.black)); 
        int teller = 0;
        while(teller < aantal){
            vloeren.add(new Obstakel(teller*100, hoogte - 50*teller, 50, 10, Color.black));
            teller ++;
        }
        int x_begin = teller*100;
        int y_begin = hoogte - 50*teller;
        teller = 0;
        while(teller < aantal){
            vloeren.add(new Obstakel(x_begin + teller*100, y_begin + 50*teller, 50, 10, Color.black));
            teller ++;
        }
        vloeren.add(new Obstakel(12, 450, 167, 10, Color.green));
    }
    
    
    public void maakMuren(){
        muren = new ArrayList<Obstakel>();
        String[] regels = Laden.laadTextFile("omgevingen/muren.txt").split("\n"); 
        int teller = 0;
        while(teller < regels.length){
            int x = Integer.parseInt(regels[teller].split(",")[0].replaceAll(" ", ""));
            int y = Integer.parseInt(regels[teller].split(",")[1].replaceAll(" ", ""));
            int r = Integer.parseInt(regels[teller].split(",")[2].replaceAll(" ", ""));
            int g = Integer.parseInt(regels[teller].split(",")[3].replaceAll(" ", ""));
            int b = Integer.parseInt(regels[teller].split(",")[4].replaceAll(" ", ""));
            muren.add(new Obstakel(x, y, 10, 50, new Color(r, g, b)));
            teller ++;
        }
    }
    
    public void maakSpecialeBeweegObjecten(){
        specialeBeweegObjecten = new ArrayList<BewegendDingExtra>();
        Image p = Laden.laadPlaatje("plaatjes/peer.jpg");
        specialeBeweegObjecten.add(new BewegendDingExtra(100, 300, 100, breedte, p));
        specialeBeweegObjecten.add(new BewegendDingExtra(100, 350, 200, breedte, p));
        specialeBeweegObjecten.add(new BewegendDingExtra(100, 400, -100, breedte, p));
        specialeBeweegObjecten.add(new BewegendDingExtra(100, 450, -200, breedte, p));
        int teller = 0;
        while(teller < 10){
            specialeBeweegObjecten.add(new BewegendDingExtra(maakGetal(200, 400), maakGetal(200, 600), maakGetal(-200, 200), breedte, p));
            teller ++;
        }
    }
    
    public void maakBeweegObjecten(){
        beweegObjecten = new ArrayList<BewegendDing>();
        beweegObjecten.add(new BewegendDing(100, 200, 30, 30, 0, 100, this));
        beweegObjecten.add(new BewegendDing(200, 200, 30, 30, 0, 100, this));
        beweegObjecten.add(new BewegendDing(300, 200, 30, 30, 0, 100, this));
        beweegObjecten.add(new BewegendDing(400, 200, 30, 30, 0, 100, this));
        beweegObjecten.add(new BewegendDing(500, 200, 30, 30, 0, 100, this));
        beweegObjecten.add(new BewegendDing(600, 200, 30, 30, 0, 100, this));
    }
    
    public void maakAchtergrond(){
        achtergrond = new Achtergrond(breedte, hoogte, Laden.laadPlaatje("plaatjes/achtergrond.jpg"));
    }
    
    public void maakObstakels(){
        obstakels = new ArrayList<Obstakel>();
        obstakels.add(new Obstakel(100, 100, 30, 30, Color.blue));
        obstakels.add(new Obstakel(200, 100, 30, 30, Color.blue));
        obstakels.add(new Obstakel(300, 100, 30, 30, Color.blue));
        obstakels.add(new Obstakel(400, 100, 30, 30, Color.blue));
        obstakels.add(new Obstakel(500, 100, 30, 30, Color.blue));
    }
    
    /**
     * Deze procedure voegt de gemaakte objecten aan de
     * tekenlijst van de Tekenaar toe:
     * tekenaar.voegObjectToe(een gemaakt object)
     * tekenaar.voegLijstToe(een gemaakte en gevulde lijst)
     *
     */
    private void voegAanTekenaarToe(){
        tekenaar.voegObjectToe(achtergrond);
        tekenaar.voegLijstToe(obstakels);
        tekenaar.voegLijstToe(beweegObjecten);
        tekenaar.voegLijstToe(specialeBeweegObjecten);
        tekenaar.voegObjectToe(bd1);
        tekenaar.voegObjectToe(bd2);
        tekenaar.voegLijstToe(vloeren);
        tekenaar.voegLijstToe(muren);
        tekenaar.voegLijstToe(liften);
    }
    
    /**
     * Deze procedure voegt de gemaakte objecten aan de
     * beweeglijst van Beweeg toe:
     * beweeg.voegObjectToe(een gemaakt object)
     * beweeg.voegLijstToe(een gemaakte en gevulde lijst)
     *
     */

    private void voegAanBeweegToe(){
        beweeg.voegLijstToe(beweegObjecten);
        beweeg.voegLijstToe(specialeBeweegObjecten);
        beweeg.voegObjectToe(bd1);
        beweeg.voegObjectToe(bd2);
        beweeg.voegLijstToe(liften);
    }

}
