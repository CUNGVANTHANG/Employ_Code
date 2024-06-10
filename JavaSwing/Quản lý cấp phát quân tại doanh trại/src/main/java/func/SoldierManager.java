package func;

import entity.Soldier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

import java.util.ArrayList;
import java.util.List;

public class SoldierManager {
    private List<Soldier> soldierList;

    public SoldierManager() {
        soldierList = new ArrayList<>();
    }

    public void addSoldier(Soldier soldier) {
        soldierList.add(soldier);
    }

    public void updateSoldier(Soldier soldier) {
        for (int i = 0; i < soldierList.size(); i++) {
            if (soldierList.get(i).getId().equals(soldier.getId())) {
                soldierList.set(i, soldier);
                break;
            }
        }
    }

    public void deleteSoldier(Soldier soldier) {
        soldierList.removeIf(s -> s.getId().equals(soldier.getId()));
    }

    public Soldier findSoldier(String id) {
        for (Soldier soldier : soldierList) {
            if (soldier.getId().equalsIgnoreCase(id)) {
                return soldier;
            }
        }
        return null;
    }

    public void loadSoldierList(String filePath) throws Exception {
        soldierList = readSoldiersFromXML(filePath);
    }

    public List<Soldier> getSoldierList() {
        return soldierList;
    }

    private List<Soldier> readSoldiersFromXML(String filePath) throws Exception {
        List<Soldier> soldiers = new ArrayList<>();
        Document doc = XMLUtils.loadXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("soldier");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String fullName = element.getElementsByTagName("fullName").item(0).getTextContent();
                String id = element.getElementsByTagName("id").item(0).getTextContent();
                String unit = element.getElementsByTagName("unit").item(0).getTextContent();
                String gender = element.getElementsByTagName("gender").item(0).getTextContent();
                String rank = element.getElementsByTagName("rank").item(0).getTextContent();
                soldiers.add(new Soldier(fullName, id, unit, gender, rank));
            }
        }
        return soldiers;
    }

    public void addSoldierToXML(Soldier soldier, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        Element root = doc.getDocumentElement();
        Element soldierElement = doc.createElement("soldier");

        Element fullNameElement = doc.createElement("fullName");
        fullNameElement.setTextContent(soldier.getFullName().trim());
        Element idElement = doc.createElement("id");
        idElement.setTextContent(soldier.getId().trim());
        Element unitElement = doc.createElement("unit");
        unitElement.setTextContent(soldier.getUnit().trim());
        Element genderElement = doc.createElement("gender");
        genderElement.setTextContent(soldier.getGender().trim());
        Element rankElement = doc.createElement("rank");
        rankElement.setTextContent(soldier.getRank().trim());

        soldierElement.appendChild(fullNameElement);
        soldierElement.appendChild(idElement);
        soldierElement.appendChild(unitElement);
        soldierElement.appendChild(genderElement);
        soldierElement.appendChild(rankElement);

        root.appendChild(soldierElement);

        XMLUtils.saveXML(doc, filePath);
    }

    public void updateSoldierInXML(Soldier oldSoldier, Soldier newSoldier, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("soldier");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String id = element.getElementsByTagName("id").item(0).getTextContent();
            if (id.equals(oldSoldier.getId())) {
                element.getElementsByTagName("id").item(0).setTextContent(newSoldier.getId().trim());
                element.getElementsByTagName("fullName").item(0).setTextContent(newSoldier.getFullName().trim());
                element.getElementsByTagName("unit").item(0).setTextContent(newSoldier.getUnit().trim());
                element.getElementsByTagName("gender").item(0).setTextContent(newSoldier.getGender().trim());
                element.getElementsByTagName("rank").item(0).setTextContent(newSoldier.getRank().trim());
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }

    public void deleteSoldierFromXML(Soldier soldier, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("soldier");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String soldierId = element.getElementsByTagName("id").item(0).getTextContent();
            if (soldierId.equals(soldier.getId())) {
                Node parentNode = element.getParentNode();
                parentNode.removeChild(element);
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }

    public static void main(String[] args) {
        SoldierManager soldierManager = new SoldierManager();

        try {
            soldierManager.loadSoldierList("src/main/resources/soldiers.xml");

            // Hiển thị thông tin các lính đã nạp
            System.out.println("Danh sách các lính:");
            for (Soldier soldier : soldierManager.getSoldierList()) {
                System.out.println("ID: " + soldier.getId() + ", Full Name: " + soldier.getFullName() + ", Unit: " + soldier.getUnit() +
                        ", Gender: " + soldier.getGender() + ", Rank: " + soldier.getRank());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi nạp dữ liệu từ file XML.");
        }
    }
}