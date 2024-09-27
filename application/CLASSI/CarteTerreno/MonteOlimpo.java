package application.CLASSI.CarteTerreno;

import application.CLASSI.CartaTerreno;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class MonteOlimpo extends CartaTerreno{
	
	public MonteOlimpo(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
	
	public void effetto3(Utente u) {
		for(CartaPersonaggio c: ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
			if(c.getMitologia().equals(TipiMitologie.GRECA)) {
				c.addPs(250);
			    c.addAtt(250);
			}
		}	
	}
}
