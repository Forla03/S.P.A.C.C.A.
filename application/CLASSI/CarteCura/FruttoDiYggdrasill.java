package application.CLASSI.CarteCura;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import javafx.scene.image.Image;

public class FruttoDiYggdrasill extends CartaCura{
	
	public FruttoDiYggdrasill(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
	
	public void effetto(Utente u,CartaPersonaggio c) {
		int x=Math.round(c.getAtt()/10);
		c.addPs(x);
	}
	
	public String getRichiestaEffetto() {
		return "Clicca su un personaggio";
	}
}