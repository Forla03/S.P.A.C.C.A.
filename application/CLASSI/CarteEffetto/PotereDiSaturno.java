package application.CLASSI.CarteEffetto;

import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import javafx.scene.image.Image;


public class PotereDiSaturno extends CartaEffetto {
	
	public PotereDiSaturno(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);    
    }
	
	public void effettoEffetto1(Utente u, CartaPersonaggio c){
     if(c.getMitologia().equals(TipiMitologie.ROMANA)) {
    	 c.lessAtt(700);
     }
     else
    	 c.lessAtt(500);
	}
}


