package ba.unsa.etf.rs.tut4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextArea unijeti;
    public TextArea bezdupli;
    public ChoiceBox<Artikal> choice;
    public Spinner<Integer> K;
    public TextArea ukupniracun;
    private ArrayList<Artikal> lista=new ArrayList<>();
    private String[] nefiltriraniartikli;
    private static ObservableList<Artikal> A = FXCollections.observableArrayList();
    ArrayList<String> ispisracuna = new ArrayList<>();
    double kosta=0;

    public void artiklibezduplih(ActionEvent actionEvent) {
        nefiltriraniartikli=unijeti.getText().split("\n");
        for(String i :nefiltriraniartikli){
            lista.add(new Artikal(i));
        }
        A.clear();
        Artikal.izbaciDuplikate(lista);
        A.addAll(lista);
        String poslijeizbacivanja=" ";
        for(Artikal j:lista){
            poslijeizbacivanja+=j+"\n";
        }
        bezdupli.setText(poslijeizbacivanja);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        A.addAll(lista);
        choice.setItems(A);
        SpinnerValueFactory<Integer> kolicina = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        this.K.setValueFactory(kolicina);
    }

    public void Racun(ActionEvent actionEvent) {
        Racun rac=new Racun();
        String proizvod = String.valueOf(choice.getValue().getSifra());
        Artikal rezultat = new Artikal();
        int kolicina_jednog_proizvoda = K.getValue();
        for (int i=0; i<lista.size();i++){
            if (proizvod.equals(lista.get(i).getSifra())){
                rac.dodajStavku(lista.get(i),kolicina_jednog_proizvoda);
                rezultat=lista.get(i);
            }
        }
        kosta += rac.ukupanIznos();
        ispisracuna.add(String.format("%6s%10d%11.2f",proizvod,kolicina_jednog_proizvoda,rezultat.getCijena()*kolicina_jednog_proizvoda));
        StringBuilder ispisracunabuilder = new StringBuilder();
        for (int i=0; i<ispisracuna.size();i++){
            ispisracunabuilder.append(ispisracuna.get(i));
            ispisracunabuilder.append("\n");
        }
        ispisracunabuilder.append(String.format("UKUPNO%20.2f", kosta));
        ukupniracun.setText(ispisracunabuilder.toString());
    }
}