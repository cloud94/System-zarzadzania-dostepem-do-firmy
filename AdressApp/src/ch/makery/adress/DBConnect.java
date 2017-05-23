package ch.makery.adress;

import java.sql.*;

public class DBConnect {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnect()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pracownicy", "root", "");
			st = con.createStatement();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void pobierzDane()
	{
		try{
			String zapytanie = "select * from pracownicy";
			rs = st.executeQuery(zapytanie);
			System.out.println("Rekordy bazy danych: ");
			while(rs.next()){
				String imie = rs.getString("imie");
				String nazwisko = rs.getString("nazwisko");
				int numer = rs.getInt(3);
				String kod = rs.getString("kod_karty");
				System.out.println("Imię: "+imie +", Nazwisko: "+nazwisko
						+", Nr pracownika: "+numer+", Kod karty: "+kod);
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
//	public String pobierzImie()
//	{
//		try{
//			String zapytanie = "select * from pracownicy";
//		}
//	}
	
}
