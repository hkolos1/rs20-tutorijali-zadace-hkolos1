/*package ba.unsa.etf.rs.zadaca4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

// Testovi za EditController operacija izmjena knjige

@ExtendWith(ApplicationExtension.class)
class EditControllerTestEdit {
    Stage theStage;
    EditController controller;

    private boolean sadrziStil(Node polje, String stil) {
        for (String s : polje.getStyleClass())
            if (s.equals(stil)) return true;
        return false;
    }

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editbook.fxml"));
        Book b = new Book("Testni autor", "Testni naslov", "1234", 100, LocalDate.now());
        controller = new EditController(b);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Library");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();

        theStage = stage;
    }

    @Test
    public void testEditSpinner(FxRobot robot) {
        robot.clickOn("#fldIsbn");
        // Spinner
        robot.press(KeyCode.TAB).release(KeyCode.TAB); // Prelazimo sa ISBN polja na spinner
        robot.press(KeyCode.UP).release(KeyCode.UP); // Biramo 105 strelicom gore

        // Koju vrijednost ima spinner?
        Spinner kbs = robot.lookup("#spinPageCount").queryAs(Spinner.class);
        assertNotNull(kbs);
        Integer i = (Integer)kbs.getValueFactory().getValue();

        robot.clickOn("#btnOk");
        // Da li je forma zatvorena?
        assertFalse(theStage.isShowing());

        Book b = controller.getBook();
        assertEquals("Testni autor", b.getAuthor());
        assertEquals("Testni naslov", b.getTitle());
        assertEquals("1234", b.getIsbn());
        assertEquals(105, b.getPageCount());
        assertEquals(LocalDate.now(), b.getPublishDate());

        // Spinner treba imati vrijednost 105
        assertEquals(new Integer(105), i); //autoboxing ne radi?
    }

    @Test
    public void testEditDateFormat(FxRobot robot) {
        robot.clickOn("#fldIsbn");
        robot.press(KeyCode.TAB).release(KeyCode.TAB); // Prelazimo sa ISBN polja na spinner
        robot.press(KeyCode.TAB).release(KeyCode.TAB); // Prelazimo sa spinnera na datum

        // Selektujemo postojeću vrijednost kako bi ista bila obrisana
        robot.clickOn("#dpPublishDate");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);

        robot.write("13. 02. 1920");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        // Pritisak na tipku enter pokreće internu validaciju koju obavlja DatePicker kontrola

        DatePicker datePicker = robot.lookup("#dpPublishDate").queryAs(DatePicker.class);
        assertTrue(sadrziStil(datePicker, "poljeIspravno"));
        String date = datePicker.getEditor().getText();

        robot.clickOn("#btnOk");
        // Da li je forma zatvorena?
        assertFalse(theStage.isShowing());

        // Da li je datum uspješno promijenjen?
        assertEquals("13. 02. 1920", date);

        // Da li je datum ispravan u knjizi?
        Book b = controller.getBook();
        assertEquals(LocalDate.of(1920, 2, 13), b.getPublishDate());
    }

    @Test
    public void testEditCancel(FxRobot robot) {
        // Da li Cancel dugme radi što treba?
        robot.clickOn("#fldAuthor");
        robot.write("Testni autor");
        robot.clickOn("#fldTitle");
        robot.write("Testni naslov");
        robot.clickOn("#fldIsbn");
        robot.write("1234");

        robot.clickOn("#btnCancel");
        // Da li je forma zatvorena?
        assertFalse(theStage.isShowing());

        // getBook treba vratiti null
        assertNull(controller.getBook());
    }

    @Test
    public void testEditValidateAuthor (FxRobot robot) {
        // Selektujemo postojeću vrijednost kako bi ista bila obrisana
        robot.clickOn("#fldAuthor");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("abc");

        // Uzmi boju
        TextField autor = robot.lookup("#fldAuthor").queryAs(TextField.class);
        assertTrue(sadrziStil(autor, "poljeIspravno"));

        // Autor je sada validan - Brišemo autora
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        // Uzmi sada boju
        assertTrue(sadrziStil(autor, "poljeNijeIspravno"));

        // Sad ćemo ponovo nešto otkucati
        robot.write("a");
        assertTrue(sadrziStil(autor, "poljeIspravno"));
    }

    @Test
    public void testEditValidateTitle(FxRobot robot) {
        // Selektujemo postojeću vrijednost kako bi ista bila obrisana
        robot.clickOn("#fldTitle");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("abc");

        // Uzmi boju
        TextField naslov = robot.lookup("#fldTitle").queryAs(TextField.class);
        assertTrue(sadrziStil(naslov, "poljeIspravno"));

        // Naslov je sada validan - Brišemo naslov
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        // Uzmi sada boju
        assertTrue(sadrziStil(naslov, "poljeNijeIspravno"));

        // Sad ćemo ponovo nešto otkucati
        robot.write("a");
        assertTrue(sadrziStil(naslov, "poljeIspravno"));
    }

    @Test
    public void testEditValidatePublishDate(FxRobot robot) {
        // Selektujemo postojeću vrijednost kako bi ista bila obrisana
        robot.clickOn("#dpPublishDate");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);

        // Datum u budućnosti
        robot.write("19. 10. 2021");
        // Simulacija pritiska na Enter osigurava da će se izvršiti listener i primijeniti validacija, ali
        // dodatno za svaki slučaj klikamo i na polje author
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("#fldAuthor");

        // Uzmi boju
        DatePicker datePicker = robot.lookup("#dpPublishDate").queryAs(DatePicker.class);
        assertTrue(sadrziStil(datePicker, "poljeNijeIspravno"));

        // Probavamo Ok
        robot.clickOn("#btnOk");
        // Da li je forma zatvorena?
        assertTrue(theStage.isShowing());
        assertNull(controller.getBook());

        // Datum u prošlosti
        robot.clickOn("#dpPublishDate");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.write("19. 10. 2018");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        // Uzmi boju
        assertTrue(sadrziStil(datePicker, "poljeIspravno"));

        // Sada Ok radi
        robot.clickOn("#btnOk");
        // Da li je forma zatvorena?
        assertFalse(theStage.isShowing());
        assertNotNull(controller.getBook());

        Book b = controller.getBook();
        assertEquals(LocalDate.of(2018,10,19), b.getPublishDate());
    }


    @Test
    public void testEditInvalid (FxRobot robot) {
        // Brišemo autora
        robot.clickOn("#fldAuthor");
        KeyCode ctrl = KeyCode.CONTROL;
        if (System.getProperty("os.name").equals("Mac OS X"))
            ctrl = KeyCode.COMMAND;
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        robot.clickOn("#btnOk");
        // Da li je forma zatvorena?
        assertTrue(theStage.isShowing());
        assertNull(controller.getBook());

        // Vraćamo autora, brišemo naslov
        robot.write("d");
        robot.clickOn("#fldTitle");
        robot.press(ctrl).press(KeyCode.A).release(KeyCode.A).release(ctrl);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        robot.clickOn("#btnOk");
        // Da li je forma zatvorena?
        assertTrue(theStage.isShowing());
        assertNull(controller.getBook());

        // Vraćamo naslov
        robot.write("c");

        robot.clickOn("#btnOk");
        // Da li je forma zatvorena?
        assertFalse(theStage.isShowing());
        assertNotNull(controller.getBook());

        Book b = controller.getBook();
        assertEquals("d", b.getAuthor());
        assertEquals("c", b.getTitle());
    }

}
*/