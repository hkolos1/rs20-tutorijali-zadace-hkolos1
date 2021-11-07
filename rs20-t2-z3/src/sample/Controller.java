package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class Controller {
    public TextField slovoUnos;
    public TextField velikoSlovo;
    public void akcija(ActionEvent actionEvent) {
        String recenicaA = slovoUnos.getText();
        String najduzaR = "";
        String recenicaB = "";
        for(String rijecA : recenicaA.split(" ")){
            if(rijecA.length()> najduzaR.length() )
            najduzaR = rijecA;
        }
        for(String rijecB : recenicaA.split(" ")){
            if(rijecB.equals(najduzaR))
                rijecB=rijecB.toUpperCase();
            recenicaB +=rijecB + " ";
        }
        velikoSlovo.setText(recenicaB);
    }
}
