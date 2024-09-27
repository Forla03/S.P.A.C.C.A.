package application.CLASSI.CarteEquipaggiamento;

import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class LaureaDiApollo extends CartaEquipaggiamento{
	
	public LaureaDiApollo(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
	
	public void effetto(Utente u,CartaPersonaggio c) {
		int x=ControllerCreazionePartita.p.getPersonaggiScelti().get(u).size()*100;
		c.addAtt(x);
		attAggiuntoPrec=x;
		psAggiuntiPrec=0;		
	}
	
	public String getRichiestaEffetto() {
		return "Clicca su un personaggio";
	}
	
	public void ricalcolo(Utente u,CartaPersonaggio c) {
		int x=ControllerCreazionePartita.p.getPersonaggiScelti().get(u).size()*100;		
		attAggiuntoPrec=x;
		psAggiuntiPrec=0;		
	}
}
