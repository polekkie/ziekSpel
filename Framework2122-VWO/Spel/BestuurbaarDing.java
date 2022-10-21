package Spel;
import Tools.*;
import java.awt.Image;
import java.awt.Graphics2D;

/**
 * class BestuurbaarDing - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class BestuurbaarDing extends BotsObject{
    public Image plaatje;
    public Omgeving omgeving;
    public int links, rechts, boven, beneden;
    public float g;
    
    public BestuurbaarDing(int x, int y, float v, Image plaatje){
        super(x, y, 30, 30);
        vx = v;
        vy = v;
        this.plaatje = plaatje;
        g = 10;
        bewaar();
    }
    
    public void registreer(Omgeving o){
        omgeving = o;
    }
    
    public void defineerToetsen(int links, int rechts, int boven, int beneden){
        this.links = links;
        this.rechts = rechts;
        this.boven = boven;
        this.beneden = beneden;
    }
    
    public void beweeg(float stap){
        bewaar();
        if(omgeving.kb.isIngedrukt(links)){
            x -= vx*stap;
        }
        if(omgeving.kb.isIngedrukt(rechts)){
            x += vx*stap;
        }
        if(omgeving.kb.isIngedrukt(boven)){
            vy = -300;
        }
        if(omgeving.kb.isIngedrukt(beneden)){
            //y += vy*stap;
        }
        
        vy += g;
        y += vy*stap;
        
        if(botstMet(omgeving.bd1) && omgeving.bd1 != this){
            if(ikKomVan(omgeving.bd1).equals("links")){
                omgeving.bd1.x = x + breedte;
            }
            if(ikKomVan(omgeving.bd1).equals("rechts")){
                omgeving.bd1.x = x - omgeving.bd1.breedte;
            }
            if(ikKomVan(omgeving.bd1).equals("boven")){
                zetyTerug();
                vy = 0;
            }
        }
        
        if(botstMet(omgeving.bd2) && omgeving.bd2 != this){
            if(ikKomVan(omgeving.bd2).equals("links")){
                omgeving.bd2.x = x + breedte;
            }
            if(ikKomVan(omgeving.bd2).equals("rechts")){
                omgeving.bd2.x = x - omgeving.bd2.breedte;
            }
            if(ikKomVan(omgeving.bd2).equals("boven")){
                zetyTerug();
                vy = 0;
            }
        }
        
        /*int teller = 0;
        while(teller < omgeving.specialeBeweegObjecten.size()){
            BewegendDingExtra be = omgeving.specialeBeweegObjecten.get(teller);
            if(botstMet(be)){
                omgeving.specialeBeweegObjecten.remove(be);
                omgeving.tekenaar.verwijderObject(be);
                omgeving.beweeg.verwijderObject(be);
            } else{
                teller ++;
            }
        }
        */
       
        /*
        teller = 0;
        while(teller <  omgeving.obstakels.size()){
            Obstakel o = omgeving.obstakels.get(teller);
            if(botstMet(o)){
                if(ikKomVan(o).equals("links") || ikKomVan(o).equals("rechts")){
                    zetxTerug();
                }
                if(ikKomVan(o).equals("boven") || ikKomVan(o).equals("beneden")){
                    zetyTerug();
                }
            }
            teller ++;
        }
        */
        int teller = 0;
        while(teller <  omgeving.vloeren.size()){
            Obstakel o = omgeving.vloeren.get(teller);
            if(botstMet(o)){
                if(ikKomVan(o).equals("links") || ikKomVan(o).equals("rechts")){
                    zetxTerug();
                }
                if(ikKomVan(o).equals("boven") || ikKomVan(o).equals("beneden")){
                    zetyTerug();
                    vy = 0;
                }
            }
            teller ++;
        }
        
        teller = 0;
        while(teller <  omgeving.muren.size()){
            Obstakel o = omgeving.muren.get(teller);
            if(botstMet(o)){
                if(ikKomVan(o).equals("links") || ikKomVan(o).equals("rechts")){
                    zetxTerug();
                }
                if(ikKomVan(o).equals("boven") || ikKomVan(o).equals("beneden")){
                    zetyTerug();
                    vy = 0;
                }
            }
            teller ++;
        }
        teller = 0;
        while(teller < omgeving.beweegObjecten.size()){
            BewegendDing b = omgeving.beweegObjecten.get(teller);
            if(botstMet(b)){
                if(ikKomVan(b).equals("links")){
                    b.x = x + breedte; 
                }
                if(ikKomVan(b).equals("rechts")){
                    b.x = x - breedte; 
                }
                if(ikKomVan(b).equals("boven") || ikKomVan(b).equals("beneden")){
                    zetyTerug();
                    vy = 0;
                }
            }
            teller ++;
        }
    }
    
    public void teken(Graphics2D g){
        g.drawImage(plaatje, (int)x, (int)y, breedte, hoogte, null);
    }
}
