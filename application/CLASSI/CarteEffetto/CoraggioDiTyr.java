package application.CLASSI.CarteEffetto;

import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import javafx.scene.image.Image;


public class CoraggioDiTyr extends CartaEffetto {
	
	public CoraggioDiTyr(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);    
        target=2;
    }
	
	public void effettoEffetto2(CartaPersonaggio c,Utente u1, Utente u2,Runnable callback) {
		if(c.getMitologia().equals(TipiMitologie.NORRENA)) {
    	 c.lessPs(500);
        }
		else
			c.lessPs(300);
	}
}