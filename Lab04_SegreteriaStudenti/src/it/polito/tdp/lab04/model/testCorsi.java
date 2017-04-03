package it.polito.tdp.lab04.model;

public class testCorsi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m= new Model();
		
		for(String stemp: m.getNomiCorsi())
			System.out.println(stemp);
		
		Studente s=m.trovaStudente(146101);
		System.out.println(s.getNome());
	}

}
