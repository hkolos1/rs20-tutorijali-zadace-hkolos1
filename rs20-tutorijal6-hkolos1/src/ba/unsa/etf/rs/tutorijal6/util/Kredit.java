package ba.unsa.etf.rs.tutorijal6.util;

import ba.unsa.etf.rs.tutorijal6.Korisnik;

public class Kredit {

    public void dajKreditnuSposobnost(KreditnaSposobnost kreditnaSposobnost, Korisnik korisnik){
        kreditnaSposobnost.provjeri(korisnik.getRacun());
    }
}
