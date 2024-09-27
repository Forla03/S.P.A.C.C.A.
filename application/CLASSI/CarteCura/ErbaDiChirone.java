package application.CLASSI.CarteCura;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;


public class ErbaDiChirone extends CartaCura{
	
	public ErbaDiChirone(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
        setTarget(3);
    }
	
	public void effetto3(Utente u) {
		for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u) ) {
			c.addPs(150);
		}
	}	
}
