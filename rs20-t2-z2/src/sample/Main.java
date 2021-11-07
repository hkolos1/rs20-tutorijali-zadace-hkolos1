package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static int sumaCifara(int n){
        int broj = n;
        if(broj<0) broj=-broj;
        int suma=0;
        while(broj!=0){
            int cifra = broj % 10;
            suma = suma + cifra ;
            broj = broj / 10;
        }
        return suma;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("sumaCifara");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
