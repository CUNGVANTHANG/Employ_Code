package func;

import entity.Vehicle;
import utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehicleManager {
    private List<Vehicle> vehicles;
    private String filePath = "src/main/resources/vehicles.xml";

    public VehicleManager() {
        this.vehicles = new ArrayList<>();
        loadVehiclesFromXML(filePath);
    }

    // Tìm kiếm xe theo biển số
    public List<Vehicle> searchByNumber(String number) {
        String cleanedNumber = number.replace("-", "");
        Pattern pattern = Pattern.compile("^" + cleanedNumber.toLowerCase());
        List<Vehicle> results = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Matcher matcher = pattern.matcher(vehicle.getVehicleNumber().toLowerCase().replace("-", ""));
            if (matcher.find()) {
                results.add(vehicle);
            }
        }
        return results;
    }

    // Phương thức thống kê tổng số lượng xe
    public int statisticTotalVehicles() {
        return vehicles.size();
    }

    private void loadVehiclesFromXML(String filePath) {
        Document doc = XMLUtils.loadXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("vehicle");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String vehicleNumber = element.getElementsByTagName("vehicleNumber").item(0).getTextContent();
            String vehicleInfo = element.getElementsByTagName("vehicleInfo").item(0).getTextContent();
            String examAssigned = element.getElementsByTagName("examAssigned").item(0).getTextContent();

            vehicles.add(new Vehicle(vehicleNumber, vehicleInfo, examAssigned));
        }
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicles();
    }

    public boolean isDuplicateVehicleNumber(String vehicleNumber) {
        String normalizedNewVehicleNumber = normalizeVehicleNumber(vehicleNumber);
        for (Vehicle vehicle : vehicles) {
            if (normalizeVehicleNumber(vehicle.getVehicleNumber()).equals(normalizedNewVehicleNumber)) {
                return true;
            }
        }
        return false;
    }

    private String normalizeVehicleNumber(String vehicleNumber) {
        return vehicleNumber.toLowerCase().replace("-", "");
    }


    public void updateVehicle(int index, Vehicle vehicle) {
        vehicles.set(index, vehicle);
        saveVehicles();
    }

    public void deleteVehicle(int index) {
        vehicles.remove(index);
        saveVehicles();
    }

    private void saveVehicles() {
        try {
            Document doc = XMLUtils.loadXML(filePath);
            Element root = doc.getDocumentElement();

            // Xóa tất cả các phần tử con
            while (root.hasChildNodes()) {
                root.removeChild(root.getFirstChild());
            }

            for (Vehicle vehicle : vehicles) {
                Element vehicleElement = doc.createElement("vehicle");

                Element vehicleNumber = doc.createElement("vehicleNumber");
                vehicleNumber.appendChild(doc.createTextNode(vehicle.getVehicleNumber()));
                vehicleElement.appendChild(vehicleNumber);

                Element vehicleInfo = doc.createElement("vehicleInfo");
                vehicleInfo.appendChild(doc.createTextNode(vehicle.getVehicleInfo()));
                vehicleElement.appendChild(vehicleInfo);

                Element examAssigned = doc.createElement("examAssigned");
                examAssigned.appendChild(doc.createTextNode(vehicle.getExamAssigned()));
                vehicleElement.appendChild(examAssigned);

                root.appendChild(vehicleElement);
            }

            XMLUtils.saveXML(filePath, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
