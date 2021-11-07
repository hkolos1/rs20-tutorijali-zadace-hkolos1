package sample;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class Controller{
    public Label helloWorld;
    public GridPane root;

    public void setText(ActionEvent actionEvent) {
        helloWorld.getStyleClass().removeAll("sakrivanje");
        helloWorld.getStyleClass().add("prikazi");
        helloWorld.setText("Hello World!");
        root.setStyle("-fx-background-color: lightskyblue");
    }
}

