package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


}
