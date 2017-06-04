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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public ObservableList<Osoba> personData = FXCollections.observableArrayList();

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
    	
    	DBConnect polaczenie = new DBConnect();

    	try{
			//ObservableList<Osoba> listaOsob = FXCollections.observableArrayList();
			String zapytanie = "select * from pracownicy";
			polaczenie.rs = polaczenie.st.executeQuery(zapytanie);
			while(polaczenie.rs.next()){
				Osoba os = new Osoba();
				os.setImie(polaczenie.rs.getString("imie"));
				os.setNazwisko(polaczenie.rs.getString("nazwisko"));;
				os.setNrPracownika(polaczenie.rs.getInt(3));;
				os.setKodKarty(polaczenie.rs.getString("kod_karty"));;
				personData.add(os);
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
    	
    	
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
    
    public boolean showPersonEditDialog(Osoba person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edytuj dane pracownika");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOsoba(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}