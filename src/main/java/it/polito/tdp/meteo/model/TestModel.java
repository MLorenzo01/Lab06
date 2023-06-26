package it.polito.tdp.meteo.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		//System.out.println(m.getUmiditaMedia(12));
		
		System.out.println(m.trovaSequenza(7));
		

	}

}

//int costo = 0, num = 0;
//boolean finito = true;
//if(validate(livello, parziale) == true) {
//	System.out.println(best);
//	best = parziale;
//}
//for(String s: citta.keySet()) {
//	if(count(livello, s) == 6)
//		citta.replace(s, true);
//}
//while(finito == true) {
//	int i = 0;
//	for(boolean b: citta.values()) {
//		if(b == true)
//			i++;
//	}
//	if(i == 3)
//		finito = false;
//	Rilevamento[] rilevam = rilevamenti.get(num);
//	for(int x = livello.size()-1; i<15 ; i++ ) {
//		Rilevamento r = rilevam[i];
//		int contatore = count(livello);
//		if(livello.size() == 0) {
//			livello.add(r);
//			parziale += costo;
//			trova(citta, rilevamenti, parziale, livello);
//			parziale -= costo;
//			livello.remove(livello.size()-1);
//		}
//		else 
//			if(!livello.contains(r) && citta.get(r.getLocalita()) == false) {
//				if(contatore < N_MIN) {
//					livello.add(r);
//					parziale += r.getUmidita();
//				}
//				else if(contatore  == N_MAX) {
//					break;
//				}else {
//					livello.add(r);
//					if(livello.get(livello.size()-1).getLocalita().compareTo(r.getLocalita()) == 0) {
//						costo = r.getUmidita();
//						parziale += costo;
//					}else {
//						costo = r.getUmidita()+ COST;
//						parziale += costo;
//					}
//					trova(citta, rilevamenti, parziale, livello);
//					livello.remove(livello.size()-1);
//					parziale -= costo;
//				}
//		}
//	}
//	if(num == 2) {
//		num = 0;
//	}
//	else
//		num++;
//}
//}
//
//public boolean validate(List<Rilevamento> parziale, int costoP) {
//if(parziale.size() == 0)
//	return false;
//if(parziale.size() == N_TOT) {
//	if(best > costoP) {
//		return true;
//	}else
//		return false;
//}else
//	return false;
//}
//public int count(List<Rilevamento> ril) {
//if(ril.size() == 0)
//	return 0;
//String ultimo = ril.get(ril.size()-1).getLocalita();
//int numero = 0;
//for(Rilevamento r: ril) {
//	if(r.getLocalita().compareTo(ultimo) == 0) {
//		numero++;
//	}
//}
//return numero;
//}
//public int count(List<Rilevamento> ril, String localita) {
//if(ril.size() == 0)
//	return 0;
//int numero = 0;
//for(Rilevamento r: ril) {
//	if(r.getLocalita().compareTo(localita) == 0) {
//		numero++;
//	}
//}
//return numero;
//}
//}

