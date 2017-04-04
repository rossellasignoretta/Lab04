package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo Corso alla lista
				
				Corso c= new Corso(rs.getString("codins"),
									rs.getInt("crediti"),
									rs.getString("nome"),
									rs.getInt("pd"));
				
				corsi.add(c);
			}
			
			conn.close();
			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codIns) {
		String sql="SELECT * FROM corso WHERE codins='?'";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, codIns);
			ResultSet rs = st.executeQuery();

			Corso c= null;
			
			while (rs.next()) {

				c= new Corso(rs.getString("codins"),
							rs.getInt("crediti"),
							rs.getString("nome"),
							rs.getInt("pd"));
				
			}
			
			conn.close();
			return c;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		List <Studente> studenti= new LinkedList<Studente>();
		String sql="SELECT * "+
                "FROM studente "+
                "WHERE matricola IN (SELECT DISTINCT matricola "+
				                       "FROM iscrizione "+
				                       "WHERE codins=?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, corso.getCodIns());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Studente s= new Studente(rs.getInt("matricola"),
										rs.getString("cognome"),
										rs.getString("nome"),
										rs.getString("CDS"));
				studenti.add(s);
				
			}
			
			conn.close();
			return studenti;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento,
	 * iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		String sql="INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES (?, ?);";
			
			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, studente.getMatricola());
				st.setString(2, corso.getCodIns());
				
				int ris=st.executeUpdate();
				
				conn.close();
				if (ris==1){
					return true;
				}else{
					return false;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Errore Db");
			}

	}
}
