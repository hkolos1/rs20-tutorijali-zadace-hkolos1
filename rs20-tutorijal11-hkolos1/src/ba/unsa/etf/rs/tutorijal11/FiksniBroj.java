package ba.unsa.etf.rs.tutorijal11;

import java.util.Objects;

public class FiksniBroj extends TelefonskiBroj implements Comparable<TelefonskiBroj> {

    private final Grad grad;
    private final String broj;

    FiksniBroj(Grad grad, String broj) {
        this.grad = grad;
        this.broj = broj;
    }

    public enum Grad{
        SARAJEVO("033"),
        TUZLA("035"),
        ZENICA("032"),
        BRCKO("049"),
        MOSTAR("036"),
        TRAVNIK("030"),
        LIVNO("034"),
        BIHAC("037"),
        GORAZDE("038"),
        ORASJE("031"),
        SIROKIBRIJEG("039");

        private final String grad;
        Grad(String broj){
            grad = broj;
        }

        public String getGrad(){
            return grad;
        }

        @Override
        public String toString(){
            return grad;
        }
    }

    public Grad getGrad(){
        return grad;
    }

    @Override
    public String toString(){
        return ispisi();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        FiksniBroj that = (FiksniBroj) o;
        return grad == that.grad && Objects.equals(broj, that.broj);
    }

    @Override
    public int compareTo(TelefonskiBroj o){
        return ispisi().compareTo(o.ispisi());
    }

    @Override
    public int hashCode(){
        return Objects.hash(grad, broj);
    }

    public String ispisi(){
        return grad.toString() + "/" + broj;
    }
}
