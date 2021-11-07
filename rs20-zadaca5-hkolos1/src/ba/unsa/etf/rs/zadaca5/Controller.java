package ba.unsa.etf.rs.zadaca5;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class Controller {
    public TableView<Owner> tableOwners;
    public TableView<Vehicle> tableVehicles;
    public TableColumn<Vehicle, String> colManufacturer;
    public TableColumn colModel;
    public TableColumn colChasisNumber;
    public TableColumn colPlateNumber;
    public TableColumn colId;
    public TableColumn colOwnerId;
    public TableColumn<Owner, String> colNameSurname;
    public TableColumn colJmbg;

    private VehicleDAO dao = null;

    @FXML
    public void initialize() {
        initializeBase();
    }

    private void initializeBase() {
        if (dao != null) dao.close();
        dao = new VehicleDAOBase();
        initializeCommon();
    }
    public void initializeXML() {
        if (dao != null) dao.close();
        dao = new VehicleDAOXML();
        initializeCommon();
    }

    private void initializeCommon() {
        tableOwners.setItems(dao.getOwners());

        colOwnerId.setCellValueFactory(new PropertyValueFactory("id"));
        colNameSurname.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName().concat(" ").concat(data.getValue().getSurname())));
        colJmbg.setCellValueFactory(new PropertyValueFactory("jmbg"));

        tableVehicles.setItems(dao.getVehicles());

        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colManufacturer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getManufacturer().getName()));
        colModel.setCellValueFactory(new PropertyValueFactory("model"));
        colChasisNumber.setCellValueFactory(new PropertyValueFactory("chasisNumber"));
        colPlateNumber.setCellValueFactory(new PropertyValueFactory("plateNumber"));
    }
    public void addOwnerAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/owner.fxml"));
            OwnerController vlasnikController = new OwnerController(dao, null);
            loader.setController(vlasnikController);
            root = loader.load();
            stage.setTitle("Vlasnik");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> tableOwners.setItems(dao.getOwners()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeOwnerAction(ActionEvent actionEvent) {
        Owner owner = tableOwners.getSelectionModel().getSelectedItem();
        if (owner == null) return;

        String namesurname = owner.getName().concat(" ").concat(owner.getSurname());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje vlasnika "+namesurname);
        alert.setContentText("Da li ste sigurni da želite obrisati vlasnika " +namesurname+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                dao.deleteOwner(owner);
            } catch(IllegalArgumentException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška pri brisanju vlasnika!");
                alert.setHeaderText("Nije moguće obrisati vlasnika "+namesurname);
                alert.setContentText(e.getMessage());
                alert.show();
            }
            tableOwners.setItems(dao.getOwners());
        }
    }
    public void switchDb(ActionEvent actionEvent) {
        initializeBase();
    }

    public void switchXml(ActionEvent actionEvent) {
        initializeXML();
    }

    public void addVehicleAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehicle.fxml"));
            VehicleController vehicleController = new VehicleController(dao, null);
            loader.setController(vehicleController);
            root = loader.load();
            stage.setTitle("Vozilo");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

            stage.setOnHiding( event -> tableVehicles.setItems(dao.getVehicles()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicleAction(ActionEvent actionEvent) {
        Vehicle vehicle = tableVehicles.getSelectionModel().getSelectedItem();
        if (vehicle == null) return;

        String namesurname = vehicle.getManufacturer().getName().concat(" ").concat(vehicle.getModel());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja vozila");
        alert.setHeaderText("Brisanje vozila "+namesurname);
        alert.setContentText("Da li ste sigurni da želite obrisati vozilo " +namesurname+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteVehicle(vehicle);
            tableVehicles.setItems(dao.getVehicles());
        }
    }
}
