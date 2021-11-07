package ba.unsa.etf.rs.zadaca3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.regex.Pattern;

public class KorisnikController {
    public TextField fldIme;
    public TextField fldPrezime;
    public TextField fldEmail;
    public TextField fldUsername;
    public ListView<Korisnik> listKorisnici;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Slider sliderGodinaRodjenja;
    public ImageView imeImg, prezimeImg, userImg, emailImg, passImg, repeatImg, godinaImg;

    private KorisniciModel model;

    public KorisnikController(KorisniciModel model) {
        this.model = model;
    }
    private static final String NAME = "^[a-zA-Z-ČčĆćĐđŽž\\s]{3,}$";
    private static final String EMAIL = "^.+@.+$";
    private static final String USER = "^[\\w_]{1,16}$";

    @FXML
    public void initialize() {
        listKorisnici.setItems(model.getKorisnici());
        listKorisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            model.setTrenutniKorisnik(newKorisnik);
            listKorisnici.refresh();
        });
        model.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldIme.textProperty().unbindBidirectional(oldKorisnik.imeProperty() );
                fldPrezime.textProperty().unbindBidirectional(oldKorisnik.prezimeProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldPassword.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().unbindBidirectional(oldKorisnik.godinaRodjenjaProperty());
            }
            if (newKorisnik == null) {
                fldIme.setText("");
                fldPrezime.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldPassword.setText("");
                fldPasswordRepeat.setText("");
                sliderGodinaRodjenja.setValue(2000);
            }
            else {
                fldIme.textProperty().bindBidirectional( newKorisnik.imeProperty() );
                fldPrezime.textProperty().bindBidirectional( newKorisnik.prezimeProperty() );
                fldEmail.textProperty().bindBidirectional( newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional( newKorisnik.usernameProperty() );
                fldPassword.textProperty().bindBidirectional( newKorisnik.passwordProperty() );
                sliderGodinaRodjenja.valueProperty().bindBidirectional(newKorisnik.godinaRodjenjaProperty());
            }
        });
        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
                imeImg.setImage(new Image("/img/check_ok.png"));

            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
                imeImg.setImage(new Image("/img/cross_not_ok.png"));
            }
        });
        fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            provjeraValidacije(fldIme, Pattern.matches(NAME, newIme));
        });

        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                fldPrezime.getStyleClass().add("poljeIspravno");
                prezimeImg.setImage(new Image("/img/check_ok.png"));
            } else {
                fldPrezime.getStyleClass().removeAll("poljeIspravno");
                fldPrezime.getStyleClass().add("poljeNijeIspravno");
                prezimeImg.setImage(new Image("/img/cross_not_ok.png"));
            }
        });
        fldPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
            provjeraValidacije(fldPrezime, Pattern.matches(NAME, newIme));
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fldEmail.getStyleClass().add("poljeIspravno");
                emailImg.setImage(new Image("/img/check_ok.png"));
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
                emailImg.setImage(new Image("/img/cross_not_ok.png"));
            }
        });
        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            provjeraValidacije(fldEmail, Pattern.matches(EMAIL, newIme));
        });

        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
                userImg.setImage(new Image("/img/check_ok.png"));
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
                userImg.setImage(new Image("/img/cross_not_ok.png"));
            }
        });
        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            provjeraValidacije(fldUsername, Pattern.matches(USER, newIme));
        });
        fldPassword.textProperty().addListener((observableValue, s, t1) -> {
            if(!t1.isEmpty() && fldPassword.getText().trim().equals(fldPasswordRepeat.getText().trim())){
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().addAll("poljeIspravno");

                fldPasswordRepeat.getStyleClass().addAll("poljeIspravno");
                passImg.setImage(new Image("/img/check_ok.png"));
                repeatImg.setImage(new Image("/img/check_ok.png"));
            } else if(model.getTrenutniKorisnik() != null && !model.getTrenutniKorisnik().checkPassword()) {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().addAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().addAll("poljeNijeIspravno");
                passImg.setImage(new Image("/img/cross_not_ok.png"));
                repeatImg.setImage(new Image("/img/cross_not_ok.png"));
            }
        });
        fldPasswordRepeat.textProperty().addListener((observableValue, s, t1) -> {
            if(!t1.isEmpty() && fldPassword.getText().trim().equals(fldPasswordRepeat.getText().trim())){
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().addAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().addAll("poljeIspravno");
                passImg.setImage(new Image("/img/check_ok.png"));
                repeatImg.setImage(new Image("/img/check_ok.png"));
            } else if(model.getTrenutniKorisnik() != null && !model.getTrenutniKorisnik().checkPassword()) {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPasswordRepeat.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().addAll("poljeNijeIspravno");
                fldPasswordRepeat.getStyleClass().addAll("poljeNijeIspravno");
                passImg.setImage(new Image("/img/cross_not_ok.png"));
                repeatImg.setImage(new Image("/img/cross_not_ok.png"));
            }
        });
        sliderGodinaRodjenja.valueProperty().addListener((obs, oldValue, newValue ) ->{
            if(newValue.intValue()<1920 || newValue.intValue()>2019){
                godinaImg.setImage(new Image("/img/cross_not_ok.png"));
            } else {
                godinaImg.setImage(new Image("/img/check_ok.png"));
            }
        });
    }
    private void provjeraValidacije(Node node, boolean poljeOk) {
        if (poljeOk) {
            node.getStyleClass().removeAll("poljeNijeIspravno");
            node.getStyleClass().add("poljeIspravno");
        } else {
            node.getStyleClass().removeAll("poljeIspravno");
            node.getStyleClass().add("poljeNijeIspravno");
        }
    }

    public void dodajAction(ActionEvent actionEvent) {
        model.getKorisnici().add(new Korisnik("", "", "", "", ""));
        listKorisnici.getSelectionModel().selectLast();
    }
    public void obrisiAction(ActionEvent actionEvent) {
        model.getKorisnici().remove(listKorisnici.getSelectionModel().getSelectedItem());
        listKorisnici.refresh();
    }
    public void generisiAction(ActionEvent actionEvent){
        String trenutnoIme = model.getTrenutniKorisnik().getIme();
        String trenutnoPrezime = model.getTrenutniKorisnik().getPrezime();
        if(trenutnoIme.isEmpty()) return;
        trenutnoIme = Slova(trenutnoIme);
        trenutnoPrezime = Slova(trenutnoPrezime);
        String rezultat = trenutnoIme.substring(0, 1);
        rezultat = rezultat + trenutnoPrezime;
        model.getTrenutniKorisnik().setUsername(rezultat);
    }
    public String Slova(String letter){
        letter = letter.toLowerCase();
        letter = letter.replace("ć", "c");
        letter = letter.replace("č", "c");
        letter = letter.replace("đ", "d");
        letter = letter.replace("š", "s");
        letter = letter.replace("ž", "z");
        return letter;
    }
    public void krajAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
