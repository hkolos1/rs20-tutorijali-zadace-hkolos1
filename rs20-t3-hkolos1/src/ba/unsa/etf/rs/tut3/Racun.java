package ba.unsa.etf.rs.tut3;

import java.util.ArrayList;

public class Racun {

    private ArrayList<Proizvodi> listaProizvoda;

    public Racun() {
        listaProizvoda =new ArrayList<>();
    }

    public void dodajStavku(Artikal proizvodi, int kolicina){
        listaProizvoda.add(new Proizvodi(proizvodi,kolicina));
    }
    public double ukupanIznos(){
        double I=0;
        double suma_proizvoda;
        for(Proizvodi j: listaProizvoda){
            suma_proizvoda=j.kolicina *j.proizvodi.getCijena();
            I+=suma_proizvoda;
        }
        return I;
    }


}
