package ba.unsa.etf.rs.tut3;

public class Proizvodi {
    public Artikal proizvodi;
    public int kolicina;

    public Artikal getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(Artikal proizvodi) {
        this.proizvodi = proizvodi;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Proizvodi(Artikal proizvodi, int kolicina) {
        this.proizvodi = proizvodi;
        this.kolicina = kolicina;
    }
}