package ba.unsa.etf.rs.zadaca5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class VehicleDAOBase implements VehicleDAO {

    private static VehicleDAOBase instance;
    private Connection conn;
    private PreparedStatement getOwnersQuery;
    private PreparedStatement getVehicleQuery;
    private PreparedStatement getPlaceQuery, addPlaceQuery;
    private PreparedStatement getManufacturerQuery, addManufacturerQuery;
    private PreparedStatement getOwnerQuery;
    private PreparedStatement getNewIdPlaceQuery, getNewIdManufacturerQuery, getPlacesQuery, getManufacturersQuery;
    private PreparedStatement getNewIdOwnerQuery, addOwnerQuery, changeOwnerQuery, deleteOwnerQuery;
    private PreparedStatement getNewIdVehiclesQuery, addVehicleQuery, changeVehicleQuery, deleteVehicleQuery;
    private PreparedStatement getVehiclesForOwnerQuery;

    VehicleDAOBase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources/db/vehicles.db");
            getOwnersQuery = conn.prepareStatement("SELECT * FROM owner");
            getVehicleQuery = conn.prepareStatement("SELECT * FROM vehicle");
            getPlacesQuery = conn.prepareStatement("SELECT * FROM place ORDER BY name");
            getManufacturersQuery = conn.prepareStatement("SELECT * FROM manufacturer ORDER BY name");

            getPlaceQuery = conn.prepareStatement("SELECT * FROM place WHERE id=?");
            getManufacturerQuery = conn.prepareStatement("SELECT * FROM manufacturer WHERE id=?");
            getOwnerQuery = conn.prepareStatement("SELECT * FROM owner WHERE id=?");

            getNewIdPlaceQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM place");
            addPlaceQuery = conn.prepareStatement("INSERT INTO place VALUES(?,?,?)");
            getNewIdManufacturerQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM manufacturer");
            addManufacturerQuery = conn.prepareStatement("INSERT INTO manufacturer VALUES(?,?)");

            getNewIdOwnerQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM owner");
            addOwnerQuery = conn.prepareStatement("INSERT INTO owner VALUES(?,?,?,?,?,?,?,?,?)");
            changeOwnerQuery = conn.prepareStatement("UPDATE owner SET name=?, surname=?, parent_name=?, date_of_birth=?, place_of_birth=?, living_address=?, living_place=?, jmbg=? WHERE id=?");
            deleteOwnerQuery = conn.prepareStatement("DELETE FROM owner WHERE id=?");

            getNewIdVehiclesQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM vehicle");
            addVehicleQuery = conn.prepareStatement("INSERT INTO vehicle VALUES(?,?,?,?,?,?)");
            changeVehicleQuery = conn.prepareStatement("UPDATE vehicle SET manufacturer=?, model=?, chasis_number=?, plate_number=?, owner=? WHERE id=?");
            deleteVehicleQuery = conn.prepareStatement("DELETE FROM vehicle WHERE id=?");

            getVehiclesForOwnerQuery = conn.prepareStatement("SELECT COUNT(*) FROM vehicle WHERE owner=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static VehicleDAOBase getInstance() {
        if (instance == null) {
            instance = new VehicleDAOBase();
        }
        return instance;
    }

    public static void deleteInstance() {
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Owner getOwnerFromResultSet(ResultSet rs) throws SQLException {
        getPlaceQuery.setInt(1, rs.getInt(6));
        ResultSet rs2 = getPlaceQuery.executeQuery();
        Place born = null;
        while (rs2.next()) {
            born = new Place(rs2.getInt(1), rs2.getString(2), rs2.getString(3));
        }

        getPlaceQuery.setInt(1, rs.getInt(8));
        rs2 = getPlaceQuery.executeQuery();
        Place living = null;
        while (rs2.next()) {
            living = new Place(rs2.getInt(1), rs2.getString(2), rs2.getString(3));
        }

        LocalDate dateOfBirth = rs.getDate(5).toLocalDate();

        Owner owner = new Owner(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                dateOfBirth, born, rs.getString(7), living, rs.getString(9));
        return owner;
    }

    @Override
    public ObservableList<Owner> getOwners() {
        ObservableList<Owner> owners = FXCollections.observableArrayList();
        try {
            ResultSet rs = getOwnersQuery.executeQuery();
            while (rs.next()) {
                Owner owner = getOwnerFromResultSet(rs);
                owners.add(owner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return owners;
    }

    @Override
    public ObservableList<Vehicle> getVehicles() {
        ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();
        try {
            ResultSet rs = getVehicleQuery.executeQuery();
            while (rs.next()) {
                getManufacturerQuery.setInt(1, rs.getInt(2));
                ResultSet rs2 = getManufacturerQuery.executeQuery();
                Manufacturer manufacturer = null;
                while (rs2.next()) {
                    manufacturer = new Manufacturer(rs2.getInt(1), rs2.getString(2));
                }

                getOwnerQuery.setInt(1, rs.getInt(6));
                ResultSet rs3 = getOwnerQuery.executeQuery();
                Owner owner = null;
                if (rs3.next()) owner = getOwnerFromResultSet(rs3);

                Vehicle vehicle = new Vehicle(rs.getInt(1), manufacturer, rs.getString(3),
                        rs.getString(4), rs.getString(5), owner);
                if(!vehicles.contains(vehicle))
                    vehicles.add(vehicle);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public ObservableList<Place> getPlaces() {
        ObservableList<Place> places = FXCollections.observableArrayList();
        try {
            ResultSet rs = getPlacesQuery.executeQuery();
            while (rs.next()) {
                Place place = new Place(rs.getInt(1), rs.getString(2), rs.getString(3));
                places.add(place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return places;
    }

    @Override
    public ObservableList<Manufacturer> getManufacturers() {
        ObservableList<Manufacturer> manufacturers = FXCollections.observableArrayList();
        try {
            ResultSet rs = getManufacturersQuery.executeQuery();
            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(rs.getInt(1), rs.getString(2));
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    private Place addPlaceIfNotExists(Place place) {
        try {
            getPlaceQuery.setInt(1, place.getId());
            ResultSet rs = getPlaceQuery.executeQuery();
            if (!rs.next()) {
                int newId = 1;
                ResultSet rs2 = getNewIdPlaceQuery.executeQuery();
                if (rs2.next()) newId = rs2.getInt(1);
                addPlaceQuery.setInt(1, newId);
                addPlaceQuery.setString(2, place.getName());
                addPlaceQuery.setString(3, place.getPostalNumber());
                addPlaceQuery.executeUpdate();
                place.setId(newId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return place;
    }

    @Override
    public void addOwner(Owner owner) {
        try {
            owner.setPlaceOfBirth( addPlaceIfNotExists(owner.getPlaceOfBirth()) );
            owner.setLivingPlace( addPlaceIfNotExists(owner.getLivingPlace()) );

            ResultSet rs = getNewIdOwnerQuery.executeQuery();
            int id=1;
            if (rs.next()) id = rs.getInt(1);
            owner.setId(id);

            addOwnerQuery.setInt(1, owner.getId());
            addOwnerQuery.setString(2, owner.getName());
            addOwnerQuery.setString(3, owner.getSurname());
            addOwnerQuery.setString(4, owner.getParentName());
            addOwnerQuery.setDate(5, Date.valueOf(owner.getDateOfBirth()));
            addOwnerQuery.setInt(6, owner.getPlaceOfBirth().getId());
            addOwnerQuery.setString(7, owner.getLivingAddress());
            addOwnerQuery.setInt(8, owner.getLivingPlace().getId());
            addOwnerQuery.setString(9, owner.getJmbg());
            addOwnerQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeOwner(Owner owner) {
        try {
            owner.setPlaceOfBirth( addPlaceIfNotExists(owner.getPlaceOfBirth()) );
            owner.setLivingPlace( addPlaceIfNotExists(owner.getLivingPlace()) );
            changeOwnerQuery.setInt(9, owner.getId());
            changeOwnerQuery.setString(1, owner.getName());
            changeOwnerQuery.setString(2, owner.getSurname());
            changeOwnerQuery.setString(3, owner.getParentName());
            changeOwnerQuery.setDate(4, Date.valueOf(owner.getDateOfBirth()));
            changeOwnerQuery.setInt(5, owner.getPlaceOfBirth().getId());
            changeOwnerQuery.setString(6, owner.getLivingAddress());
            changeOwnerQuery.setInt(7, owner.getLivingPlace().getId());
            changeOwnerQuery.setString(8, owner.getJmbg());
            changeOwnerQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOwner(Owner owner) {
        try {
            getVehiclesForOwnerQuery.setInt(1, owner.getId());
            ResultSet rs = getVehiclesForOwnerQuery.executeQuery();
            if (rs.next())
                if (rs.getInt(1) > 0)
                    throw new IllegalArgumentException("Owner has vehicles!");

            deleteOwnerQuery.setInt(1, owner.getId());
            deleteOwnerQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Manufacturer addManufacturerIfNotExists(Manufacturer manufacturer) {
        try {
            getManufacturerQuery.setInt(1, manufacturer.getId());
            ResultSet rs = getManufacturerQuery.executeQuery();
            if (!rs.next()) {
                int newId = 1;
                ResultSet rs2 = getNewIdManufacturerQuery.executeQuery();
                if (rs2.next()) newId = rs2.getInt(1);

                addManufacturerQuery.setInt(1, newId);
                addManufacturerQuery.setString(2, manufacturer.getName());
                addManufacturerQuery.executeUpdate();
                manufacturer.setId(newId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturer;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        try {
            vehicle.setManufacturer( addManufacturerIfNotExists(vehicle.getManufacturer()) );

            getOwnerQuery.setInt(1, vehicle.getOwner().getId());
            ResultSet rs = getOwnerQuery.executeQuery();
            if (!rs.next())
                throw new IllegalArgumentException("Unknown owner with id "+vehicle.getOwner().getId());

            rs = getNewIdVehiclesQuery.executeQuery();
            int id=1;
            if (rs.next()) id = rs.getInt(1);
            vehicle.setId(id);

            addVehicleQuery.setInt(1, vehicle.getId());
            addVehicleQuery.setInt(2, vehicle.getManufacturer().getId());
            addVehicleQuery.setString(3, vehicle.getModel());
            addVehicleQuery.setString(4, vehicle.getChasisNumber());
            addVehicleQuery.setString(5, vehicle.getPlateNumber());
            addVehicleQuery.setInt(6, vehicle.getOwner().getId());
            addVehicleQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeVehicle(Vehicle vehicle) {
        try {
            vehicle.setManufacturer( addManufacturerIfNotExists(vehicle.getManufacturer()) );

            getOwnerQuery.setInt(1, vehicle.getOwner().getId());
            ResultSet rs = getOwnerQuery.executeQuery();
            if (!rs.next())
                throw new IllegalArgumentException("Unknown owner with id "+vehicle.getOwner().getId());

            changeVehicleQuery.setInt(6, vehicle.getId());
            changeVehicleQuery.setInt(1, vehicle.getManufacturer().getId());
            changeVehicleQuery.setString(2, vehicle.getModel());
            changeVehicleQuery.setString(3, vehicle.getChasisNumber());
            changeVehicleQuery.setString(4, vehicle.getPlateNumber());
            changeVehicleQuery.setInt(5, vehicle.getOwner().getId());
            changeVehicleQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        try {
            deleteVehicleQuery.setInt(1, vehicle.getId());
            deleteVehicleQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}