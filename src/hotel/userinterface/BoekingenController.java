package hotel.userinterface;

import hotel.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class BoekingenController {
    @FXML private TextField naamTextField;
    @FXML private TextField adresTextField;
    @FXML private DatePicker aankomstdatumDatePicker;
    @FXML private DatePicker vertrekdatumDatePicker;
    @FXML private ComboBox<KamerType> kamertypeComboBox;
    @FXML private Button buttonBoek;
    @FXML private Label labelGegevens;

    private Hotel hotel = Hotel.getHotel();

    public void initialize() {
        labelGegevens.setText("");
        List<KamerType> kamertypes = hotel.getKamerTypen();
        kamertypeComboBox.setItems(FXCollections.observableList(kamertypes));
    }

    public void boek() {
        LocalDate checkDatum = LocalDate.now().minusDays(1);
        LocalDate aankomst = aankomstdatumDatePicker.getValue();
        LocalDate vertrek = vertrekdatumDatePicker.getValue();

        boolean nietInVerleden = aankomst.isAfter(checkDatum) && vertrek.isAfter(checkDatum);
        boolean vertrekNaAankomst = vertrek.isAfter(aankomst);
        boolean textIngevuld = naamTextField.getText() != null && adresTextField.getText() != null
                && aankomstdatumDatePicker.getValue() != null && vertrekdatumDatePicker.getValue() != null;
        boolean kamerSelected = kamertypeComboBox.getValue() != null;

        try {
            if (textIngevuld && nietInVerleden && vertrekNaAankomst && kamerSelected) {
                Hotel.getHotel().voegBoekingToe(aankomst, vertrek, naamTextField.getText(),
                        adresTextField.getText(), kamertypeComboBox.getValue());
                labelGegevens.setText("Succesvolle boeking!");
                Stage stage = (Stage) buttonBoek.getScene().getWindow();
                stage.close();
            }
            else{
                labelGegevens.setText("De ingevoerde gegevens zijn niet correct!");
            }
        }
        catch (Exception e) {
            labelGegevens.setText("Kamers zijn niet beschikbaar!");
        }
    }

    public void reset() {
        naamTextField.setText(null);
        adresTextField.setText(null);
        aankomstdatumDatePicker.setValue(null);
        vertrekdatumDatePicker.setValue(null);
        kamertypeComboBox.setValue(null);
    }
}
