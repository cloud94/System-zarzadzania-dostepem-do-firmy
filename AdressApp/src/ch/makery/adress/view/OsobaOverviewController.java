package ch.makery.adress.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ch.makery.adress.*;
import ch.makery.adress.model.*;

public class OsobaOverviewController {
    @FXML
    private TableView<Osoba> osobaTable;
    @FXML
    private TableColumn<Osoba, String> imieColumn;
    @FXML
    private TableColumn<Osoba, String> nazwiskoColumn;

    @FXML
    private Label imie;
    @FXML
    private Label nazwisko;
    @FXML
    private Label nr_pracownika;
    @FXML
    private Label kod_karty;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     * @return 
     */
    public void osobaOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        imieColumn.setCellValueFactory(cellData -> cellData.getValue().imieProperty());
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());
        // czysci info o osobie
        showOsobaDetails(null);
        // pokazuje info o zaznaczonej osobie
        osobaTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue)->showOsobaDetails(newValue));
    }
    
    // metoda usuwajaca osobe z tabeli -uzywana w przypadku klikniecia "usun"
    @FXML
    private void usunOsobe(){
    	int wybor = osobaTable.getSelectionModel().getSelectedIndex();
    	if(wybor>=0)
    	{
    		try{
    			Osoba os = osobaTable.getSelectionModel().getSelectedItem();
        		
        		DBConnect pol = new DBConnect();
        		String zapytanie = "DELETE FROM pracownicy WHERE nr_pracownika="+
        		os.getNrPracownika();
        		//System.out.println(zapytanie);
        		pol.st.executeUpdate(zapytanie);
    		}catch(Exception e){
    			System.out.println(e);
    		};
    		
    		osobaTable.getItems().remove(wybor);
    	}
    	else
    	{
    		// nic nie zaznaczono
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak wyboru");
            alert.setHeaderText("Nie zaznaczono osoby.");
            alert.setContentText("Proszę zaznaczyć osobę do usunięcia.");

            alert.showAndWait();
    	}
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        osobaTable.setItems(mainApp.getPersonData());
    }
    
    private void showOsobaDetails(Osoba os)
    {
    	if(os!=null)
    	{
    		imie.setText(os.getImie());
    		nazwisko.setText(os.getNazwisko());
    		nr_pracownika.setText(Integer.toString(os.getNrPracownika()));
    		kod_karty.setText(os.getKodKarty());
    	}
    	// jesli jest puste
    	else
    	{
    		imie.setText("Brak info");
    		nazwisko.setText("Brak info");
    		nr_pracownika.setText("Brak info");
    		kod_karty.setText("Brak info");
    	}
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Osoba tempPerson = new Osoba();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Osoba selectedPerson = osobaTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showOsobaDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak wyboru");
            alert.setHeaderText("Nie zaznaczono pracownika");
            alert.setContentText("Proszę wybrać osobę z tabeli");

            alert.showAndWait();
        }
    }

}