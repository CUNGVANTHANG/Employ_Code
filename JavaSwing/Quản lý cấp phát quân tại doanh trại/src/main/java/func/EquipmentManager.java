// func/EquipmentManager.java
package func;

import entity.Equipment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {

    private List<Equipment> equipmentList;

    public EquipmentManager() {
        equipmentList = new ArrayList<>();
    }

    public void addEquipment(Equipment equipment) throws Exception {
        equipmentList.add(equipment);
    }

    public void updateEquipment(Equipment equipment) {
        for (int i = 0; i < equipmentList.size(); i++) {
            if (equipmentList.get(i).getName().equals(equipment.getName())) {
                equipmentList.set(i, equipment);

                break;
            }
        }
    }

    public void deleteEquipment(Equipment equipment) {
        equipmentList.removeIf(eq -> eq.getName().equals(equipment.getName()));
    }

    public Equipment findEquipment(String name) {
        for (Equipment equipment : equipmentList) {
            if (equipment.getName().equalsIgnoreCase(name)) {
                return equipment;
            }
        }
        return null;
    }

    public void loadEquipmentList(String filePath) throws Exception {
        equipmentList = readEquipmentsFromXML(filePath);
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    private List<Equipment> readEquipmentsFromXML(String filePath) throws Exception {
        List<Equipment> equipments = new ArrayList<>();
        Document doc = XMLUtils.loadXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("equipment");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String category = element.getElementsByTagName("category").item(0).getTextContent();
                equipments.add(new Equipment(name, category));
            }
        }
        return equipments;
    }

    public void addEquipmentToXML(Equipment equipment, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        Element root = doc.getDocumentElement();
        Element equipmentElement = doc.createElement("equipment");

        Element nameElement = doc.createElement("name");
        nameElement.setTextContent(equipment.getName().trim());
        Element categoryElement = doc.createElement("category");
        categoryElement.setTextContent(equipment.getCategory().trim());

        equipmentElement.appendChild(nameElement);
        equipmentElement.appendChild(categoryElement);

        root.appendChild(equipmentElement);

        XMLUtils.saveXML(doc, filePath);
    }

    public void updateEquipmentInXML(Equipment oldEquipment, Equipment newEquipment, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("equipment");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            if (name.equals(oldEquipment.getName())) {
                element.getElementsByTagName("name").item(0).setTextContent(newEquipment.getName().trim());
                element.getElementsByTagName("category").item(0).setTextContent(newEquipment.getCategory().trim());
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }

    public void deleteEquipmentFromXML(String name, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("equipment");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String equipmentName = element.getElementsByTagName("name").item(0).getTextContent();
            if (equipmentName.equals(name)) {
                Node parentNode = element.getParentNode();
                parentNode.removeChild(element);
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }


    public static void main(String[] args) {
        EquipmentManager equipmentManager = new EquipmentManager();

        try {
            equipmentManager.loadEquipmentList("src/main/resources/equipments.xml");

            // Hiển thị thông tin các trang thiết bị đã nạp
            System.out.println("Danh sách các trang thiết bị:");
            for (Equipment equipment : equipmentManager.getEquipmentList()) {
                System.out.println("Name: " + equipment.getName() + ", Category: " + equipment.getCategory());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi nạp dữ liệu từ file XML.");
        }
    }
}
