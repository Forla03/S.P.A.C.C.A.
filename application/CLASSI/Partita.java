package application.CLASSI;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import application.CLASSI.CarteCura.*;
import application.CLASSI.CarteEquipaggiamento.*;
import application.CLASSI.CarteEffetto.*;
import application.CLASSI.CarteTerreno.*;
import application.CLASSI.CarteImprevisto.*;

import java.util.Collections;

public class Partita {

	protected int numGioc;
	protected String codice;
	protected ArrayList<Utente> listaUtenti=new ArrayList<Utente>();
	protected String nomiGiocatori;
	protected HashMap<Utente, ArrayList<CartaPersonaggio>> personaggiScelti = new HashMap<>();
	protected ArrayList<CartaGiocabile> mazzo=new ArrayList<>();
	protected HashMap<Utente,ArrayList<CartaGiocabile>> mano=new HashMap<>();
	protected HashMap<Utente,CartaTerreno> terreno=new HashMap<>();
	protected boolean torneo=false;
	protected int turnoGiocatore;
	protected int turnoTotale;
	protected String timeline;
	
	public Partita(String codice, int numGioc, String nomiGiocatori) throws AWTException {
		try {
		timeline="";
		turnoGiocatore=0;
		turnoTotale=0;
		this.numGioc=numGioc;
		this.codice=codice;
		this.nomiGiocatori=nomiGiocatori;
		listaUtenti=new ArrayList<Utente>();
		String[] nomiGiocatoriArray=nomiGiocatori.split(" ");
		List<Utente> utenti = JDBCCaricamento.getUtenti();
		for(int i=0; i<nomiGiocatoriArray.length; i++) {	
			System.out.println(nomiGiocatoriArray[i]);
		    boolean found = false;
		    for(Utente u : utenti) {				
		        if(u.getNome().equals(nomiGiocatoriArray[i])) {
		            listaUtenti.add(u);
		            found = true;
		            break;
		        }
		    }
		    if(!found) {
		        listaUtenti.add(new RobotIntelligente(nomiGiocatoriArray[i],""));
		    }
		}
					
		mazzo.addAll(riconoscimentoClassiEquip());
	    mazzo.addAll(riconoscimentoClassiCura());
		mazzo.addAll(riconoscimentoClassiEffetto());
		mazzo.addAll(riconoscimentoClassiTerreno());
		mazzo.addAll(riconoscimentoClassiImprevisto());
		List<CartaGiocabile> copie = new ArrayList<>();
	    for (CartaGiocabile carta : mazzo) {
	        if (carta.getRarita() == RaritaCartaGiocabile.COMUNE) {
	            copie.add(carta.clone()); 
	            copie.add(carta.clone()); 
	        } else if (carta.getRarita() == RaritaCartaGiocabile.EPICA) {
	            copie.add(carta.clone()); 
	        }
	    }
	    for (Utente u : personaggiScelti.keySet()) {
	    	terreno.put(u, null);
	    }
	    
	    mazzo.addAll(copie);
		for(CartaGiocabile c:mazzo) {
			System.out.println(c.getClass());
		}
		Collections.shuffle(mazzo);
		}
		catch(Exception e) {
			System.out.println("Eccezione");
			e.printStackTrace();
		}
	}
	
	public Partita(String codice,int numGiocatori,String[] nomiGiocatoriArray,String[]mazzo,int turnoGiocatore,int turnoTotale,String timeline,String[] carteManoGioc1,String[]carteManoGioc2,String[] carteManoGioc3,String[] carteManoGioc4,HashMap<String,ArrayList<String>>cartePg1Equip,HashMap<String,int[]>cartePg1Attributi,ArrayList<String>cartePg1,HashMap<String,ArrayList<String>>cartePg2Equip,HashMap<String,int[]>cartePg2Attributi,ArrayList<String>cartePg2, HashMap<String,ArrayList<String>>cartePg3Equip,HashMap<String,int[]>cartePg3Attributi,ArrayList<String>cartePg3,HashMap<String,ArrayList<String>>cartePg4Equip,HashMap<String,int[]>cartePg4Attributi,ArrayList<String>cartePg4,String carteTerrGioc1,String carteTerrGioc2,String carteTerrGioc3,String carteTerrGioc4) throws AWTException {		
		//crea la partita prendendo come input stringhe ed interi letti dal db 
		this.codice=codice;
		this.numGioc=numGiocatori;
		for(int i=0;i<nomiGiocatoriArray.length;i++) {
			this.nomiGiocatori+=nomiGiocatoriArray[i]+" ";
		}
		//riempimento mazzo
		this.mazzo.addAll(riconoscimentoCarteGiocabiliDaString(mazzo));
		this.turnoGiocatore=turnoGiocatore;
		this.turnoTotale=turnoTotale;
		this.timeline=timeline;	
		//prendo i giocatori che fanno parte della partita		
		List<Utente> utenti = JDBCCaricamento.getUtenti();
		for(int i=0; i<nomiGiocatoriArray.length; i++) {	
		    boolean found = false;
		    for(Utente u : utenti) {				
		        if(u.getNome().equals(nomiGiocatoriArray[i])) {
		            listaUtenti.add(u);
		            found = true;
		            break;
		        }
		    }
		    if(!found) {
		        listaUtenti.add(new RobotIntelligente(nomiGiocatoriArray[i],""));
		    }
		}
				
		//riempimento mani
		ArrayList<String[]>carteManoTemp=new ArrayList<String[]>();
		carteManoTemp.add(carteManoGioc1);
		carteManoTemp.add(carteManoGioc2);
		carteManoTemp.add(carteManoGioc3);
		carteManoTemp.add(carteManoGioc4);
	   	for(int i=0;i<listaUtenti.size();i++) {	   		
	   		mano.put(listaUtenti.get(i), riconoscimentoCarteGiocabiliDaString(carteManoTemp.get(i)));	   		
	   	}
	   	
	   	//riempimento personaggi posseduti dai giocatori
	   	ArrayList<ArrayList<String>>cartePgTemp=new ArrayList<ArrayList<String>>();  
	   	cartePgTemp.add(cartePg1);
	   	cartePgTemp.add(cartePg2);
	   	cartePgTemp.add(cartePg3);
	   	cartePgTemp.add(cartePg4);	
	   	
	   	ArrayList<HashMap<String,ArrayList<String>>>carteEquipTemp=new ArrayList<HashMap<String,ArrayList<String>>>();
	   	carteEquipTemp.add(cartePg1Equip);
		carteEquipTemp.add(cartePg2Equip);
		carteEquipTemp.add(cartePg3Equip);
		carteEquipTemp.add(cartePg4Equip);
		
		
		ArrayList<HashMap<String,int[]>> attributiTemp= new ArrayList <HashMap<String,int[]>>();
		attributiTemp.add(cartePg1Attributi);
		attributiTemp.add(cartePg2Attributi);
		attributiTemp.add(cartePg3Attributi);
		attributiTemp.add(cartePg4Attributi);
		
		ArrayList<String> carteTerrenoTemp=new ArrayList<String>();
		carteTerrenoTemp.add(carteTerrGioc1);
		carteTerrenoTemp.add(carteTerrGioc2);
		carteTerrenoTemp.add(carteTerrGioc3);
		carteTerrenoTemp.add(carteTerrGioc4);
			
		
	   	for(int i=0;i<listaUtenti.size();i++) {
	   		personaggiScelti.put(listaUtenti.get(i), riconoscimentoPgDaString(cartePgTemp.get(i)));  
	   		terreno.put(listaUtenti.get(i), riconoscimentoCarteTerrenoDaString(carteTerrenoTemp.get(i)));
            for(CartaPersonaggio c:personaggiScelti.get(listaUtenti.get(i))) {
            	if(carteEquipTemp.get(i).keySet().contains(c.getNome())) {          		
            		c.addEquipLista(riconoscimentoCarteEquipDaString(carteEquipTemp.get(i).get(c.getNome())));
            		//System.out.println("Equipaggiamento dato a "+c.getNome()+":"+ c.getEquip().toString());
            	}
            	if(attributiTemp.get(i).keySet().contains(c.getNome())) {
            		c.setAttAttuale(attributiTemp.get(i).get(c.getNome())[0]);
            		c.setPsAttuali(attributiTemp.get(i).get(c.getNome())[1]);
            	}
	   			
	   		}
	   		
	   	}   	
		
	}
	
	public void aggiungiPersonaggi(Utente u, ArrayList<CartaPersonaggio> cp) {
		personaggiScelti.put(u, cp);
	}
	
	public HashMap<Utente, ArrayList<CartaPersonaggio>> getPersonaggiScelti(){
		return personaggiScelti;
	}
	
	public int getTurnoGioc() {
		return turnoGiocatore;
	}
	
	public List<Utente> getListaUtenti(){
		return listaUtenti;
	}
	
	public int getTurnoTot() {
		return turnoTotale;
	}
	
	public String getTimeline() {
		return timeline;
	}
	
	public void setTimeline(String s) {
		timeline=s;
	}
	
	public void aggiungiGiocatore() {                                   
		List<Utente> utenti= JDBCCaricamento.getUtenti();				
		String[] nomiGioc=nomiGiocatori.split(",");
	}
	
	public String getCodice() {
		return codice;
	}
	
	public int getNumGioc() {
		return numGioc;
	}

	public List<String> getNomiGiocatori() {
		List<String> nomi=new ArrayList<String>();
		for(Utente u: listaUtenti) {
			nomi.add(u.getNome());
		}
		return nomi;
	}
	
	public String getNomiGiocatoriString() {
		return nomiGiocatori;
	}
	
	public ArrayList<CartaGiocabile> getMazzo(){
		return mazzo;
	}
	
	public HashMap<Utente,ArrayList<CartaGiocabile>> getMano(){
		return mano;
	}
	
	public HashMap<Utente,CartaTerreno> getTerreno(){
		return terreno;
	}
	
	public void inizializzaMano() {
	    for (Utente u : personaggiScelti.keySet()) {
	        ArrayList<CartaGiocabile> temp = new ArrayList<CartaGiocabile>(3);
	        for (int i = 0; i < 4; i++) {
	            temp.add(mazzo.get(i));
	        }
	        mano.put(u, new ArrayList<>(temp)); 
	        mazzo.subList(0, 4).clear();
	    }
	}
	
	public void stampaMano() {
	    if (mano.isEmpty()) {
	        System.out.println("La mano è vuota.");
	    } else {
	        for (Map.Entry<Utente, ArrayList<CartaGiocabile>> entry : mano.entrySet()) {
	            Utente utente = entry.getKey();
	            ArrayList<CartaGiocabile> carte = entry.getValue();
	            System.out.println("Utente: " + utente);
	            if (carte.isEmpty()) {
	                System.out.println("La mano di questo utente è vuota.");
	            } else {
	                System.out.println("Carte nella mano:");
	                for (CartaGiocabile carta : carte) {
	                    System.out.println(carta);
	                }
	            }
	            System.out.println();
	        }
	    }
	}

	public void stampaMazzo() {
	    System.out.println("Mazzo:");
	    for (CartaGiocabile carta : mazzo) {
	        System.out.println(carta);
	    }
	}
	
	public void pesca(Utente u) {
	    if (!mazzo.isEmpty()) {
	    	if(mano.get(u).size()<8) {
	        mano.get(u).add(mazzo.remove(0));
	    	}
	    	else
	    		System.out.println("Numero massimo di carte nella mano raggiunto.");
	    } else {
	        System.out.println("Il mazzo è vuoto, impossibile pescare.");
	    }
	}
	
	public void pescaMultiplo(Utente u,int numeroCarte) {		
		for (int i = 0; i < numeroCarte; i++) {
			if(!mazzo.isEmpty()) {
				if(mano.get(u).size()<8) {
				 mano.get(u).add(mazzo.remove(0));
				}
				else{
		    		System.out.println("Massimo di carte nella mano raggiunto.");
		    		break;
				}
			}
			else {
				System.out.println("Non ci sono più carte nel mazzo.");
				break;
			}
	    }
	}
	
	public String getMazzoString() {
		String mazzoString="";
		for(CartaGiocabile c:mazzo) {
			mazzoString+=c.getNome()+"|";
		}
		return mazzoString;
	}
	
	public String getCarteManoGioc1String(List<Utente> listUtenti) {
		String s="";
		if(!listUtenti.isEmpty()) {
			for(CartaGiocabile c:mano.get(listUtenti.get(0))) {
				s+=c.getNome()+"|";
			}
		}
		return s;
	}
	
	public String getCarteManoGioc2String(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=2) {
			for(CartaGiocabile c:mano.get(listUtenti.get(1))) {
				s+=c.getNome()+"|";
			}
		}
		return s;
	}
	
	public String getCarteManoGioc3String(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=3) {
			for(CartaGiocabile c:mano.get(listUtenti.get(2))) {
				s+=c.getNome()+"|";
			}
		}
		return s;
	}
	
	public String getCarteManoGioc4String(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=4) {
			for(CartaGiocabile c:mano.get(listUtenti.get(3))) {
				s+=c.getNome()+"|";
			}
		}
		return s;
	}
	
	public String getCartePersGioc1(List<Utente> listUtenti) {
		String s="";
		if(!listUtenti.isEmpty()) {
			for(CartaPersonaggio c:personaggiScelti.get(listUtenti.get(0))) {
				s+=c.getNome()+"/";
				s+=c.getAttAttuale()+"/";
				s+=c.getPsAttuali();
				if(c.getEquip().isEmpty())
					s+="|";
				else {
					for(CartaEquipaggiamento e:c.getEquip()) {
						s+="/"+e.getNome();
					}
					s+="|";
				}
			}
		}
		return s;
	}
	
	public String getCartePersGioc2(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=2) {
			for(CartaPersonaggio c:personaggiScelti.get(listUtenti.get(1))) {
				s+=c.getNome()+"/";
				s+=c.getAttAttuale()+"/";
				s+=c.getPsAttuali();
				if(c.getEquip().isEmpty())
					s+="|";
				else {
					for(CartaEquipaggiamento e:c.getEquip()) {
						s+="/"+e.getNome();
					}
					s+="|";
				}
			}
		}
		return s;
	}
	
	public String getCartePersGioc3(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=3) {
			for(CartaPersonaggio c:personaggiScelti.get(listUtenti.get(2))) {
				s+=c.getNome()+"/";
				s+=c.getAttAttuale()+"/";
				s+=c.getPsAttuali();
				if(c.getEquip().isEmpty())
					s+="|";
				else {
					for(CartaEquipaggiamento e:c.getEquip()) {
						s+="/"+e.getNome();
					}
					s+="|";
				}
			}
		}
		return s;
	}
	
	public String getCartePersGioc4(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=4) {
			for(CartaPersonaggio c:personaggiScelti.get(listUtenti.get(3))) {
				s+=c.getNome()+"/";
				s+=c.getAttAttuale()+"/";
				s+=c.getPsAttuali();
				if(c.getEquip().isEmpty())
					s+="|";
				else {
					for(CartaEquipaggiamento e:c.getEquip()) {
						s+="/"+e.getNome();
					}
					s+="|";
				}
			}
		}
		return s;
	}
	
	public String getCartaTerrGioc1String(List<Utente> listUtenti) {
		String s="";
		if(!listUtenti.isEmpty()) {
			if(terreno.get(listUtenti.get(0))!=null)
				s=terreno.get(listUtenti.get(0)).getNome()+"|";
		}
		return s;
	}
	
	public String getCartaTerrGioc2String(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=2) {
			if(terreno.get(listUtenti.get(1))!=null)
				s=terreno.get(listUtenti.get(1)).getNome()+"|";
		}
		return s;
	}
	
	public String getCartaTerrGioc3String(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=3) {
			if(terreno.get(listUtenti.get(2))!=null)
				s=terreno.get(listUtenti.get(2)).getNome()+"|";
		}
		return s;
	}
	
	public String getCartaTerrGioc4String(List<Utente> listUtenti) {
		String s="";
		if(listUtenti.size()>=4) {
			if(terreno.get(listUtenti.get(3))!=null)
				s=terreno.get(listUtenti.get(3)).getNome()+"|";
		}
		return s;
	}
	
	public ArrayList<CartaEquipaggiamento> riconoscimentoClassiEquip(){
		ArrayList<CartaEquipaggiamento> carte=new ArrayList<>();
		for(CartaEquipaggiamento c:JDBCCaricamento.getCarteEquipaggiamento()) {
			if(c.getNome().equals("Elmo di Atena")) {
				ElmoDiAtena temp=new ElmoDiAtena(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Laurea di Apollo")) {
				LaureaDiApollo temp=new LaureaDiApollo(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Ankh")) {
				Ankh temp=new Ankh(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Gladio di Marte")) {
				GladioDiMarte temp=new GladioDiMarte(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Lancia di Odino")) {
				LanciaDiOdino temp=new LanciaDiOdino(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Scarabeo sacro")) {
				ScarabeoSacro temp=new ScarabeoSacro(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Spada Gramr")) {
				SpadaGramr temp=new SpadaGramr(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Zanna di Sobek")) {
				ZannaDiSobek temp=new ZannaDiSobek(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Scudo di Minerva")) {
				ScudoDiMinerva temp=new ScudoDiMinerva(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Mjolnir")) {
				Mjolnir temp=new Mjolnir(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Calzari alati di Ermes")) {
				CalzariAlatiDiErmes temp=new CalzariAlatiDiErmes(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Pettine d'oro di Afrodite")) {
				PettineDoroDiAfrodite temp=new PettineDoroDiAfrodite(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}			
			else
				carte.add(c);
		}
		return carte;
	}

	public ArrayList<CartaCura> riconoscimentoClassiCura(){
		ArrayList<CartaCura> carte=new ArrayList<>();
		for(CartaCura c:JDBCCaricamento.getCarteCura()) {
			if(c.getNome().equals("Conoscenza di Toth")) {
				ConoscenzaDiToth temp=new ConoscenzaDiToth(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Sacerdote di Sekhmet")) {
				SacerdoteDiSekhmet temp=new SacerdoteDiSekhmet(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Rigenerazione di Eir")) {
				RigenerazioneDiEir temp=new RigenerazioneDiEir(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Grazia di Apollo")) {
				GraziaDiApollo temp=new GraziaDiApollo(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Frutto di Yggdrasill")) {
				FruttoDiYggdrasill temp=new FruttoDiYggdrasill(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Culto di Salus")) {
				CultoDiSalus temp=new CultoDiSalus(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Erba di Chirone")) {
				ErbaDiChirone temp=new ErbaDiChirone(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Conforto di Igea")) {
				ConfortoDiIgea temp=new ConfortoDiIgea(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Miracolo di Epione")) {
				MiracoloDiEpione temp=new MiracoloDiEpione(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Benedizione di Hathor")) {
				BenedizioneDiHathor temp=new BenedizioneDiHathor(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Intervento di Esculapio")) {
				InterventoDiEsculapio temp=new InterventoDiEsculapio(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Melodia di Bragi")) {
				MelodiaDiBragi temp=new MelodiaDiBragi(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}

			else
				carte.add(c);
		}
		return carte;
	}
	
	public ArrayList<CartaEffetto> riconoscimentoClassiEffetto(){
		ArrayList<CartaEffetto> carte=new ArrayList<>();
		for(CartaEffetto c:JDBCCaricamento.getCarteEffetto()) {
			if(c.getNome().equals("Benedizione di Baldur")) {
				BenedizioneDiBaldur temp=new BenedizioneDiBaldur(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Inganno di Anubi")) {
				IngannoDiAnubi temp=new IngannoDiAnubi(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Potere di Saturno")) {
				PotereDiSaturno temp=new PotereDiSaturno(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Coraggio di Tyr")) {
				CoraggioDiTyr temp=new CoraggioDiTyr(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Ebrezza di Bacco")) {
				EbbrezzaDiBacco temp=new EbbrezzaDiBacco(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Furia solare di Ra")) {
				FuriaSolareDiRa temp=new FuriaSolareDiRa(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Giudizio di Giove")) {
				GiudizioDiGiove temp=new GiudizioDiGiove(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Ingegno di Atena")) {
				IngegnoDiAtena temp=new IngegnoDiAtena(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Ira del re dell'Olimpo")) {
				IraDelReDellOlimpo temp=new IraDelReDellOlimpo(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Maledizione di Seth")) {
				MaledizioneDiSeth temp=new MaledizioneDiSeth(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Saggezza di Odino")) {
				SaggezzaDiOdino temp=new SaggezzaDiOdino(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else if(c.getNome().equals("Ombra di Ade")) {
				OmbraDiAde temp=new OmbraDiAde(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
			    carte.add(temp);
			}
			else {
				carte.add(c);	
			}
		}
		return carte;
	}
	
	public ArrayList<CartaTerreno> riconoscimentoClassiTerreno(){
		ArrayList<CartaTerreno> carte=new ArrayList<>();
		for(CartaTerreno c:JDBCCaricamento.getCarteTerreno()) {
			if(c.getNome().equals("Città eterna")) {
				CittaEterna temp=new CittaEterna(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Campo di Aaru")) {
				CampoDiAaru temp=new CampoDiAaru(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Monte Olimpo")) {
				MonteOlimpo temp=new MonteOlimpo(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Asgard")) {
				Asgard temp=new Asgard (c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else
				carte.add(c);
		}
		return carte;
	}
	
	public ArrayList<CartaImprevisto> riconoscimentoClassiImprevisto(){
		ArrayList<CartaImprevisto> carte=new ArrayList<>();
		for(CartaImprevisto c: JDBCCaricamento.getCarteImprevisto()){
			if(c.getNome().equals("Pesatura dell'anima")) {
				PesaturaDellAnima temp=new PesaturaDellAnima(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Ragnarok")) {
				Ragnarok temp=new Ragnarok(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Scatola di Pandora")) {
				ScatolaDiPandora temp=new ScatolaDiPandora(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else if(c.getNome().equals("Viaggio di Enea")) {
				ViaggioDiEnea temp=new ViaggioDiEnea(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());
				carte.add(temp);
			}
			else
				carte.add(c);
		}
		return carte;
	}
	
	//Metodi per caricamento partita salvata in precedenza
	
	public ArrayList<CartaGiocabile> riconoscimentoCarteGiocabiliDaString(String[] m){  //valuta se è giusto clonare prima o no
	    ArrayList<CartaGiocabile> carte = new ArrayList<CartaGiocabile>();	            //dopodichè verifca che le informazioni partita siano giuste
	    carte.addAll(riconoscimentoClassiEquip());
	    carte.addAll(riconoscimentoClassiCura());
	    carte.addAll(riconoscimentoClassiEffetto());
	    carte.addAll(riconoscimentoClassiTerreno());
	    carte.addAll(riconoscimentoClassiImprevisto());
	    List<CartaGiocabile> copie = new ArrayList<>();
	    for (CartaGiocabile carta : carte) {
	        if (carta.getRarita() == RaritaCartaGiocabile.COMUNE) {
	            copie.add(carta.clone()); 
	            copie.add(carta.clone()); 
	        } else if (carta.getRarita() == RaritaCartaGiocabile.EPICA) {
	            copie.add(carta.clone()); 
	        }
	    }
	    carte.addAll(copie);
	    ArrayList<CartaGiocabile>carteOutput=new ArrayList<CartaGiocabile>();
	    for(int i=0;i<m.length;i++) {
	    	for(CartaGiocabile c:carte) {
	    		if(c.getNome().equals(m[i])) {
	    			carteOutput.add(c);
	    			carte.remove(c);
	    			break;
	    		}
	    	}
	    }
	    return carteOutput;
	}
	
	public ArrayList<CartaPersonaggio> riconoscimentoPgDaString(ArrayList<String> nomiCarte){
		ArrayList<CartaPersonaggio>carte=new ArrayList<CartaPersonaggio>();
		ArrayList<CartaPersonaggio>carteOutput=new ArrayList<CartaPersonaggio>();
		carte.addAll(JDBCCaricamento.getCartePersonaggio());
		for(String s:nomiCarte) {
			for(CartaPersonaggio c:carte) {
				if(c.getNome().equals(s)) {
					carteOutput.add(c.clone());
					//carte.remove(c);
				}
			}
		}
		return carteOutput;
	}
	
	public ArrayList<CartaEquipaggiamento> riconoscimentoCarteEquipDaString(ArrayList<String> m){
	    ArrayList<CartaEquipaggiamento> carte = new ArrayList<CartaEquipaggiamento>();
	    carte.addAll(riconoscimentoClassiEquip());	    	   
	    List<CartaEquipaggiamento> copie = new ArrayList<>();
	    for (CartaEquipaggiamento carta : carte) {	        
	            if (carta.getRarita() == RaritaCartaGiocabile.COMUNE) {
	                copie.add((CartaEquipaggiamento)carta.clone()); 
	                copie.add((CartaEquipaggiamento)carta.clone()); 
	            } else if (carta.getRarita() == RaritaCartaGiocabile.EPICA) {
	                copie.add((CartaEquipaggiamento)carta.clone()); 
	            
	        }
	    }
	    ArrayList<CartaEquipaggiamento>carteOutput=new ArrayList<CartaEquipaggiamento>();
	    for(int i=0;i<m.size();i++) {
	    	for(CartaGiocabile c:carte) {
	    		if(c.getNome().equals(m.get(i))) {
	    			carteOutput.add((CartaEquipaggiamento)c);
	    			carte.remove(c);
	    			break;
	    		}
	    	}
	    }    
	    return carteOutput;
	}
	
	public CartaTerreno riconoscimentoCarteTerrenoDaString(String m){	
		for(CartaTerreno c:JDBCCaricamento.getCarteTerreno()) {
			if(m.equals("Città eterna") && c.getNome().equals("Città eterna")) {
				CittaEterna temp=new CittaEterna(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());		
				return temp;
			}
			else if(m.equals("Campo di Aaru") && c.getNome().equals("Campo di Aaru")) {
				CampoDiAaru temp=new CampoDiAaru(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());		
				return temp;
			}
			else if(m.equals("Monte Olimpo") && c.getNome().equals("Monte Olimpo")) {
				MonteOlimpo temp=new MonteOlimpo(c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());	
				return temp;
			}
			else if(m.equals("Asgard") && c.getNome().equals("Asgard")) {
				Asgard temp=new Asgard (c.getNome(),c.getMitologia(),c.rarita,c.getEffetto(),c.getImmagine());	
				return temp;
			}			
		}
		return null;
	}

	public void torneoOn() {
		torneo=true;
	}
	
	public void torneoOff() {
		torneo=false;
	}
	
	public boolean getTorneo() {
		return torneo;
	}
	
	public void inizializzaMazzo() {
		mazzo.addAll(riconoscimentoClassiEquip());
		mazzo.addAll(riconoscimentoClassiCura());
		mazzo.addAll(riconoscimentoClassiEffetto());
		mazzo.addAll(riconoscimentoClassiTerreno());
		mazzo.addAll(riconoscimentoClassiImprevisto());
		List<CartaGiocabile> copie = new ArrayList<>();
	    for (CartaGiocabile carta : mazzo) {
	        if (carta.getRarita() == RaritaCartaGiocabile.COMUNE) {
	            copie.add(carta.clone()); 
	            copie.add(carta.clone()); 
	        } else if (carta.getRarita() == RaritaCartaGiocabile.EPICA) {
	            copie.add(carta.clone()); 
	        }
	    }
	    Collections.shuffle(mazzo);
	}
}

