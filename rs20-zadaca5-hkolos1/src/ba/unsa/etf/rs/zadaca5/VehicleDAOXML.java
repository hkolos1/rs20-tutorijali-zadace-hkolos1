package ba.unsa.etf.rs.zadaca5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class VehicleDAOXML implements VehicleDAO {
    private ArrayList<Owner> owners = new ArrayList<>();
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<Place> places = new ArrayList<>();
    private ArrayList<Manufacturer> manufacturers = new ArrayList<>();

    VehicleDAOXML() {
        readFiles();
    }

    private void readFiles() {
        owners.clear();
        vehicles.clear();
        try {
            XMLDecoder decoder = new XMLDecoder(new FileInputStream("owners.xml"));
            owners = (ArrayList<Owner>)decoder.readObject();
            decoder.close();
            decoder = new XMLDecoder(new FileInputStream("vehicles.xml"));
            vehicles = (ArrayList<Vehicle>)decoder.readObject();
            decoder.close();
            decoder = new XMLDecoder(new FileInputStream("places.xml"));
            places = (ArrayList<Place>)decoder.readObject();
            decoder.close();
            decoder = new XMLDecoder(new FileInputStream("manufacturers.xml"));
            manufacturers = (ArrayList<Manufacturer>)decoder.readObject();
            decoder.close();

            places.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
            manufacturers.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeFiles() {
        try {
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream("owners.xml"));
            encoder.writeObject(owners);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("vehicles.xml"));
            encoder.writeObject(vehicles);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("places.xml"));
            encoder.writeObject(places);
            encoder.close();
            encoder = new XMLEncoder(new FileOutputStream("manufacturers.xml"));
            encoder.writeObject(manufacturers);
            encoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Owner> getOwners() {
        return FXCollections.observableArrayList(owners);
    }

    @Override
    public ObservableList<Vehicle> getVehicles() {
        return FXCollections.observableArrayList(vehicles);
    }

    @Override
    public ObservableList<Place> getPlaces() {
        return FXCollections.observableArrayList(places);
    }

    @Override
    public ObservableList<Manufacturer> getManufacturers() {
        return FXCollections.observableArrayList(manufacturers);
    }

    @Override
    public void addOwner(Owner owner) {
        owner.setPlaceOfBirth( addPlaceIfNotExists(owner.getPlaceOfBirth()) );
        owner.setLivingPlace( addPlaceIfNotExists(owner.getLivingPlace()) );

        int maxId = 0;
        for(Owner v : owners)
            if (v.getId() > maxId) maxId = v.getId();
        owner.setId(maxId+1);

        owners.add(owner);
        writeFiles();
    }

    private Place addPlaceIfNotExists(Place place) {
        int maxId = 0;
        for (int i=0; i<places.size(); i++)
            if (places.get(i).getId() == place.getId()) {
                places.set(i, place);
                return place;
            } else if (places.get(i).getId() > maxId)
                maxId = places.get(i).getId();
        place.setId(maxId + 1);
        places.add(place);
        places.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
        return place;
    }

    @Override
    public void changeOwner(Owner owner) {
        owner.setPlaceOfBirth( addPlaceIfNotExists(owner.getPlaceOfBirth()) );
        owner.setLivingPlace( addPlaceIfNotExists(owner.getLivingPlace()) );
        for (int i=0; i<owners.size(); i++)
            if (owners.get(i).getId() == owner.getId())
                owners.set(i, owner);
        writeFiles();
    }

    @Override
    public void deleteOwner(Owner owner) {
        for (Vehicle v : vehicles)
            if (v.getOwner().getId() == owner.getId())
                throw new IllegalArgumentException("Owner has vehicles!");

        for (int i=0; i<owners.size(); i++)
            if (owners.get(i).getId() == owner.getId())
                owners.remove(i);
        writeFiles();
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicle.setManufacturer(addManufacturerIfNotExists(vehicle.getManufacturer()) );

        boolean check = false;
        for(Owner v: owners)
            if (v.getId() == vehicle.getOwner().getId())
                check = true;
        if (!check)
            throw new IllegalArgumentException("Unknown owner with id "+vehicle.getOwner().getId());

        int maxId = 0;
        for(Vehicle v : vehicles)
            if (v.getId() > maxId) maxId = v.getId();
        vehicle.setId(maxId+1);

        vehicles.add(vehicle);
        writeFiles();
    }

    private Manufacturer addManufacturerIfNotExists(Manufacturer manufacturer) {
        int maxId = 0;
        for (int i=0; i<manufacturers.size(); i++)
            if (manufacturers.get(i).getId() == manufacturer.getId()) {
                manufacturers.set(i, manufacturer);
                return manufacturer;
            } else if (manufacturers.get(i).getId() > maxId)
                maxId = manufacturers.get(i).getId();
        manufacturer.setId(maxId+1);
        manufacturers.add(manufacturer);
        manufacturers.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
        return manufacturer;
    }

    @Override
    public void changeVehicle(Vehicle vehicle) {
        vehicle.setManufacturer( addManufacturerIfNotExists(vehicle.getManufacturer()) );

        boolean check = false;
        for(Owner v: owners)
            if (v.getId() == vehicle.getOwner().getId())
                check = true;
        if (!check)
            throw new IllegalArgumentException("Unknown owner with id "+vehicle.getOwner().getId());

        for (int i=0; i<vehicles.size(); i++)
            if (vehicles.get(i).getId() == vehicle.getId())
                vehicles.set(i, vehicle);
        writeFiles();
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        for (int i=0; i<vehicles.size(); i++)
            if (vehicles.get(i).getId() == vehicle.getId())
                vehicles.remove(i);
        writeFiles();
    }

    @Override
    public void close() {

    }
}
