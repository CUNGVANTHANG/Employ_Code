package entity;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class XMLHandler {
    private static final String FILE_NAME = "src/main/resources/parkinglot.xml";

    public static void saveToXML(ParkingLot parkingLot) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("ParkingLot");
            doc.appendChild(rootElement);

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

                rootElement.appendChild(vehicleElement);
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

    public static ParkingLot loadFromXML() {
        ParkingLot parkingLot = new ParkingLot();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return parkingLot;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Vehicle");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String licensePlate = eElement.getElementsByTagName("LicensePlate").item(0).getTextContent();
                    String entryTime = eElement.getElementsByTagName("EntryTime").item(0).getTextContent();
                    String exitTime = eElement.getElementsByTagName("ExitTime").item(0).getTextContent();
                    double parkingFee = Double.parseDouble(eElement.getElementsByTagName("ParkingFee").item(0).getTextContent());

                    Vehicle vehicle = new Vehicle(licensePlate, entryTime);
                    vehicle.setExitTime(exitTime);
                    vehicle.setParkingFee(parkingFee);

                    parkingLot.addVehicle(vehicle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parkingLot;
    }
}
