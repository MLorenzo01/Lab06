package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private MeteoDAO meteoDAO;
	private final static int COST = 100;
	private final static int N_MIN = 3;
	private final static int N_MAX = 6;
	private final static int N_TOT = 15;
	private int costoB = 0;
	private List<Rilevamento> cittaBest;
	Map<Integer, Rilevamento[]> rilevamenti;

	public Model() {
		meteoDAO = new MeteoDAO();
		cittaBest = new ArrayList<>();
	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		return this.meteoDAO.getUmiditaMedia(mese);
	}
	public List<Rilevamento> getAllRilevamentiMese(int mese) {
		return this.meteoDAO.getAllRilevamentiMese(mese);
	}
	public List<String> getCitta(){
		return this.meteoDAO.getCitta();
	}
	public Rilevamento[] getAllRilevamentiLocalitaMese(int mese, String Localita){
		return this.meteoDAO.getAllRilevamentiLocalitaMese(mese, Localita); 
	}
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		int cont = 0;
		List<Rilevamento> parziale = new ArrayList<Rilevamento>();
		List<String> citta = getCitta();
		rilevamenti = new HashMap<Integer, Rilevamento[]>();
		for(String s: citta) {
			rilevamenti.put(cont, getAllRilevamentiLocalitaMese(mese, s));
			cont++;
		}
		TreeMap<String, Boolean> verifica = new TreeMap<String, Boolean>();
		for(String s : citta) {
			verifica.put(s, false);
		}
		trova(parziale, 0);
		String stringa = "";
		for(Rilevamento s: cittaBest) {
			stringa += s.getLocalita()+", ";
		}
		return stringa;
	}
	public void trova(List<Rilevamento> parziale, int livello) {
		if (livello== N_TOT) {
			//caso terminale
			Double costo = calcolaCosto(parziale);
			if (cittaBest ==null || costo < calcolaCosto(cittaBest)) {
				//System.out.format("%f %s\n", costo, parziale);
				cittaBest = new ArrayList<>(parziale);
			}
		}else {
			for(Rilevamento[] r: rilevamenti.values()) {
				for(Rilevamento rilev: r) {
					if(!parziale.contains(rilev)) {
						if(aggiuntaValida(parziale, rilev)) {
							parziale.add(rilev);
							trova(parziale, livello);
							parziale.remove(rilev);
						}else
							break;
					}
				}
			}
		}
	}
	public double calcolaCosto(List<Rilevamento> parziale) {
		double costo = 0.0;
		//sommatoria delle umidità in ciascuna città, considerando il rilevamento del giorno giusto
		//SOMMA parziale.get(giorno-1).getRilevamenti().get(giorno-1)
		for (int giorno=1; giorno<=N_TOT; giorno++) {
			//dove mi trovo
			Rilevamento c = parziale.get(giorno-1);
			//che umidità ho in quel giorno in quella città?
			costo+=c.getUmidita();
		}
		//poi devo sommare 100*numero di volte in cui cambio città
		for (int giorno=2; giorno<=N_TOT; giorno++) {
			//dove mi trovo
			if(!parziale.get(giorno-1).getLocalita().equals(parziale.get(giorno-2).getLocalita())) {
				costo +=COST;
			}
		}
		return costo;
	}

	
	public boolean aggiuntaValida(List<Rilevamento> parziale, Rilevamento rilev) {
		//verifica giorni massimi
				//contiamo quante volte la città 'prova' era già apparsa nell'attuale lista costruita fin qui
				int conta = 0;
				for (Rilevamento precedente:parziale) {
					if (precedente.getLocalita().equals(rilev.getLocalita()))
						conta++; 
				}
				if (conta >=N_MAX)
					return false;
				
				// verifica dei giorni minimi
				if (parziale.size()==0) //primo giorno posso inserire qualsiasi città
						return true;
				if (parziale.size()==1 || parziale.size()==2) {
					//siamo al secondo o terzo giorno, non posso cambiare
					//quindi l'aggiunta è valida solo se la città di prova coincide con la sua precedente
					return parziale.get(parziale.size()-1).getLocalita().equals(rilev.getLocalita()); 
				}
				//nel caso generale, se ho già passato i controlli sopra, non c'è nulla che mi vieta di rimanere nella stessa città
				//quindi per i giorni successivi ai primi tre posso sempre rimanere
				if (parziale.get(parziale.size()-1).getLocalita().equals(rilev.getLocalita()))
					return true; 
				// se cambio città mi devo assicurare che nei tre giorni precedenti sono rimasto fermo 
				if (parziale.get(parziale.size()-1).getLocalita().equals(parziale.get(parziale.size()-2).getLocalita()) 
				&& parziale.get(parziale.size()-2).getLocalita().equals(parziale.get(parziale.size()-3).getLocalita()))
					return true;
					
				return false;
				
			}
}
