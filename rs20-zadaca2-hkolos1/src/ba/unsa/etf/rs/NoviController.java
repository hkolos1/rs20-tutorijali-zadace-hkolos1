package ba.unsa.etf.rs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NoviController {
    public TextField fldIme;
    public Button btnPrijava;

    @FXML
    public void prijavaOK(ActionEvent actionEvent) throws IOException {
        String userStudenta = new String();
        userStudenta =fldIme.getText();
        if (userStudenta.length() < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravno ime");
            alert.setHeaderText("Neispravno ime studenta");
            alert.setContentText("Ime studenta treba biti najmanje 10 karaktera dugačko");
            alert.show();
        }else {
            ZadacaController.listaStudenata.add(userStudenta);
            btnPrijava.getScene().getWindow().hide();
        }
    }
    public Button btnCancel;
    public void prijavaNOK(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    //Metoda za progressBar
    public ProgressBar progressBar;
    public void promjenaTeksta(){
        if(fldIme.getText().length()<10){
            progressBar.setProgress(fldIme.getText().length()/10.);
            progressBar.getStyleClass().removeAll("zeleniProgress");
            progressBar.getStyleClass().add("crveniProgress");
        }
        if(fldIme.getText().length()>=10){
            progressBar.setProgress(fldIme.getText().length()/10.);
            progressBar.getStyleClass().add("zeleniProgress");
            progressBar.getStyleClass().removeAll("crveniProgress");
        }
    }
}