package ba.unsa.etf.rs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ZadacaController {

    @FXML
    public void unosStudenta(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/novi.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene((Parent) loader.load()));
        stage.showAndWait();
    }
    public ChoiceBox choiceColor ;
    public Slider sliderStudents;
    public TextField fldText;
    public ListView lvStudents;

    public ArrayList<String> nizStringova =new ArrayList<String>();
    public static ObservableList<String> listaStudenata;
    @FXML
    public void initialize() {
        sliderStudents.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,Number oldValue, Number newValue) {
                promjenaSlidera(newValue.intValue());
            }
        });
        for(int i=1;i<=14;i++) {
            String imeStudenta = "Student" + i;
            nizStringova.add(imeStudenta);
        }
        nizStringova.add("Student");
        ArrayList<String> pomocniNiz=new ArrayList<String>();
        for(int i=0;i<sliderStudents.getValue();i++){
            pomocniNiz.add(nizStringova.get(i));
        }

        listaStudenata = FXCollections.observableArrayList(pomocniNiz);
        //Odabir boje numerickih tipki
        lvStudents.setItems(listaStudenata);
        choiceColor.setItems(FXCollections.observableArrayList(
                "Default", "Crvena","Zelena", "Plava")
        );
    }
    public void promjenaSlidera(int broj){
        listaStudenata.clear();
        for(int i=listaStudenata.size(); i<broj; i++){
            if(broj==15){
                listaStudenata.add("Student" + fldText.getText()); continue;
            }
            listaStudenata.add("Student" + (i+1));
        }
    }
    public Button dugme0, dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9;
    public void obojiTipke (){
        String colorValue =(String)choiceColor.getValue();
        if(colorValue=="Default") {
            for (Button button : Arrays.asList(dugme0, dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9)) {
                button.styleProperty().setValue("");
            }
        }
        if(colorValue=="Crvena") {
            for (Button button : Arrays.asList(dugme0, dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9)) {
                button.styleProperty().setValue("-fx-background-color:red;");
            }
        }
        if(colorValue=="Plava") {
            for (Button button : Arrays.asList(dugme0, dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9)) {
                button.styleProperty().setValue("-fx-background-color:blue;");
            }
        }
        if(colorValue=="Zelena") {
            for (Button button : Arrays.asList(dugme0, dugme1, dugme2, dugme3, dugme4, dugme5, dugme6, dugme7, dugme8, dugme9)) {
                button.styleProperty().setValue("-fx-background-color:green;");
            }
        }
    }
    //numericki dio 0-9
    public void nulaAction(ActionEvent actionEvent) {
        String broj = fldText.getText().toString();
        fldText.setText(broj += "0");
    }
    public void jedanAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "1");
    }
    public void dvaAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "2");
    }
    public void triAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "3");
    }
    public void cetriAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "4");
    }
    public void petAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "5");
    }
    public void sestAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "6");
    }
    public void sedamAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "7");
    }
    public void osamAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "8");
    }
    public void devetAction(ActionEvent actionEvent){
        String broj = fldText.getText().toString();
        fldText.setText(broj += "9");
    }
}