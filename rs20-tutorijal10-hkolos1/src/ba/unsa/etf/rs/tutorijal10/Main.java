package ba.unsa.etf.rs.tutorijal10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/igrica.fxml"));
        stage.setTitle("Tutorijal10");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
    }
    public static void main(String[] args) throws IOException {
        boolean pogodak = false;
        while (!pogodak) {
            System.out.println("Unesite broj: ");
            Scanner unos = new Scanner(System.in);
            int broj = unos.nextInt();
            String brojJson = "{\"broj\" : \"" + broj + "\"}";
            URL url = new URL("https://web-tutorijal-10.herokuapp.com/pokusaj?username=hkolos1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            OutputStream izlaz = con.getOutputStream();
            byte[] input = brojJson.getBytes("utf-8");
            izlaz.write(input, 0, input.length);

            BufferedReader ulaz = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String json = "", line = null;
            while ((line = ulaz.readLine()) != null)
                json = json + line;

            JSONObject rezultat = new JSONObject(json);

            System.out.println(rezultat.get("message"));
            if (rezultat.get("message").equals("Pogodak! Novi broj je generisan.")) ;
            pogodak = true;
        }
    }
}