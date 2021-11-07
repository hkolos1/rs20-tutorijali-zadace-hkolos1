package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import static sample.Main.sumaCifara;

public class Controller {
    public TextField brojN;
    public TextField rezultatN;


    public void btnIspisi(ActionEvent actionEvent) {
        int n= Integer.parseInt(brojN.getText());
        String result="";
        for(int i=1; i<n; i++){
            if(i%sumaCifara(i)==0)
                result=result + i + " ";
        }
        rezultatN.setText(result);
    }
}

