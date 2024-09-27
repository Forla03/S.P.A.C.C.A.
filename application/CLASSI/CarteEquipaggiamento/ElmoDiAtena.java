package application.CLASSI.CarteEquipaggiamento;

import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class ElmoDiAtena extends CartaEquipaggiamento{
	
	public ElmoDiAtena(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
        target=2;
    }
	
	public int effettoRiduzioneDanno(int x,Utente u) {
		x=0;
		for(CartaPersonaggio cp:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
			x+=200;
		}
		return x;
	}	
	
	public String getRichiestaEffetto() {
		return "Clicca su un personaggio";
	}
}
