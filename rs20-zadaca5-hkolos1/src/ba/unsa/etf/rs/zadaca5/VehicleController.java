package ba.unsa.etf.rs.zadaca5;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class VehicleController {
    private VehicleDAO dao;
    private Vehicle vehicle;
    public ComboBox manufacturerCombo;
    public ComboBox ownerCombo;

    public TextField modelField, chasisNumberField, plateNumberField;

    private ObservableList<Owner> owners;
    private ObservableList<Manufacturer> manufacturers;

    public VehicleController() {
        dao = null;
        vehicle = null;
    }

    public VehicleController(VehicleDAO dao, Vehicle vehicle) {
        this.dao = dao;
        this.vehicle = vehicle;
        owners = dao.getOwners();
        manufacturers = dao.getManufacturers();
    }

    @FXML
    public void initialize() {
        ownerCombo.setItems(owners);
        manufacturerCombo.setItems(manufacturers);

        if (vehicle != null) {
            manufacturerCombo.setValue(vehicle.getManufacturer().getName());
            modelField.setText(vehicle.getModel());
            chasisNumberField.setText(vehicle.getChasisNumber());
            plateNumberField.setText(vehicle.getPlateNumber());
            String surnameName = vehicle.getOwner().getSurname().concat(" ").concat(vehicle.getOwner().getName());
            ownerCombo.setValue(surnameName);
        }
    }
}
