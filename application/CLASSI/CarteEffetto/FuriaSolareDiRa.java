package application.CLASSI.CarteEffetto;

import application.CLASSI.CartaCura;
import application.CLASSI.CartaEffetto;
import application.CLASSI.CartaEquipaggiamento;
import application.CLASSI.CartaGiocabile;
//import application.CLASSI.CartaPersonaggio;
import application.CLASSI.RaritaCartaGiocabile;
import application.CLASSI.TipiMitologie;
import application.CLASSI.Utente;
import application.CONTROLLER.ControllerCreazionePartita;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class FuriaSolareDiRa extends CartaEffetto {
	
	public FuriaSolareDiRa(String nome, TipiMitologie mitologia, RaritaCartaGiocabile rarita, String effetto, Image immagine) {
        super(nome, mitologia, rarita, effetto, immagine);  
        target=3;
    }
	
	public void effetto3(Utente u){
		boolean equip=false;
		boolean cura=false;
		boolean effetto=false;		
		boolean egizio=false;
		
		ArrayList<CartaGiocabile> carteDaRimuovere = new ArrayList<>();
		
		for(CartaGiocabile c: ControllerCreazionePartita.p.getMazzo()) {
			if(ControllerCreazionePartita.p.getMano().get(u).size()<8) {
				if(equip==false && c instanceof CartaEquipaggiamento) {
					ControllerCreazionePartita.p.getMano().get(u).add(c);
					if(c.getMitologia().equals(TipiMitologie.EGIZIA))
						egizio=true;
					equip=true;
					carteDaRimuovere.add(c);
				}
				else if(cura==false && c instanceof CartaCura) {
					ControllerCreazionePartita.p.getMano().get(u).add(c);
					if(c.getMitologia().equals(TipiMitologie.EGIZIA))
						egizio=true;
					cura=true;
					carteDaRimuovere.add(c);
				}
				else if(effetto==false && c instanceof CartaEffetto) {
					ControllerCreazionePartita.p.getMano().get(u).add(c);
					if(c.getMitologia().equals(TipiMitologie.EGIZIA))
						egizio=true;
					effetto=true;
					carteDaRimuovere.add(c);
				}
			}
		}
		
		ControllerCreazionePartita.p.getMazzo().removeAll(carteDaRimuovere);
		
		if(egizio==true && !ControllerCreazionePartita.p.getMazzo().isEmpty() && ControllerCreazionePartita.p.getMano().get(u).size()<8) {
			ControllerCreazionePartita.p.getMano().get(u).add(ControllerCreazionePartita.p.getMazzo().remove(0));
			System.out.println("Carta bonus pescata");
		}
		if(effetto==false || cura==false || equip==false)
			System.out.println("Mancanze nel mazzo");
	}
}