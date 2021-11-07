package ba.unsa.etf.rs.tutorijal9;

import java.util.ArrayList;

public class UN {
    private ArrayList<Drzava> drzave = new ArrayList<Drzava>();

    public UN() {}

    public UN(ArrayList<Drzava> un) {
        this.drzave = un;
    }

    public ArrayList<Drzava> getDrzave() {
        return drzave;
    }

    public void setDrzave(ArrayList<Drzava> drzave) {
        this.drzave = drzave;
    }

    @Override
    public String toString(){
        return "UN{" +
                "drzave=" + drzave +
                '}';
    }
}
