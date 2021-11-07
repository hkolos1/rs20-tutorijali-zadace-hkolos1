package ba.unsa.etf.rs.tutorijal9;


import com.github.tsijercic1.xml.common.InvalidXMLException;
import com.github.tsijercic1.xml.common.Node;
import com.github.tsijercic1.xml.parser.XMLParser;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorijal {

    public static void main(String[] args) {
        ArrayList<Grad> gradovi;
        gradovi=ucitajGradove();
        for(int i=0; i<gradovi.size(); i++){
            System.out.println(gradovi.get(i).getNaziv());
        }
        for(Grad g: gradovi){
            System.out.println(g);
        }
        Drzava bih = new Drzava("Bosna i Hercegovina", 3500000, 51129, "km2", gradovi.get(0));

        ArrayList<Drzava> drzave = new ArrayList<>();
        drzave.add(bih);

        UN un = new UN();
        un.setDrzave(drzave);

        //UN un = ucitajXml(null);
        //System.out.println(un);
    }
    public static ArrayList<Grad> ucitajGradove() {
        ArrayList<Grad> gradovi = new ArrayList<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new FileReader("mjerenja.txt"));
            while (scanner.hasNextLine()) {
                String jedanRedGrad = scanner.nextLine();
                String[] nizPodatakaOGradu = jedanRedGrad.split(",");
                Grad grad = new Grad();
                grad.setNaziv(nizPodatakaOGradu[0]);
                int brojTemperatura = nizPodatakaOGradu.length - 1;
                if (brojTemperatura > 1000) brojTemperatura = 1000;

                double[] temperature = new double[1000];
                for (int i = 0; i < brojTemperatura; i++) {
                    temperature[i] = Double.parseDouble(nizPodatakaOGradu[i + 1]);
                }
                grad.setTemperature(temperature);
                gradovi.add(grad);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka nije pronađena!");
        }
        return gradovi;
    }
    public static UN ucitajXml(ArrayList<Grad>gradovi){
        UN un = new UN();
        try{
            XMLParser xmlParser = new XMLParser("drzave.xml");
            Node root = xmlParser.getDocumentRootNode();
            for(Node node:root.getChildNodes()){
                Drzava drzava = new Drzava();

                drzava.setNaziv(node.getChildNode("naziv").getContent());
                drzava.setPovrsina(Double.parseDouble(node.getChildNode("povrsina").getContent()));
                drzava.setJedinica(node.getChildNode("povrsina").getAttributes().get("jedinica"));
                drzava.setBrojStanovnika(Integer.parseInt(node.getAttributes().get("stanovnika")));

                Node grad = node.getChildNode("glavnigrad");
                Grad glavniGrad = new Grad();
                glavniGrad.setBrojStanovnika(Integer.parseInt(grad.getAttributes().get("stanovnika")));
                glavniGrad.setNaziv(grad.getChildNode("naziv").getContent());

                for(Grad g: gradovi){
                    if(g.getNaziv().equals(glavniGrad.getNaziv())){
                        glavniGrad.setTemperature(g.getTemperature());
                    }
                }
                drzava.setGlavniGrad(glavniGrad);
                un.getDrzave().add(drzava);
            }
        } catch(InvalidXMLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return un;
    }
    public static void zapisiXml(UN un) throws FileNotFoundException{
        XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("un.xml"));
        izlaz.writeObject(un);
        izlaz.close();
    }
}