package application.CLASSI.CarteImprevisto;

import application.CLASSI.CartaImprevisto;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;

public class Ragnarok extends CartaImprevisto{
	
	public Ragnarok(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);
    }
	
	public void effetto3(Utente u) {
	    int contaNorreni=0;
	    for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
	    	if(c.getMitologia().equals(TipiMitologie.NORRENA))
	    		contaNorreni+=1;
	    }
     	 for(CartaPersonaggio c:ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
     		 if(contaNorreni>=2)
     			 c.addAtt(800);
     		 else
     			 c.lessPs(800);
     		if(c.getPsAttuali()<=0)
	    		c.setPsAttuali(1);
     	 }   
	}
}
