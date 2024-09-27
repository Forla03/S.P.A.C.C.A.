package application.CLASSI.CarteEffetto;

import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;
import java.util.Random;

public class IngannoDiAnubi extends CartaEffetto {  //la carta può non rimuovere niente anche se ci sono altri personaggi con carte equip
	
	public IngannoDiAnubi(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);    
       
    }
	
	public void effettoEffetto1(Utente u, CartaPersonaggio c){
       Random random=new Random();
       if(!c.getEquip().isEmpty()) {
       int x=random.nextInt(c.getEquip().size());      
    	   if(c.getEquip().get(x).getMitologia().equals(TipiMitologie.EGIZIA)&&ControllerCreazionePartita.p.getMano().get(u).size()<9) {
    		   ControllerCreazionePartita.p.getMano().get(u).add(c.getEquip().remove(x));
    	   }
    	   else {
    		   c.getEquip().remove(x);
    	   }
       }
       else {
    	   System.out.println("Nessun equipaggiamento");
       }
	}
}

