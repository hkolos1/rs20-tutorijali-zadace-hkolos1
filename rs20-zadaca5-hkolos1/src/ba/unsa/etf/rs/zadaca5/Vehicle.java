package ba.unsa.etf.rs.zadaca5;

import java.util.Objects;

public class Vehicle {

    private int id;
    private Manufacturer manufacturer;
    private String model, chasisNumber, plateNumber;
    private Owner owner;

    public Vehicle(int id, Manufacturer manufacturer, String model, String chasisNumber, String plateNumber, Owner owner) {

        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.chasisNumber = chasisNumber;
        this.plateNumber = plateNumber;
        this.owner = owner;
    }

    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getChasisNumber() {
        return chasisNumber;
    }

    public void setChasisNumber(String chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(manufacturer, vehicle.manufacturer) && Objects.equals(model, vehicle.model) && Objects.equals(chasisNumber, vehicle.chasisNumber) && Objects.equals(plateNumber, vehicle.plateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturer, model, chasisNumber, plateNumber);
    }
}