package controller;

import entity.ParkingLot;
import entity.ParkingLotManager;
import entity.Vehicle;
import entity.XMLHandler;

import java.util.List;

public class ParkingLotManagerController {
    private ParkingLotManager manager;

    public ParkingLotManagerController() {
        this.manager = XMLHandler.loadManagerFromXML();
        if (manager == null) {
            manager = new ParkingLotManager();
        }
    }

    public boolean isParkingLotIdExists(String id) {
        for (ParkingLot parkingLot : getAllParkingLots()) {
            if (parkingLot.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    public void addParkingLot(ParkingLot parkingLot) {
        manager.addParkingLot(parkingLot);
        XMLHandler.saveManagerToXML(manager);
    }

    public void removeParkingLot(String id) {
        manager.removeParkingLot(id);
        XMLHandler.saveManagerToXML(manager);
    }

    public ParkingLot getParkingLotById(String id) {
        return manager.getParkingLotById(id);
    }

    public List<ParkingLot> getAllParkingLots() {
        return manager.getAllParkingLots();
    }

    public void addVehicleToParkingLot(String lotId, Vehicle vehicle) {
        manager.addVehicleToParkingLot(lotId, vehicle);
        XMLHandler.saveManagerToXML(manager);
    }

    public void removeVehicleFromParkingLot(String lotId, Vehicle vehicle) {
        manager.removeVehicleFromParkingLot(lotId, vehicle);
        XMLHandler.saveManagerToXML(manager);
    }

    public void updateParkingLot(ParkingLot parkingLot) {
        removeParkingLot(parkingLot.getId());
        addParkingLot(parkingLot);
    }
}
