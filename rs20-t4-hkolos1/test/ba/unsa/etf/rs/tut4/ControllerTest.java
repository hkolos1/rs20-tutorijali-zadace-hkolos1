package ba.unsa.etf.rs.tut4;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
public class ControllerTest {
    @Start

    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("/dizajn.fxml"));
        stage.setTitle("Kasa");
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
    @Test
    void testartiklibezduplih_saduplim(FxRobot robot){
        TextArea unos=robot.lookup("#unijeti").queryAs(TextArea.class);
        TextArea ispis=robot.lookup("#bezdupli").queryAs(TextArea.class);
        robot.clickOn(unos);
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("GEL1,Gel za kosu,5.5\n");
        robot.write("GEL1,Gel za kosu,5.5\n");
        robot.clickOn("#dugme");
        assertEquals(" GEL1,Gel za kosu,5.5\n", ispis.getText());
    }
    @Test
    void testtestartiklibezduplih_bezduplim(FxRobot robot){
        TextArea unos=robot.lookup("#unijeti").queryAs(TextArea.class);
        TextArea ispis=robot.lookup("#bezdupli").queryAs(TextArea.class);
        robot.clickOn(unos);
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("JAB,Jabuka,3.5\n");
        robot.write("JAB,Jabuka,5.5\n");
        robot.clickOn("#dugme");
        assertEquals(" JAB,Jabuka,3.5\nJAB,Jabuka,5.5\n", ispis.getText());
    }
    @Test
    void testRacun(FxRobot robot){
        TextArea unos=robot.lookup("#unijeti").queryAs(TextArea.class);
        TextArea ispis=robot.lookup("#bezdupli").queryAs(TextArea.class);
        robot.clickOn(unos);
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("SOK1,Prirodni sok,4.6\n");
        robot.write("ZVA1,Žvakaće gume,1\n");
        robot.clickOn("#dugme");
        robot.clickOn("#check");
        ChoiceBox proizvod =robot.lookup("#choice").queryAs(ChoiceBox.class);
        Platform.runLater(() -> proizvod.show());
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        robot.type(KeyCode.DOWN).type(KeyCode.ENTER);
        robot.clickOn("#K");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.type(KeyCode.DIGIT3);
        robot.clickOn("#DUGME");
        TextArea R=robot.lookup("#ukupniracun").queryAs(TextArea.class);
        assertEquals("  ZVA1         3       3,00\nUKUPNO                3,00",R.getText());
    }
    @Test
    void testRacunViseProizovda(FxRobot robot){
        TextArea unos=robot.lookup("#unijeti").queryAs(TextArea.class);
        robot.clickOn(unos);
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("SOK1,Prirodni sok,4.6\n");
        robot.write("ZVA1,Žvakaće gume,1\n");
        robot.clickOn("#dugme");
        robot.clickOn("#check");
        ChoiceBox proizvod =robot.lookup("#choice").queryAs(ChoiceBox.class);
        Platform.runLater(() -> proizvod.show());
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        robot.type(KeyCode.DOWN).type(KeyCode.ENTER);
        robot.clickOn("#K");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.type(KeyCode.DIGIT3);
        robot.clickOn("#DUGME");
        TextArea R=robot.lookup("#ukupniracun").queryAs(TextArea.class);
        Platform.runLater(() -> proizvod.show());
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        robot.type(KeyCode.UP).type(KeyCode.ENTER);
        robot.clickOn("#K");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.type(KeyCode.DIGIT1).type(KeyCode.DIGIT0);
        robot.clickOn("#DUGME");
        assertEquals("  ZVA1         3       3,00\n  SOK1        10      46,00\nUKUPNO               49,00",R.getText());
    }
}
