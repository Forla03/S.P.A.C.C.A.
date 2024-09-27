package application.CLASSI.CarteEquipaggiamento;

import application.CLASSI.CartaEquipaggiamento;
//import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
//import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class ScudoDiMinerva extends CartaEquipaggiamento{
	
	public ScudoDiMinerva(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
        target=2;
    }
	
	public int effettoRiduzioneDanno(int x,Utente u) {
		return Math.round(x/100*20);
	}	
	
	public String getRichiestaEffetto() {
		return "Clicca su un personaggio";
	}
}
