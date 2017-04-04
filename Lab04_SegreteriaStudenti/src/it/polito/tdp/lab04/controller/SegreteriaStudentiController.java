package it.polito.tdp.lab04.controller;


import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	private Model model;
	List<Corso> corsi = new LinkedList<Corso>();

	@FXML
	private ComboBox<Corso> comboCorso;

	@FXML
	private Button btnCercaIscrittiCorso;

	@FXML
	private Button btnCercaCorsi;

	@FXML
	private Button btnCercaNome;

	@FXML
	private TextArea txtResult;

	@FXML
	private Button btnIscrivi;

	@FXML
	private TextField txtMatricola;

	@FXML
	private Button btnReset;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtCognome;

	public void setModel(Model model) {
		this.model=model;
		comboCorso.getItems().add(new Corso(null, -1, "Corsi", -1));
		comboCorso.getItems().addAll(model.getCorsi());
	}

	@FXML
	void doReset(ActionEvent event) {
		txtMatricola.clear();
		txtNome.clear();
		txtCognome.clear();
		txtResult.clear();

	}

	@FXML
	void doCercaNome(ActionEvent event) {
		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();
		int matricola=-1;
		try{
			matricola=Integer.parseInt(txtMatricola.getText());
		}catch(NumberFormatException nfe){
			txtResult.setText("Inserisci un intero!");
			return;
		}
		
		Studente s=model.trovaStudente(matricola);
		if (s!=null){
			txtNome.setText(s.getNome());
			txtCognome.setText(s.getCognome());
		}else{
			txtResult.setText("Matricola inesistente!");
			return;
		}

	}

	@FXML
	void doCercaIscrittiCorso(ActionEvent event) {
		txtResult.clear();
		Corso corso=comboCorso.getValue();
		
		if (corso.getNome().equals("Corsi")){
			txtResult.setText("Nessun corso selezionato");
			return;
		}

		List<Studente> studenti=model.cercaIscritti(corso);
		for(Studente s: studenti){
			txtResult.appendText(s.getMatricola()+"   "+s.getCognome()+"   "+s.getNome()+"   "+s.getCDS()+"\n");
		}
	}

	@FXML
	void doCercaCorsi(ActionEvent event) {
		txtResult.clear();
		
		int matricola=-1;
		try{
			matricola=Integer.parseInt(txtMatricola.getText());
		}catch(NumberFormatException nfe){
		}
		
		Studente studente=model.trovaStudente(matricola);
		Corso corso=comboCorso.getValue();
		
		//CONTROLLO SE ESISTE LO STUDENTE
		if (studente==null){
			txtResult.setText("Matricola inesistente!");
			return;
		}
		//CERCO I CORSI A CUI è ISCRITTO
		List <Corso> corsi=model.cercaCorsi(studente);
		//CONTROLLO SE LO STUDENTE è ISCRITTO AD ALMENO UN CORSO
		if (corsi.size()==0){
			txtResult.setText("Studente iscritto a nessun corso");
			return;
			}
		//SE NON è STATO SELEZIONATO NESSUN CORSO STAMPO LA LISTA DI TUTTI I CORSI A CUI è ISCRITTO LO STUDENTE
		if (corso.getNome().equals("Corsi") ){
			for(Corso  c: corsi){
				txtResult.appendText(c.getCodIns()+"   "+c.getCrediti()+"   "+c.getNome()+"   "+c.getPd()+"\n");
				}
			}else{ //ALTRIMENTI CONTROLLO SE LO STUDENTE è ISCRITTO AL CORSO SELEZIONATO
				for(Corso  c: corsi){
					if(c.equals(corso)){
						txtResult.setText("Studente iscritto a questo corso");
						return;
					}
				txtResult.setText("Studente non iscritto a questo corso");
				}
			}
			
	}

	@FXML
	void doIscrivi(ActionEvent event) {
		txtResult.clear();
		
		int matricola=-1;
		try{
			matricola=Integer.parseInt(txtMatricola.getText());
		}catch(NumberFormatException nfe){
		}
		
		Studente studente=model.trovaStudente(matricola);
		Corso corso=comboCorso.getValue();
		
		//CONTROLLO SE ESISTE LO STUDENTE
		if (studente==null){
			txtResult.setText("Matricola inesistente!");
			return;
		}
		
		//CONTROLLO SE è STATO SELEZIONATO UN CORSO
		if (corso.getNome().equals("Corsi")){
			txtResult.setText("Nessun corso selezionato");
			return;
		}
		
		//CONTROLLO SE è GIà ISCRITTO A QUEL CORSO
		List <Corso> corsi=model.cercaCorsi(studente);
		for(Corso  c: corsi){
			if(c.equals(corso)){
				txtResult.setText("Studente già iscritto a questo corso");
				return;
				}
			}
		
		//ISCRIVO LO STUDENTE AL CORSO
		if(model.iscriviStudente(studente, corso)){
			txtResult.setText("Studente iscritto al corso!");
		}
		
	}

	@FXML
	void initialize() {
		assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnCercaNome != null : "fx:id=\"btnCercaNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
		
		comboCorso.setValue(new Corso(null, -1, "Corsi", -1));
	}

}
