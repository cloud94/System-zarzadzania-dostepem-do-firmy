package ch.makery.adress;

import java.io.IOException;
//import java.sql.*;
import javafx.collections.FXCollections;
import ch.makery.adress.view.*;
import ch.makery.adress.model.Osoba;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Osoba> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dane pracowników");

        initRootLayout();

        showPersonOverview();
    }
    
    public MainApp() {
        // Add some sample data
    	
//    	DBConnect connect = new DBConnect();
//    	connect.pobierzDane();
    	
        personData.add(new Osoba("Jan", "Kowalski"));
        personData.add(new Osoba("Adam", "Małysz"));
        personData.add(new Osoba("Mateusz", "Kowalski"));
        personData.add(new Osoba("Adam", "Psikuta"));
        personData.add(new Osoba("Jan", "Muzykant"));
        personData.add(new Osoba("Adrian", "Nowak"));
        personData.add(new Osoba("Mariusz", "Kowal"));
        personData.add(new Osoba("Stefan", "Będzki"));
        personData.add(new Osoba("Robert", "Kubica"));
//        try{
//			String zapytanie = "select * from pracownicy";
//			
//			rs = st.executeQuery(zapytanie);
//			System.out.println("Rekordy bazy danych: ");
//			while(rs.next()){
//				String imie = rs.getString("imie");
//				String nazwisko = rs.getString("nazwisko");
//				int numer = rs.getInt(3);
//				String kod = rs.getString("kod_karty");
//				System.out.println("Imię: "+imie +" Nazwisko: "+nazwisko
//						+" Nr pracownika: "+numer+" Kod karty: "+kod);
//			}
//		}catch(Exception e)
//		{
//			System.out.println(e);
//		}
    }
    
    public ObservableList<Osoba> getPersonData() {
        return personData;
    }

// funkcja inicjalizuje widok podstawowy
    public void initRootLayout() {
        try {
            // laduje widok z pliku RootLayout.fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

           
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// pokazuje widok informacji wewnatrz "RootLayout"
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Informacje.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            OsobaOverviewController controller = loader.getController();
            controller.setMainApp(this);

       } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
    	DBConnect polaczenie = new DBConnect();
    	polaczenie.pobierzDane();
        launch(args);
    }
}