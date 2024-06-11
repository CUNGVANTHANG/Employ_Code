package controller;

import entity.ParkingLot;
import entity.ParkingLotManager;
import entity.Vehicle;
import entity.XMLHandler;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotController {
    private ParkingLot parkingLot;

    public ParkingLotController(String id) {
        this.parkingLot = XMLHandler.loadFromXML(id);
    }

    public ParkingLotController(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void addVehicle(Vehicle vehicle, String id) {
        parkingLot.addVehicle(vehicle);
        XMLHandler.saveToXML(parkingLot, id);
    }

    public void modifyVehicle(Vehicle oldVehicle, String licensePlate, String id) {
        parkingLot.modifyVehicle(oldVehicle, licensePlate);
        XMLHandler.saveToXML(parkingLot, id);

    }

    public boolean isVehicleCountExceeded(String parkingLotId) {
        int maxVehicleCount = getParkingLotMaxVehicleCount(parkingLotId); // Hàm này cần được thay thế bằng hàm lấy giới hạn số lượng phương tiện từ attribute countVehicle

        int currentVehicleCount = getParkingLotVehicleCount(parkingLotId); // Hàm này cần được thay thế bằng hàm lấy số lượng phương tiện thực tế

        return currentVehicleCount >= maxVehicleCount;
    }

    public int getParkingLotMaxVehicleCount(String parkingLotId) {
        // Lấy giới hạn số lượng phương tiện từ attribute countVehicle của bãi đậu xe với id tương ứng
        ParkingLot parkingLot = XMLHandler.loadFromXML(parkingLotId);
        return parkingLot.getVehicleCount(); // Giả sử giới hạn số lượng phương tiện được lưu trữ trong trường vehicleCount của ParkingLot
    }

    public int getParkingLotVehicleCount(String parkingLotId) {
        // Lấy số lượng phương tiện hiện tại trong bãi đậu xe với id tương ứng
        ParkingLot parkingLot = XMLHandler.loadFromXML(parkingLotId);
        int count = 0;
        for (Vehicle vehicle : parkingLot.getAllVehicles()) {
            if (vehicle.getExitTime() == null || vehicle.getExitTime().isEmpty()) {
                count++;
            }
        }
        return count;
    }


    public void removeVehicle(String licensePlate, String id) {
        Vehicle vehicle = parkingLot.getVehicle(licensePlate);
        if (vehicle != null) {
            parkingLot.removeVehicle(vehicle);
            XMLHandler.saveToXML(parkingLot, id);

        }
    }

    public void updateVehicle(Vehicle vehicle, String id) {
        parkingLot.updateVehicle(vehicle);
        XMLHandler.saveToXML(parkingLot, id);
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

    public boolean isVehicleExited(String licensePlate) {
        List<Vehicle> allVehicles = parkingLot.loadAllVehicles();

        // Lặp qua từng phương tiện trong danh sách tất cả các phương tiện
        for (Vehicle vehicle : allVehicles) {
            // Kiểm tra xem biển số xe của phương tiện có trùng với biển số xe cần kiểm tra không
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                // Nếu phương tiện có cùng biển số xe, kiểm tra xem đã rời bãi đậu xe chưa
                if (vehicle.getExitTime() == null || vehicle.getExitTime().isEmpty()) {
                    return false; // Phương tiện chưa rời bãi đậu xe
                }
            }
        }
        return true; // Phương tiện đã rời bãi đậu xe hoặc không tồn tại trong file XML
    }
}
