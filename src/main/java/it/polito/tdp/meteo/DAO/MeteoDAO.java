package it.polito.tdp.meteo.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiMese(int mese) {
		final String sql = "SELECT s.Localita, s.Umidita, s.Data "
				+ "FROM situazione s "
				+ "WHERE MONTH(`Data`) = ? AND DAY(`Data`) <16";
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public Rilevamento[] getAllRilevamentiLocalitaMese(int mese, String localita) {
		final String sql = "SELECT s.Localita, s.Umidita, s.Data "
				+ "FROM situazione s "
				+ "WHERE MONTH(`Data`) = ? AND DAY(`Data`) <16 AND s.Localita = ? ";
		Rilevamento rilevamenti[] = new Rilevamento[15];

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();
			int i = 0;
			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti[i] = r;
				i++;
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String getUmiditaMedia(int mese){
		String sql = "SELECT s.Localita, AVG(s.Umidita) AS media "
				+ "FROM situazione s "
				+ "WHERE MONTH(`Data`) = ? "
				+ "GROUP BY s.Localita ";
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();
			String risultato = "";
			while(rs.next()) {
				risultato += rs.getString("Localita") + " " + rs.getDouble("media") + "\n";
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore connessione al DB");
			e.printStackTrace();
			return null;
		}
	}
	public List<String> getCitta(){
		String sql = "SELECT s.Localita FROM situazione s GROUP BY s.Localita"
				+ "\n"
				+ "";
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<String> risultato = new ArrayList<String>();
			while(rs.next()) {
				risultato.add(rs.getString("Localita"));
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore connessione al DB");
			e.printStackTrace();
			return null;
		}
	}

}
