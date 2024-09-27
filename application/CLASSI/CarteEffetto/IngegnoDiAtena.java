package application.CLASSI.CarteEffetto;

import java.util.ArrayList;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaGiocabile;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;


public class IngegnoDiAtena extends CartaEffetto {
	
	public IngegnoDiAtena(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);  
        target=3;
    }
	
	public void effetto3(Utente u){
		ArrayList<CartaGiocabile>carte=new ArrayList<>();
		for(int i=0;i<3;i++) {
			if(!ControllerCreazionePartita.p.getMazzo().isEmpty()) {
				carte.add(ControllerCreazionePartita.p.getMazzo().remove(0));				
			}
			else
				System.out.println("Oltre i limiti del mazzo");
		}
		for(CartaGiocabile c:carte) {
			if(c.getMitologia().equals(TipiMitologie.GRECA) && ControllerCreazionePartita.p.getMano().get(u).size()<8)
				ControllerCreazionePartita.p.getMano().get(u).add(c);				
		}
	}
}
