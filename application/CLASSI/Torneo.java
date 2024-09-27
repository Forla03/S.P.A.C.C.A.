package application.CLASSI;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Random;


public class Torneo {  
	/*
	 * Classe che rappresenta un torneo
	 * Ha due arraylist di utenti, giocatori e vincitori
	 * Quando finisce il turno, vengono ricreate alre partite con i vincitori
	 */
	
	public ArrayList<Utente> giocatori=new ArrayList<Utente>();
	public ArrayList<Utente> vincitori=new ArrayList<Utente>();	
	public ArrayList<Partita> partite=new ArrayList<Partita>();
	
	String codiceTorneo;
	
	public Torneo(ArrayList<Utente> u, String codiceTorneo) throws AWTException {
		this.codiceTorneo=codiceTorneo;
		giocatori.addAll(u);
		nuovoTurno();
	}
	
	public Torneo(ArrayList<Utente> u, String codiceTorneo, ArrayList<Utente> v) {
		this.codiceTorneo=codiceTorneo;
		giocatori.addAll(u);
		vincitori.addAll(v);
	}
	
	public void setPartite(ArrayList<Partita> p) {
		partite.clear();
		partite=p;
	}
	
	public String getCodice() {
		return codiceTorneo;
	}
	
	public ArrayList<Utente> getGiocatori(){
		return giocatori;
	}
	
	public ArrayList<Utente> getVincitori(){
		return vincitori;
	}
	
	public String getVincitoriString() {
		String v="";
		for(Utente u: vincitori) {
			v+=u.getNome()+",";
		}
		return v;
	}
	
	public ArrayList<Partita> getPartite() {
		return partite;
	}
	
	public String getGiocatoriString() {
		String nomi="";
		for (Utente u : giocatori) {
			nomi+=u.getNome()+",";
		}
		return nomi;
	}
	
	public String getCodiciSemi() {
		if(giocatori.size()==4) {
			String codici="";
			for(Partita p: partite) {
				codici+=p.getCodice()+",";
		}
		return codici;
		}
		else {
			return "";
		}
	}
	
	public String getCodiciQuarti() {
		if(giocatori.size()==8) {
			String codici="";
			for(Partita p: partite) {
				codici+=p.getCodice()+",";
			}
			return codici;
		}
		else {
			return "";
		}
	}
	
	public String getCodiceFinale() {
		if(giocatori.size()==2) {
			String codice=partite.get(0).getCodice();
			return codice;
		}
		else {
			return "";
		}	
	}
	
	public void nuovaPartita(String codice, int numGioc, String nomiGiocatori) throws AWTException {
		partite.add(new Partita(codice, numGioc, nomiGiocatori));
	}
	
	public void nuovoTurno() throws AWTException{	
		int numPartite=getGiocatori().size()/2;
		for (int i = 0; i < numPartite; i++) {        	                      
			nuovaPartita(generaCodicePartita(), 2, getGiocatori().get(i*2).getNome() + " " + getGiocatori().get(i*2+1).getNome());
		  }	    		
	}
	
	public String generaCodicePartita() {
        Random rand = new Random();
        String codicePart = "!";
        boolean z = false;
        char temp;
        for (int i = 0; i < 8; i++) {
            z = rand.nextBoolean();
            if (z) {          	
                temp = (char) ('a' + rand.nextInt(26));
                codicePart += temp;
            } else {
            	codicePart += rand.nextInt(9);
            }
        }
        return codicePart;
    }
}
