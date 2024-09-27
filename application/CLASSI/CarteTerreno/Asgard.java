package application.CLASSI.CarteTerreno;

import application.CLASSI.CartaTerreno;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class Asgard extends CartaTerreno{
	
	public Asgard(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
	
	public int effettoRiduzioneDannoTerreno(CartaPersonaggio c) {
		if(c.getMitologia().equals(TipiMitologie.NORRENA))
			return 500;
		else
			return 0;
	}	
}