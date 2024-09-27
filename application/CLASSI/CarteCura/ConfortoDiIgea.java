package application.CLASSI.CarteCura;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import javafx.scene.image.Image;

public class ConfortoDiIgea extends CartaCura{
	
	public ConfortoDiIgea(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
        setTarget(2);
    }
	
	public void effetto2(Utente u,CartaPersonaggio c1,CartaPersonaggio c2) {
		c1.addPs(500);
		c2.addPs(500);
	}
	
	public String getRichiestaEffetto() {
		return "Clicca su due personaggi";
	}
}
