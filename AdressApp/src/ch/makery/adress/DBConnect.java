package ch.makery.adress;

import java.sql.*;
import ch.makery.adress.MainApp;
import ch.makery.adress.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnect {

	private Connection con;
	public Statement st;
	public ResultSet rs;
	private ObservableList<Osoba> listaOsob;
	
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
	
}
