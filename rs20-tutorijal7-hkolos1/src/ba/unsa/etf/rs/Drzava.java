package ba.unsa.etf.rs;

public class Drzava {
    private int id;
    private Grad glavniGrad;
    private String naziv;

    public Drzava(int id, Grad glavniGrad, String naziv) {
        this.id = id;
        this.glavniGrad = glavniGrad;
        this.naziv = naziv;
    }

    public Drzava() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Grad getGlavniGrad() {
        return glavniGrad;
    }

    public void setGlavniGrad(Grad glavniGrad) {
        this.glavniGrad = glavniGrad;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
