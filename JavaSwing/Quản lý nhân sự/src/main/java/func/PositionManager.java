package func;

import entity.Position;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

import java.util.ArrayList;
import java.util.List;

public class PositionManager {
    private List<Position> positions;
    private static final String filePath = "src/main/resources/positions.xml";

    public PositionManager() {
        this.positions = new ArrayList<>();
        loadPositionsFromXML();
    }

    public void addPosition(Position position) {
        positions.add(position);
        savePositionsToXML();
    }

    public void removePosition(Position oldPosition) {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getPositionID().equals(oldPosition.getPositionID())) {
                positions.remove(i);
                savePositionsToXML();
                break;
            }
        }
    }

    public void updatePosition(Position oldPosition, Position newPosition) {
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getPositionID().equals(oldPosition.getPositionID())) {
                positions.set(i, newPosition);
                savePositionsToXML();
                break;
            }
        }
    }

    public Position findPositionByID(String positionID) {
        for (Position position : positions) {
            if (position.getPositionID().equals(positionID)) {
                return position;
            }
        }
        return null;
    }

    public List<Position> getAllPositions() {
        return new ArrayList<>(positions);
    }

    public int getPositionCount() {
        return positions.size();
    }

    private void loadPositionsFromXML() {
        Document doc = XMLUtils.loadXML(filePath);
        if (doc != null) {
            NodeList nodeList = doc.getElementsByTagName("position");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    double commission = Double.parseDouble(element.getElementsByTagName("commission").item(0).getTextContent());
                    double allowance = Double.parseDouble(element.getElementsByTagName("allowance").item(0).getTextContent());

                    positions.add(new Position(id, name, commission, allowance));
                }
            }
        }
    }

    private void savePositionsToXML() {
        try {
            Document doc = XMLUtils.loadXML(filePath);
            Element rootElement = doc.getDocumentElement();

            // Xóa tất cả các phần tử con
            while (rootElement.hasChildNodes()) {
                rootElement.removeChild(rootElement.getFirstChild());
            }

            for (Position position : positions) {
                Element positionElement = doc.createElement("position");

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(position.getPositionID()));
                positionElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(position.getPositionName()));
                positionElement.appendChild(nameElement);

                Element commissionElement = doc.createElement("commission");
                commissionElement.appendChild(doc.createTextNode(Double.toString(position.getCommission())));
                positionElement.appendChild(commissionElement);

                Element allowanceElement = doc.createElement("allowance");
                allowanceElement.appendChild(doc.createTextNode(Double.toString(position.getAllowance())));
                positionElement.appendChild(allowanceElement);

                rootElement.appendChild(positionElement);
            }

            XMLUtils.saveXML(filePath, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
