package Spel;
import java.awt.Image;
import java.awt.Graphics2D;


/**
 * class Achtergrond - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Achtergrond extends SpelObject{
    public Image plaatje;
    
    public Achtergrond(int breedte, int hoogte, Image plaatje){
        super(0, 0, breedte, hoogte, null);
        this.plaatje = plaatje;
    }
    
    public void teken(Graphics2D g){
        g.drawImage(plaatje, (int)x, (int)y, breedte, hoogte, null);
    }
}
