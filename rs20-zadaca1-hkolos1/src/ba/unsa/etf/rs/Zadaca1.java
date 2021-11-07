package ba.unsa.etf.rs;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Zadaca1 {
    private static final String noviRed = System.lineSeparator();

    public static String najveciGrad(String a) {
        String[] red = a.split(noviRed);
        int maximal = 0;
        for (String s : red) {
            String[] brojStanovnika = s.split(",");
            if (Integer.parseInt((brojStanovnika[2].trim())) > maximal)
                maximal = Integer.parseInt((brojStanovnika[2]).trim());

        }

        for (String s : red) {

            String[] noviBrojstanovnika = s.split(",");
            if (Integer.parseInt((noviBrojstanovnika[2]).trim()) == maximal) {
                System.out.print(noviBrojstanovnika[0] + noviRed);
                return noviBrojstanovnika[1];
            }
        }
        return a;
    }
    public static int sumaCifara (int broj){
        int sumaCifara = 0;
        while (broj != 0){
            sumaCifara = sumaCifara + broj%10;
            broj = broj / 10;
        }
        return sumaCifara;
    }
    public static int najvecaSuma (int...brojevi){
        ArrayList<Integer> listNumber = new ArrayList<>();
        for (int broj : brojevi){
            listNumber.add(broj);
        }

        int suma = sumaCifara(listNumber.get(0));
        int temp;
        int brojNajvecaSuma = listNumber.get(0);
        for (int i = 1; i < listNumber.size(); i++){
            temp = sumaCifara(listNumber.get(i));
            if ( temp > suma){
                suma = temp;
                brojNajvecaSuma = listNumber.get(i);
            }
        }
        return brojNajvecaSuma;

    }
    public static double[] najmanjaSrednjaVrijednost(double[][] matrica) {
        double suma;
        double trenutna = 0;
        int cifra = 0;
        for (int i = 0; i < matrica[0].length; i++){
            trenutna = trenutna + matrica[0][i];
        }
        trenutna = trenutna / matrica[0].length;

        for (int i = 0; i < matrica.length; i++){
            suma = 0;
            for (int j = 0; j < matrica[i].length; j++){
                suma = suma + matrica[i][j];
            }
            suma = suma/matrica[i].length;
            if (suma < trenutna){
                trenutna = suma;
                cifra = i;
            }
        }
        return matrica[cifra];
    }
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
    //private final String noviRed = System.lineSeparator();
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));

        System.out.print("Unesite string sa gradovima (prazan red za kraj): ");
        Scanner ulaz = new Scanner(System.in);
        String s = "";
        do {
            String unosniIzraz = ulaz.nextLine();
            if (unosniIzraz.isEmpty())
                break;
            s = s + unosniIzraz + noviRed;
        }
        while (true);

        System.out.print(najveciGrad(s) + noviRed);
        System.out.print("Unesite članove niza cijelih brojeva (0 za kraj): ");
        int broj;
        int [] niz = new int [100];
        for (int i = 0; i < 100; i++){
            broj = ulaz.nextInt();
            if (broj == 0) break;
            niz[i] = broj;
        }
        System.out.print("Broj sa najvećom sumom cifara je: "+ najvecaSuma(niz)+ noviRed);
        int brojRedova;
        System.out.print("Unesite broj redova matrice: ");
        brojRedova = ulaz.nextInt();
        double [][] matrica = new double [brojRedova][];
        int brojReda;
        for (int i = 0; i < brojRedova; i++) {
            System.out.print("Unesite broj elemenata u " + (i + 1) + ". redu: ");
            brojReda = ulaz.nextInt();
            matrica[i] = new double[brojReda];
            System.out.print("Unesite elemente: ");
            for (int j = 0; j < brojReda; j++) {
                matrica[i][j] = ulaz.nextDouble();
            }
        }
        double [] najmanjiRed = najmanjaSrednjaVrijednost(matrica);
        System.out.print("Red sa najmanjom srednjom vrijednošću glasi:" + noviRed);
        for (double vrijednost : najmanjiRed) {
            double srednjaVrijednost= (double) vrijednost;
            System.out.print(fmt(srednjaVrijednost) + " ");
        }
    }
}