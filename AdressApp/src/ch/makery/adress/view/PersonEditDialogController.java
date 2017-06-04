package ch.makery.adress.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ch.makery.adress.DBConnect;
import ch.makery.adress.model.Osoba;



public class PersonEditDialogController {

    @FXML
    private TextField nazwisko;
    @FXML
    private TextField imie;
    @FXML
    private TextField kod_karty;
    @FXML
    private Label nr_pracownika;


    private Stage dialogStage;
    private Osoba osoba;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;

        nazwisko.setText(osoba.getNazwisko());
        imie.setText(osoba.getImie());
        nr_pracownika.setText("Będzie widoczny po zatwierdzeniu");
        kod_karty.setText(osoba.getKodKarty());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	osoba.setImie(imie.getText());
        	osoba.setNazwisko(nazwisko.getText());
        	osoba.setKodKarty(kod_karty.getText());
        	
        	try{
        		
        		DBConnect pol = new DBConnect();
        		String zapytanie = "UPDATE pracownicy SET imie='"+osoba.getImie()+"', nazwisko='"+osoba.getNazwisko()+
        				"', kod_karty='"+osoba.getKodKarty()+"' WHERE nr_pracownika="+
        		osoba.getNrPracownika();
        		//System.out.println(zapytanie);
        		if(pol.st.executeUpdate(zapytanie)==0)
        		{
        			zapytanie = "INSERT INTO pracownicy (imie, nazwisko, nr_pracownika, kod_karty) VALUES('"+
        		osoba.getImie()+"', '"+osoba.getNazwisko()+"', NULL, '"+osoba.getKodKarty()+"')";
        			//System.out.println(zapytanie);
        			pol.st.executeUpdate(zapytanie);
        		}
    		}catch(Exception e){
    			System.out.println(e);
    		};

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (imie.getText() == null || imie.getText().length() == 0) {
            errorMessage += "Nie wprowadzono imienia!\n"; 
        }
        if (nazwisko.getText() == null || nazwisko.getText().length() == 0) {
            errorMessage += "Nie wprowadzono nazwiska!\n"; 
        }
        if (kod_karty.getText() == null || kod_karty.getText().length() == 0) {
            errorMessage += "Nie wprowadzono kodu karty!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Błędne dane");
            alert.setHeaderText("Proszę sprawdzić wprowadzone dane");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}