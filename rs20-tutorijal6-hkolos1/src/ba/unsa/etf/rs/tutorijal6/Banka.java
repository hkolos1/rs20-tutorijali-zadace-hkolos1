package ba.unsa.etf.rs.tutorijal6;

import java.util.ArrayList;
import java.util.List;

public class Banka {
    private static long brojRacuna = 1000000000000000L;
    private List<Uposlenik> uposleni;
    private List<Korisnik> korisnici;

    public Banka() {
        uposleni = new ArrayList<>();
        korisnici = new ArrayList<>();
    }

    public List<Uposlenik> getUposleni() {
        return uposleni;
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public Korisnik kreirajNovogKorisnika(String ime, String prezime) {
        Korisnik k = new Korisnik(ime, prezime);
        korisnici.add(k);
        return k;
    }
    public Uposlenik kreirajNovogUposlenog(String ime, String prezime) {
        Uposlenik u = new Uposlenik(ime, prezime);
        uposleni.add(u);
        return u;
    }
    public Racun kreirajRacunZaKorisnika(Korisnik k1) throws Exception {
        if(!korisnici.contains(k1))
            throw new IllegalAccessException("Korisnik ne postoji");
        Racun r = new Racun (1000000000000000L, k1);
        k1.dodajRacun(r);
        return r;
    }
}
