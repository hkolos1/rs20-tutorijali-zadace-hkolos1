package ba.unsa.etf.rs.zadaca3;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.regex.Pattern;

public class Korisnik {
    private SimpleStringProperty ime, prezime, email, username, password;
    private SimpleDoubleProperty godinaRodjenja;

    public Korisnik() {}

    public Korisnik(String ime, String prezime, String email, String username, String password) {
        this(ime, prezime, email, username, password, 2000);
    }
    public boolean checkPassword() {
        //Regex kod posuđen sa interneta, kao i provjera za password
        String passwordRegex =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{1,}$";
        Pattern pat = Pattern.compile(passwordRegex);
        if (password.get() == null)
            return false;
        return (pat.matcher(password.get()).matches());
    }

    public Korisnik(String ime, String prezime, String email, String username, String password, int godinaRodjenja) {
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.godinaRodjenja = new SimpleDoubleProperty(godinaRodjenja);
    }

    @Override
    public String toString() {
        return prezime.get() + " " + ime.get();
    }

    public String getIme() {
        return ime.get();
    }

    public SimpleStringProperty imeProperty() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public String getPrezime() {
        return prezime.get();
    }

    public SimpleStringProperty prezimeProperty() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public double getGodinaRodjenja() {
        return godinaRodjenja.get();
    }

    public SimpleDoubleProperty godinaRodjenjaProperty() {
        return godinaRodjenja;
    }

    public void setGodinaRodjenja(double godinaRodjenja) {
        this.godinaRodjenja.set(godinaRodjenja);
    }
}