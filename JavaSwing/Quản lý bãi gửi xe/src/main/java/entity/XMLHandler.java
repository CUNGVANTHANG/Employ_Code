package entity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLHandler {
    private static final String FILE_NAME = "src/main/resources/parkinglot.xml";

    public static void saveToXML(ParkingLot parkingLot, String id) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            File file = new File(FILE_NAME);
            if (!file.exists()) return;


            Document docRead = dBuilder.parse(file);
            docRead.getDocumentElement().normalize();

            Element rootElement = doc.createElement("ParkingLotManager");
            doc.appendChild(rootElement);

            NodeList parkingLotList = docRead.getElementsByTagName("ParkingLot");

            for (int i = 0; i < parkingLotList.getLength(); i++) {
                Element currentParkingLot = (Element) parkingLotList.item(i);
                String currentId = currentParkingLot.getAttribute("id");
                if (currentId.equals(id)) {
                    Element parkingLotElement = createParkingLotElement(doc, parkingLot);
                    rootElement.appendChild(parkingLotElement);
                } else {
                    rootElement.appendChild(doc.importNode(currentParkingLot, true));
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_NAME));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element createParkingLotElement(Document doc, ParkingLot parkingLot) {
        Element parkingLotElement = doc.createElement("ParkingLot");
        parkingLotElement.setAttribute("id", parkingLot.getId());
        parkingLotElement.setAttribute("name", parkingLot.getName());
        parkingLotElement.setAttribute("location", parkingLot.getLocation());
        parkingLotElement.setAttribute("vehicleCount", String.valueOf(parkingLot.getVehicleCount()));

        for (Vehicle vehicle : parkingLot.getAllVehicles()) {
            Element vehicleElement = doc.createElement("Vehicle");

            Element licensePlate = doc.createElement("LicensePlate");
            licensePlate.appendChild(doc.createTextNode(vehicle.getLicensePlate()));
            vehicleElement.appendChild(licensePlate);

            Element entryTime = doc.createElement("EntryTime");
            entryTime.appendChild(doc.createTextNode(vehicle.getEntryTime()));
            vehicleElement.appendChild(entryTime);

            Element exitTime = doc.createElement("ExitTime");
            exitTime.appendChild(doc.createTextNode(vehicle.getExitTime()));
            vehicleElement.appendChild(exitTime);

            Element parkingFee = doc.createElement("ParkingFee");
            parkingFee.appendChild(doc.createTextNode(String.valueOf(vehicle.getParkingFee())));
            vehicleElement.appendChild(parkingFee);

            parkingLotElement.appendChild(vehicleElement);
        }

        return parkingLotElement;
    }

    public static ParkingLot loadFromXML(String id) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return null;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList parkingLotList = doc.getElementsByTagName("ParkingLot");
            for (int i = 0; i < parkingLotList.getLength(); i++) {
                Node parkingLotNode = parkingLotList.item(i);
                if (parkingLotNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element parkingLotElement = (Element) parkingLotNode;

                    if (parkingLotElement.getAttribute("id").equals(id)) {
                        String name = parkingLotElement.getAttribute("name");
                        String location = parkingLotElement.getAttribute("location");
                        int vehicleCount = Integer.parseInt(parkingLotElement.getAttribute("vehicleCount"));

                        ParkingLot parkingLot = new ParkingLot(id, name, location, vehicleCount);

                        NodeList vehicleList = parkingLotElement.getElementsByTagName("Vehicle");
                        for (int j = 0; j < vehicleList.getLength(); j++) {
                            Node vehicleNode = vehicleList.item(j);
                            if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element vehicleElement = (Element) vehicleNode;

                                String licensePlate = vehicleElement.getElementsByTagName("LicensePlate").item(0).getTextContent();
                                String entryTime = vehicleElement.getElementsByTagName("EntryTime").item(0).getTextContent();
                                String exitTime = vehicleElement.getElementsByTagName("ExitTime").item(0).getTextContent();
                                double parkingFee = Double.parseDouble(vehicleElement.getElementsByTagName("ParkingFee").item(0).getTextContent());

                                Vehicle vehicle = new Vehicle(licensePlate, entryTime);
                                vehicle.setExitTime(exitTime);
                                vehicle.setParkingFee(parkingFee);

                                parkingLot.addVehicle(vehicle);
                            }
                        }
                        return parkingLot;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveManagerToXML(ParkingLotManager manager) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("ParkingLotManager");
            doc.appendChild(rootElement);

            List<ParkingLot> parkingLots = manager.getAllParkingLots();
            for (ParkingLot parkingLot : parkingLots) {
                Element parkingLotElement = doc.createElement("ParkingLot");
                parkingLotElement.setAttribute("id", parkingLot.getId());
                parkingLotElement.setAttribute("name", parkingLot.getName());
                parkingLotElement.setAttribute("location", parkingLot.getLocation());
                parkingLotElement.setAttribute("vehicleCount", String.valueOf(parkingLot.getVehicleCount()));

                List<Vehicle> vehicles = parkingLot.getAllVehicles();
                for (Vehicle vehicle : vehicles) {
                    Element vehicleElement = doc.createElement("Vehicle");

                    Element licensePlate = doc.createElement("LicensePlate");
                    licensePlate.appendChild(doc.createTextNode(vehicle.getLicensePlate()));
                    vehicleElement.appendChild(licensePlate);

                    Element entryTime = doc.createElement("EntryTime");
                    entryTime.appendChild(doc.createTextNode(vehicle.getEntryTime()));
                    vehicleElement.appendChild(entryTime);

                    Element exitTime = doc.createElement("ExitTime");
                    exitTime.appendChild(doc.createTextNode(vehicle.getExitTime()));
                    vehicleElement.appendChild(exitTime);

                    Element parkingFee = doc.createElement("ParkingFee");
                    parkingFee.appendChild(doc.createTextNode(String.valueOf(vehicle.getParkingFee())));
                    vehicleElement.appendChild(parkingFee);

                    parkingLotElement.appendChild(vehicleElement);
                }

                rootElement.appendChild(parkingLotElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_NAME));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ParkingLotManager loadManagerFromXML() {
        ParkingLotManager manager = new ParkingLotManager();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return manager;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList parkingLotList = doc.getElementsByTagName("ParkingLot");
            for (int i = 0; i < parkingLotList.getLength(); i++) {
                Node parkingLotNode = parkingLotList.item(i);
                if (parkingLotNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element parkingLotElement = (Element) parkingLotNode;

                    String id = parkingLotElement.getAttribute("id");
                    String name = parkingLotElement.getAttribute("name");
                    String location = parkingLotElement.getAttribute("location");
                    int vehicleCount = Integer.parseInt(parkingLotElement.getAttribute("vehicleCount"));

                    ParkingLot parkingLot = new ParkingLot(id, name, location, vehicleCount);

                    NodeList vehicleList = parkingLotElement.getElementsByTagName("Vehicle");
                    for (int j = 0; j < vehicleList.getLength(); j++) {
                        Node vehicleNode = vehicleList.item(j);
                        if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element vehicleElement = (Element) vehicleNode;

                            String licensePlate = vehicleElement.getElementsByTagName("LicensePlate").item(0).getTextContent();
                            String entryTime = vehicleElement.getElementsByTagName("EntryTime").item(0).getTextContent();
                            String exitTime = vehicleElement.getElementsByTagName("ExitTime").item(0).getTextContent();
                            double parkingFee = Double.parseDouble(vehicleElement.getElementsByTagName("ParkingFee").item(0).getTextContent());

                            Vehicle vehicle = new Vehicle(licensePlate, entryTime);
                            vehicle.setExitTime(exitTime);
                            vehicle.setParkingFee(parkingFee);

                            parkingLot.addVehicle(vehicle);
                        }
                    }

                    manager.addParkingLot(parkingLot);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manager;
    }

    public static List<Vehicle> loadAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(FILE_NAME));
            doc.getDocumentElement().normalize();

            NodeList parkingLotNodes = doc.getElementsByTagName("ParkingLot");

            for (int i = 0; i < parkingLotNodes.getLength(); i++) {
                Node parkingLotNode = parkingLotNodes.item(i);
                if (parkingLotNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element parkingLotElement = (Element) parkingLotNode;

                    NodeList vehicleNodes = parkingLotElement.getElementsByTagName("Vehicle");

                    for (int j = 0; j < vehicleNodes.getLength(); j++) {
                        Node vehicleNode = vehicleNodes.item(j);
                        if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element vehicleElement = (Element) vehicleNode;

                            // Lấy các thuộc tính của phương tiện
                            String licensePlate = vehicleElement.getElementsByTagName("LicensePlate").item(0).getTextContent();
                            String entryTime = vehicleElement.getElementsByTagName("EntryTime").item(0).getTextContent();
                            String exitTime = vehicleElement.getElementsByTagName("ExitTime").item(0).getTextContent();
                            double parkingFee = Double.parseDouble(vehicleElement.getElementsByTagName("ParkingFee").item(0).getTextContent());

                            // Tạo một đối tượng Vehicle từ thông tin thu thập được
                            Vehicle vehicle = new Vehicle(licensePlate, entryTime);
                            vehicle.setExitTime(exitTime);
                            vehicle.setParkingFee(parkingFee);

                            // Thêm Vehicle vào danh sách
                            vehicles.add(vehicle);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vehicles;
    }

}
