package ba.unsa.etf.rs.tut4;

import org.junit.jupiter.api.Test;

class RacunTest {

    @Test
    void test1() {
        Racun r = new Racun();
        r.dodajStavku(new Artikal("HLB", "Hljeb", 1.1), 2);
        r.dodajStavku(new Artikal("JAJ", "Jaje", 0.25), 5);
        assertEquals(3.45, r.ukupanIznos());
    }

    private void assertEquals(double v, double ukupanIznos) {
    }

    @Test
    void testPrazno() {
        Racun r = new Racun();
        assertEquals(0, r.ukupanIznos());
    }

     @Test
    void testset_getkolicina(){
        Proizvodi r= new Proizvodi(new Artikal("IGRA", "Igračka", 5), 10);
        r.setKolicina(2);
        assertEquals(2, r.getKolicina());
     }
     
    @Test
    void testset_getproizvod(){
        Proizvodi r= new Proizvodi(new Artikal("IGRA", "Igračka", 5),10);
        r.setProizvodi(new Artikal("PRAK", "Praktikum", 20));
        assertEquals(new Artikal("PRAK","Praktikum",20),r.getProizvodi());
    }

    private void assertEquals(Artikal artikal, Artikal proizvodi) {
    }
}