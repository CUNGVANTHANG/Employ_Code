package entity;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotManager {
    private List<ParkingLot> parkingLotList;

    public ParkingLotManager() {
        this.parkingLotList = new ArrayList<>();
    }

    public void addParkingLot(ParkingLot parkingLot) {
        parkingLotList.add(parkingLot);
    }

    public void removeParkingLot(String id) {
        parkingLotList.removeIf(parkingLot -> parkingLot.getId().equals(id));
    }

    public ParkingLot getParkingLotById(String id) {
        for (ParkingLot parkingLot : parkingLotList) {
            if (parkingLot.getId().equals(id)) {
                return parkingLot;
            }
        }
        return null;
    }

    public List<ParkingLot> getAllParkingLots() {
        return parkingLotList;
    }

    public void addVehicleToParkingLot(String lotId, Vehicle vehicle) {
        ParkingLot parkingLot = getParkingLotById(lotId);
        if (parkingLot != null) {
            parkingLot.addVehicle(vehicle);
        }
    }

    public void removeVehicleFromParkingLot(String lotId, Vehicle vehicle) {
        ParkingLot parkingLot = getParkingLotById(lotId);
        if (parkingLot != null) {
            parkingLot.removeVehicle(vehicle);
        }
    }

    public int getVehicleCountInParkingLot(String lotId) {
        ParkingLot parkingLot = getParkingLotById(lotId);
        if (parkingLot != null) {
            return parkingLot.getAllVehicles().size();
        }
        return 0;
    }
}
