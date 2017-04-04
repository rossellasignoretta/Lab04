package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
		
	List<Corso> corsi;
	
	public Model(){
		corsi= new LinkedList<Corso>();

	}
	

	public List<String> getNomiCorsi(){
		CorsoDAO c= new CorsoDAO();
		corsi.addAll(c.getTuttiICorsi());
		List <String> nomiCorsi= new LinkedList<String>();
		for(Corso ctemp: corsi){
			nomiCorsi.add(ctemp.getNome());
		}
		return nomiCorsi;
		
	}
	
	public Studente trovaStudente(int matricola){
		StudenteDAO s=new StudenteDAO();
		return s.getStudente(matricola);
	}
	
	public List<Studente> cercaIscritti(String nome){
		CorsoDAO c= new CorsoDAO();
		
		for(Corso ctemp: corsi){
			if (ctemp.getNome().equals(nome)){
				return c.getStudentiIscrittiAlCorso(ctemp);
			}
		}
		return null;
	}
	
	public List<Corso> cercaCorsi(Studente s){
		StudenteDAO studente= new StudenteDAO();
		return studente.getCorsiACuiEIscrittoUnoStudente(s);
	
	}
}
