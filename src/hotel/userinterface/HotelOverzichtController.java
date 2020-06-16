package hotel.userinterface;

import hotel.model.Boeking;
import hotel.model.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HotelOverzichtController {
    @FXML private Label hotelnaamLabel;
    @FXML private ListView<String> boekingenListView;
    @FXML private DatePicker overzichtDatePicker;

    private Hotel hotel = Hotel.getHotel();

    public void initialize() {
        hotelnaamLabel.setText("Boekingen hotel " + hotel.getNaam());
        overzichtDatePicker.setValue(LocalDate.now());
        toonBoekingen();
    }

    public void toonVorigeDag() {
        LocalDate dagEerder = overzichtDatePicker.getValue().minusDays(1);
        overzichtDatePicker.setValue(dagEerder);
    }

    public void toonVolgendeDag() {
        LocalDate dagLater = overzichtDatePicker.getValue().plusDays(1);
        overzichtDatePicker.setValue(dagLater);
    }

    public void nieuweBoeking() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Boekingen.fxml"));
        Parent root = loader.load();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
        initialize();
    }

        // Maak in je project een nieuwe FXML-pagina om boekingen te kunnen invoeren
        // Open de nieuwe pagina in deze methode
        // Zorg dat de gebruiker ondertussen geen gebruik kan maken van de HotelOverzicht-pagina
        // Update na sluiten van de nieuwe pagina het boekingen-overzicht

    public void toonBoekingen() {
        ObservableList<String> boekingen = FXCollections.observableArrayList();
        LocalDate datum = overzichtDatePicker.getValue();
        Hotel hotel = Hotel.getHotel();

        for (Boeking boeking : hotel.getBoekingen()) {
            LocalDate aankomstdatum = boeking.getAankomstDatum();
            LocalDate vertrekdatum = boeking.getVertrekDatum();
            if ((aankomstdatum.isBefore(datum.plusDays(1)) && vertrekdatum.isAfter(datum.minusDays(1)))) {
                boekingen.add(String.format("Begindatum: %tD,  Einddatum: %tD,  Kamernummer: %d,  Klantnaam: %s",
                        aankomstdatum, vertrekdatum, boeking.getKamer().getKamerNummer(),
                        boeking.getBoeker().getNaam()));
            }
        }
        // Vraag de boekingen op bij het Hotel-object.
        // Voeg voor elke boeking in nette tekst (string) toe aan de boekingen-lijst.
        boekingenListView.setItems(boekingen);
    }
}