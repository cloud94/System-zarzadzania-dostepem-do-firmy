package ch.makery.adress.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


//	Model klasy Osoba
 
public class Osoba {

    private final StringProperty imie;
    private final StringProperty nazwisko;
    private final IntegerProperty nr_pracownika;
    private final StringProperty kod_karty;

    // konstruktor domyslny
    public Osoba() {
        this(null, null);
    }

// konstruktor z imieniem i nazwiskiem
    public Osoba(String im, String naz) {
        this.imie = new SimpleStringProperty(im);
        this.nazwisko = new SimpleStringProperty(naz);

        // domyslne dane do testow
        this.nr_pracownika = new SimpleIntegerProperty();
        this.kod_karty = new SimpleStringProperty("kod");
    }

    public String getImie() {
        return imie.get();
    }

    public void setImie(String im) {
        this.imie.set(im);
    }

    public StringProperty imieProperty() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public void setNazwisko(String naz) {
        this.nazwisko.set(naz);
    }

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public int getNrPracownika() {
        return nr_pracownika.get();
    }

    public void setNrPracownika(int nr) {
        this.nr_pracownika.set(nr);
    }

    public IntegerProperty nrPracownikaProperty() {
        return nr_pracownika;
    }
    
    public String getKodKarty() {
        return kod_karty.get();
    }

    public void setKodKarty(String kod) {
        this.kod_karty.set(kod);
    }

    public StringProperty kodKartyProperty() {
        return kod_karty;
    }

}