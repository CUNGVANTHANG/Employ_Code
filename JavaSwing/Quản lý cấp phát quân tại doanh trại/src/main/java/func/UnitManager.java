package func;

import entity.Unit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

import java.util.ArrayList;
import java.util.List;

public class UnitManager {
    private List<Unit> unitList;

    public UnitManager() {
        unitList = new ArrayList<>();
    }

    public void addUnit(Unit unit) {
        unitList.add(unit);
    }

    public void updateUnit(Unit unit) {
        for (int i = 0; i < unitList.size(); i++) {
            if (unitList.get(i).getId().equals(unit.getId())) {
                unitList.set(i, unit);
                break;
            }
        }
    }

    public void deleteUnit(Unit unit) {
        unitList.removeIf(u -> u.getId().equals(unit.getId()));
    }

    public Unit findUnit(String id) {
        for (Unit unit : unitList) {
            if (unit.getId().equalsIgnoreCase(id)) {
                return unit;
            }
        }
        return null;
    }

    public void loadUnitList(String filePath) throws Exception {
        unitList = readUnitsFromXML(filePath);
    }

    public List<Unit> getUnitList() {
        return unitList;
    }

    private List<Unit> readUnitsFromXML(String filePath) throws Exception {
        List<Unit> units = new ArrayList<>();
        Document doc = XMLUtils.loadXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("unit");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String id = element.getElementsByTagName("id").item(0).getTextContent();
                int soldierCount = Integer.parseInt(element.getElementsByTagName("soldierCount").item(0).getTextContent());
                units.add(new Unit(name, id, soldierCount));
            }
        }
        return units;
    }

    public void addUnitToXML(Unit unit, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        Element root = doc.getDocumentElement();
        Element unitElement = doc.createElement("unit");

        Element nameElement = doc.createElement("name");
        nameElement.setTextContent(unit.getName().trim());
        Element idElement = doc.createElement("id");
        idElement.setTextContent(unit.getId().trim());
        Element soldierCountElement = doc.createElement("soldierCount");
        soldierCountElement.setTextContent(String.valueOf(unit.getSoldierCount()).trim());

        unitElement.appendChild(nameElement);
        unitElement.appendChild(idElement);
        unitElement.appendChild(soldierCountElement);

        root.appendChild(unitElement);

        XMLUtils.saveXML(doc, filePath);
    }

    public void updateUnitInXML(Unit oldUnit, Unit newUnit, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("unit");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String id = element.getElementsByTagName("id").item(0).getTextContent();
            if (id.equals(oldUnit.getId())) {
                element.getElementsByTagName("name").item(0).setTextContent(newUnit.getName().trim());
                element.getElementsByTagName("id").item(0).setTextContent(newUnit.getId().trim());
                element.getElementsByTagName("soldierCount").item(0).setTextContent(String.valueOf(newUnit.getSoldierCount()).trim());
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }

    public void deleteUnitFromXML(Unit unit, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("unit");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String unitId = element.getElementsByTagName("id").item(0).getTextContent();
            if (unitId.equals(unit.getId())) {

                Node parentNode = element.getParentNode();
                parentNode.removeChild(element);
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }

    public static void main(String[] args) {
        UnitManager unitManager = new UnitManager();

        try {
            unitManager.loadUnitList("src/main/resources/units.xml");

            // Hiển thị thông tin các đơn vị đã nạp
            System.out.println("Danh sách các đơn vị:");
            for (Unit unit : unitManager.getUnitList()) {
                System.out.println("ID: " + unit.getId() + ", Name: " + unit.getName() + ", Soldier Count: " + unit.getSoldierCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi nạp dữ liệu từ file XML.");
        }
    }
}