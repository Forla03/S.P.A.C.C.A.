package application.CLASSI.CarteEffetto;

import java.util.ArrayList;
import java.util.List;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaGiocabile;
import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;
import java.util.Random;


public class SaggezzaDiOdino extends CartaEffetto {
	
	public SaggezzaDiOdino(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);  
        target=7;
    }
	
	 public void effettoEffetto5(Utente u1,Utente u2, CartaGiocabile c) {
		 if(ControllerCreazionePartita.p.getMano().get(u2).contains(c)){
		   if(ControllerCreazionePartita.p.getMano().get(u1).size()<8) {			   
			   ControllerCreazionePartita.p.getMano().get(u1).add(c);
			   ControllerCreazionePartita.p.getMano().get(u2).remove(c);
		 }
		   else {
			   ControllerCreazionePartita.p.getMano().get(u2).remove(c);
			   System.out.println("Mano piena");
		   }
		 }
		 else {
			 System.out.println("Errore, carta non presente");
			 return;
		 }
		 
		 if(c.getMitologia().equals(TipiMitologie.NORRENA)) {
			 if(! ControllerCreazionePartita.p.getMano().get(u2).isEmpty()) {
				 Random random=new Random();
				 int x=random.nextInt(ControllerCreazionePartita.p.getMano().get(u2).size());
				 if(ControllerCreazionePartita.p.getMano().get(u1).size()<8) {
					 ControllerCreazionePartita.p.getMano().get(u1).add(ControllerCreazionePartita.p.getMano().get(u2).remove(x));
				 }
				 else
					 ControllerCreazionePartita.p.getMano().get(u2).remove(x);
			 }
			 else
				 System.out.println("Mano avversaria vuota, impossibile rubare ancora");
		 }
	   	
	    }
    
    public String getRichiestaEffetto() {
    	return "Scegli la carta da rubare";
    }
}
		 
	 

