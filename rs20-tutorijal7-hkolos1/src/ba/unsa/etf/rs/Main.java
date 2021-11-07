package ba.unsa.etf.rs;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        GeografijaDAO a = new GeografijaDAO();

//        for(int i=0; i<a.gradovi().size(); i++){
//            System.out.println(a.gradovi());
//        }

        System.out.println(a.gradovi());
        //System.out.println("Gradovi su:\n" + ispisiGradove());
        //glavniGrad();
    }

}