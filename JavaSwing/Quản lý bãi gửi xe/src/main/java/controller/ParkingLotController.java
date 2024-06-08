package controller;

import entity.ParkingLot;
import entity.Vehicle;
import entity.XMLHandler;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotController {
    private ParkingLot parkingLot;

    public ParkingLotController() {
        this.parkingLot = XMLHandler.loadFromXML();
    }

    public void addVehicle(Vehicle vehicle) {
        parkingLot.addVehicle(vehicle);
        XMLHandler.saveToXML(parkingLot);
    }

    public void modifyVehicle(Vehicle oldVehicle, String licensePlate) {
        parkingLot.modifyVehicle(oldVehicle, licensePlate);
        XMLHandler.saveToXML(parkingLot);
    }

    public void removeVehicle(String licensePlate) {
        Vehicle vehicle = parkingLot.getVehicle(licensePlate);
        if (vehicle != null) {
            parkingLot.removeVehicle(vehicle);
            XMLHandler.saveToXML(parkingLot);
        }
    }

    public void updateVehicle(Vehicle vehicle) {
        parkingLot.updateVehicle(vehicle);
        XMLHandler.saveToXML(parkingLot);
    }

    public Vehicle getVehicle(String licensePlate) {
        return parkingLot.getVehicle(licensePlate);
    }

    public Vehicle getVehicle(String licensePlate, String entryTime) {
        for (Vehicle vehicle : parkingLot.getAllVehicles()) {
            if (vehicle.getLicensePlate().equals(licensePlate) && vehicle.getEntryTime().equals(entryTime)) {
                return vehicle;
            }
        }
        return null;
    }

    public List<Vehicle> getVehiclesWithoutExitTime() {
        List<Vehicle> vehiclesWithoutExitTime = new ArrayList<>();
        for (Vehicle vehicle : parkingLot.getAllVehicles()) {
            if (vehicle.getExitTime() == null || vehicle.getExitTime().isEmpty()) {
                vehiclesWithoutExitTime.add(vehicle);
            }
        }
        return vehiclesWithoutExitTime;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public List<Vehicle> getAllVehicles() {
        return parkingLot.getAllVehicles();
    }

    public List<Vehicle> getAllVehiclesByLicensePlate(String licensePlate) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : parkingLot.getAllVehicles()) {
            if (vehicle.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                result.add(vehicle);
            }
        }
        return result;
    }

    // Kiểm tra xem phương tiện với biển số xe đã có thời gian ra hay chưa
    public boolean isVehicleExited(String licensePlate) {
        List<Vehicle> vehicles = parkingLot.getAllVehicles();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                if (vehicle.getExitTime().isEmpty()) {
                    return false; // Phương tiện chưa rời bãi đậu xe
                }
            }
        }
        return true; // Phương tiện đã rời bãi đậu xe
    }
}
