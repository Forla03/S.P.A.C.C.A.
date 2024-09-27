package application.CLASSI.CarteEffetto;

import java.util.List;

import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;


public class GiudizioDiGiove extends CartaEffetto {
	
	public GiudizioDiGiove(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);  
        target=4;
    }
	
    public void effettoEffetto3(CartaPersonaggio c,List<Utente> utenti){
		int x=0;
		for(Utente u:utenti) {
			for(CartaPersonaggio cp:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
				if(cp.getMitologia().equals(TipiMitologie.ROMANA))
					x+=500;
				else
					x+=300;
			}
		}
    	c.addAtt(x);
	 }
	
	public String getRichiestaEffetto() {
		return "Clicca su un personaggio";
	}
}