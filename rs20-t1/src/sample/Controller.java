package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Controller {
    public Label helloWorld;
    public GridPane root;

    public void setText(ActionEvent actionEvent) {
        helloWorld.getStyleClass().removeAll("sakrij");
        helloWorld.getStyleClass().add("prikazi");
        helloWorld.setText("Hello World!");
        root.setStyle("-fx-background-color: lightpink");
    }
}