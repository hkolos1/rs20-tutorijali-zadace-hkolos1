package ba.unsa.etf.rs;
import java.sql.*;
import java.util.ArrayList;

public class GeografijaDAO {
    private Connection conn;
    private PreparedStatement upitSortGrad, traziGradUpit;
    private static GeografijaDAO instance = null;


    GeografijaDAO() throws SQLException {
        String url = "jdbc:sqlite:baza.db";
        conn = DriverManager.getConnection(url);
        pripremiUpite();

        try {
            Statement stm;
            stm = conn.createStatement();
            stm.execute("SELECT * FROM Grad");
            stm.execute("SELECT * FROM Drzava");

        } catch (SQLException e) {
            createTables();
            napuni();
        }

    }
    public static GeografijaDAO getInstance(){
        if(instance == null) {
            try {
                instance = new GeografijaDAO();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return instance;
    }

    public static void removeInstance() {
        if (instance == null)
            return;
        try {
            instance.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        instance = null;
    }


    public void pripremiUpite(){
        try {
            upitSortGrad = conn.prepareStatement("SELECT id, broj_stanovnika, naziv, drzava FROM grad ORDER BY broj_stanovnika DESC");
            traziGradUpit = conn.prepareStatement("SELECT drzava FROM grad");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public ArrayList<Grad> gradovi() {
        ArrayList<Grad> grad = new ArrayList<>();
        try {
            ResultSet rs = upitSortGrad.executeQuery();
            while(rs.next()){
                Grad temp = new Grad();
                temp.setId(rs.getInt(1));
                temp.setNaziv(rs.getString(2));
                temp.setBrojStanovnika(rs.getInt(3));

                grad.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grad;
    }

    private void createTables() {
        String [] upit = new String[]{
                "drop table if exists grad;",
                "drop table if exists drzava;",
                "create table grad(id integer primary key autoincrement, naziv varchar(20)" +
                        ", broj_stanovnika integer);",
                "create table drzava(id integer primary key autoincrement, naziv varchar(50));"
        };

        String [] update = new String [] {
                "alter table grad add drzava integer references drzava(id);",
                "alter table drzava add glavni_grad integer references grad(id);"
        };

        Statement smt = null;
        try{
            smt=conn.createStatement();
            for(String query : upit ) smt.execute(query);
            for(String upd : update) smt.executeUpdate(upd);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void napuni() {
        try{
            Statement smt = conn.createStatement();
            smt.execute("insert into drzava(naziv,glavni_grad) values ('Velika Britanija',1);");
            smt.execute("insert into drzava(naziv,glavni_grad) values ('Austrija',2);");
            smt.execute("insert into drzava(naziv,glavni_grad) values ('Francuska',3);");
            smt.execute("insert into grad(naziv,broj_stanovnika,drzava) values ('London',8825000,1);");
            smt.execute("insert into grad(naziv,broj_stanovnika,drzava) values ('Beč',1899055,2);");
            smt.execute("insert into grad(naziv,broj_stanovnika,drzava) values ('Graz',280200,2);");
            smt.execute("insert into grad(naziv,broj_stanovnika,drzava) values ('Pariz',2206488,3);");
            smt.execute("insert into grad(naziv,broj_stanovnika,drzava) values ('Manchester',545500,1);");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    //--------------------

    /*Grad glavniGrad(String drzava){
        // Grad glavniGrad(String drzava) - vraća null ako država ne postoji
        try {
            ResultSet rs = traziGradUpit.executeQuery();
            while(rs.next()){

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }*/

    void obrisiDrzavu(String drzava){
        // void obrisiDrzavu(String drzava) - briše i sve gradove u toj držav
    }

    void dodajGrad(Grad grad){
        //void dodajGrad(Grad grad) i void dodajDrzavu(Drzava drzava)
    }

    void izmijeniGrad(Grad grad){
        //void izmijeniGrad(Grad grad) - ažurira slog u bazi za dati grad
    }

    /*Drzava nadjiDrzavu(String drzava){
        //Drzava nadjiDrzavu(String drzava) - vraća null ako država ne postoji

    }*/



    //--------------------


}
