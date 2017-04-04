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
	

	public List<Corso> getCorsi(){
		CorsoDAO c= new CorsoDAO();
		return c.getTuttiICorsi();
		
	}
	
	public Studente trovaStudente(int matricola){
		StudenteDAO s=new StudenteDAO();
		return s.getStudente(matricola);
	}
	
	public List<Studente> cercaIscritti(Corso corso){
		CorsoDAO c= new CorsoDAO();
		return c.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> cercaCorsi(Studente s){
		StudenteDAO studente= new StudenteDAO();
		return studente.getCorsiACuiEIscrittoUnoStudente(s);
	
	}
	
	public boolean iscriviStudente(Studente studente, Corso corso){
		CorsoDAO c= new CorsoDAO();
		return c.inscriviStudenteACorso(studente, corso);
		
	}
}
