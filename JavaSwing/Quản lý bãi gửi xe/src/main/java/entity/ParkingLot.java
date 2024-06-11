package entity;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private List<Vehicle> vehicles;
    private String id;
    private String name;
    private String location;
    private int vehicleCount;

    public ParkingLot(String id, String name, String location, int vehicleCount) {
        this.vehicles = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.location = location;
        this.vehicleCount = vehicleCount;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void modifyVehicle(Vehicle oldVehicle, String licensePlate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(oldVehicle.getLicensePlate()) && vehicle.getEntryTime().equals(oldVehicle.getEntryTime())) {
                vehicle.setLicensePlate(licensePlate);
            }
        }
    }
    public Vehicle getVehicle(String licensePlate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                return vehicle;
            }
        }
        return null;
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }

    public void updateVehicle(Vehicle updatedVehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            if (vehicle.getLicensePlate().equals(updatedVehicle.getLicensePlate()) && vehicle.getEntryTime().equals(updatedVehicle.getEntryTime())) {
                vehicles.set(i, updatedVehicle);
                return;
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public List<Vehicle> loadAllVehicles() {
        return XMLHandler.loadAllVehicles();
    }
}

