package application.CLASSI.CarteEffetto;

import java.util.Iterator;

import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;


public class OmbraDiAde extends CartaEffetto {
	
	public OmbraDiAde(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);    
        target=2;
    }
	
	public void effettoEffetto2(CartaPersonaggio c, Utente u1, Utente u2,Runnable callback){
	    Iterator<CartaEquipaggiamento> iterator = c.getEquip().iterator();   //se la carta equip non è greca viene rubata
	    while (iterator.hasNext()) {
	        CartaEquipaggiamento e = iterator.next();
	        if(ControllerCreazionePartita.p.getMano().get(u1).size()<8 && !e.getMitologia().equals(TipiMitologie.GRECA)) {
	            ControllerCreazionePartita.p.getMano().get(u1).add(e);
	            iterator.remove();
	        }
	        else
	            iterator.remove();
	    }
	}
}