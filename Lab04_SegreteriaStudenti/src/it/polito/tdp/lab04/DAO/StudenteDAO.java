package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public Studente getStudente(int matricola){
		
		String sql="SELECT * FROM studente WHERE matricola=?";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			Studente s= null;
			
			if (rs.next()){
				s= new Studente(rs.getInt("matricola"),
											rs.getString("cognome"),
											rs.getString("nome"),
											rs.getString("CDS"));
			}
			
			conn.close();
			return s;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		
		
	}

	public List<Corso> getCorsiACuiEIscrittoUnoStudente(Studente studente) {
		
		List <Corso> corsi= new LinkedList<Corso>();
		String sql="SELECT * "+
                "FROM corso "+
                "WHERE codIns IN (SELECT DISTINCT codIns "+
				                       "FROM iscrizione "+
				                       "WHERE matricola=?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, studente.getMatricola());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Corso c= new Corso(rs.getString("codins"),
										rs.getInt("crediti"),
										rs.getString("nome"),
										rs.getInt("pd"));
				corsi.add(c);
				
			}
			
			conn.close();
			return corsi;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}


}
