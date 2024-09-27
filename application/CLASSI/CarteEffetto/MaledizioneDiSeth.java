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


public class MaledizioneDiSeth extends CartaEffetto {
	
	public MaledizioneDiSeth(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);  
        target=6;
    }
	
    public void effetto3(Utente u) {
    	ArrayList<CartaPersonaggio> egiziani=new ArrayList<>();
    	ArrayList<CartaPersonaggio> nonEgiziani=new ArrayList<>();
    	for(CartaPersonaggio c: ControllerCreazionePartita.p.getPersonaggiScelti().get(u)) {
    		if(c.getMitologia().equals(TipiMitologie.EGIZIA))
    			egiziani.add(c);
    		else
    			nonEgiziani.add(c);
    	}
    	Random random = new Random();
    	if(!egiziani.isEmpty()) {
    			int y=random.nextInt(egiziani.size());
    			int x=Math.round(egiziani.get(y).getPsAttuali()/2);
    			egiziani.get(y).lessPs(x);
    	}
    	else {
    		int y=random.nextInt(nonEgiziani.size());
			int x=Math.round(nonEgiziani.get(y).getPsAttuali()/2);
			nonEgiziani.get(y).lessPs(x);
    	}
    }
}
		 
	 
