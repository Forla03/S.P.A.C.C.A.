package application.CLASSI.CarteCura;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import javafx.scene.image.Image;

public class GraziaDiApollo extends CartaCura{
	
	public GraziaDiApollo(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
	
	public void effetto(Utente u,CartaPersonaggio c) {
		int x=c.getAtt();
		if(x>c.getPsAttuali()*2) {
			x=c.getPsAttuali()*2;
		}
		c.addPs(x);			
	}
	
	public String getRichiestaEffetto() {
		return "Clicca su un personaggio";
	}
}