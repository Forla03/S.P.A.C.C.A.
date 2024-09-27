package application.CLASSI.CarteEffetto;

import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class BenedizioneDiBaldur extends CartaEffetto {
	
	public BenedizioneDiBaldur(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);    
        target=3;
    }
	
	 public void effetto3(Utente u) {		 
	    for(CartaPersonaggio cp:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	    	cp.addPs(500);
	    	if(cp.getMitologia().equals(TipiMitologie.NORRENA)) {
	    		for(CartaPersonaggio cp2:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	    			cp2.addPs(100);
	    		}
	    	}
	    }
	}
}
