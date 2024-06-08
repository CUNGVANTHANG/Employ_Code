package entity;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private List<Vehicle> vehicles;

    public ParkingLot() {
        this.vehicles = new ArrayList<>();
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

}

