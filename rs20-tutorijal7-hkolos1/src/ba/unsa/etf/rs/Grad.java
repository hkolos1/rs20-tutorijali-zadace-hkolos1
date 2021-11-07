package ba.unsa.etf.rs;

public class Grad {
    private int id, brojStanovnika;
    private String naziv;
    private Drzava drzava;

    public Grad(int id, int brojStanovnika, Drzava drzava, String naziv) {
        this.id = id;
        this.brojStanovnika = brojStanovnika;
        this.drzava = drzava;
        this.naziv = naziv;
    }

    public Grad() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }


    @Override
    public String toString() {
        return  '\n'+
                "id=" + id +
                "'\n' brojStanovnika=" + brojStanovnika +
                "'\n' naziv='" + naziv + '\'' +
                "'\n' drzava=" + drzava + '\n';
    }
}
