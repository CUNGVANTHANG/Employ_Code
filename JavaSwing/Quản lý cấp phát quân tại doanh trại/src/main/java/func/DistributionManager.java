package func;

import entity.Distribution;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.XMLUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DistributionManager {

    private List<Distribution> distributionList;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public DistributionManager() {
        distributionList = new ArrayList<>();
    }

    public void addDistribution(Distribution distribution) {
        distributionList.add(distribution);
    }

    public void updateDistribution(Distribution distribution) {
        for (int i = 0; i < distributionList.size(); i++) {
            if (distributionList.get(i).getDistributionDate().equals(distribution.getDistributionDate()) &&
                    distributionList.get(i).getDistributionUnit().equals(distribution.getDistributionUnit())) {
                distributionList.set(i, distribution);
                break;
            }
        }
    }

    public void deleteDistribution(Distribution distribution) {
        distributionList.removeIf(d -> d.getDistributionDate().equals(distribution.getDistributionDate()) &&
                d.getDistributionUnit().equals(distribution.getDistributionUnit()));
    }

    public Distribution findDistribution(LocalDate distributionDate, String distributionUnit) {
        for (Distribution distribution : distributionList) {
            if (distribution.getDistributionDate().equals(distributionDate) &&
                    distribution.getDistributionUnit().equalsIgnoreCase(distributionUnit)) {
                return distribution;
            }
        }
        return null;
    }

    public void loadDistributionList(String filePath) throws Exception {
        distributionList = readDistributionsFromXML(filePath);
    }

    public List<Distribution> getDistributionList() {
        return distributionList;
    }

    private List<Distribution> readDistributionsFromXML(String filePath) throws Exception {
        List<Distribution> distributions = new ArrayList<>();
        Document doc = XMLUtils.loadXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("distribution");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Date distributionDate = dateFormat.parse(element.getElementsByTagName("distributionDate").item(0).getTextContent());
                String distributionUnit = element.getElementsByTagName("distributionUnit").item(0).getTextContent();
                int quantityDistributed = Integer.parseInt(element.getElementsByTagName("quantityDistributed").item(0).getTextContent());
                int quantityReturned = Integer.parseInt(element.getElementsByTagName("quantityReturned").item(0).getTextContent());
                boolean distributionStatus = Boolean.parseBoolean(element.getElementsByTagName("distributionStatus").item(0).getTextContent());
                distributions.add(new Distribution(distributionDate, distributionUnit, quantityDistributed, quantityReturned, distributionStatus));
            }
        }
        return distributions;
    }

    public void addDistributionToXML(Distribution distribution, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        Element root = doc.getDocumentElement();
        Element distributionElement = doc.createElement("distribution");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(distribution.getDistributionDate());

        Element dateElement = doc.createElement("distributionDate");
        dateElement.setTextContent(dateString.trim());

        Element unitElement = doc.createElement("distributionUnit");
        unitElement.setTextContent(distribution.getDistributionUnit().trim());

        Element quantityDistributedElement = doc.createElement("quantityDistributed");
        quantityDistributedElement.setTextContent(Integer.toString(distribution.getQuantityDistributed()).trim());

        Element quantityReturnedElement = doc.createElement("quantityReturned");
        quantityReturnedElement.setTextContent(Integer.toString(distribution.getQuantityReturned()).trim());

        Element statusElement = doc.createElement("distributionStatus");
        statusElement.setTextContent(Boolean.toString(distribution.isDistributionStatus()).trim());

        distributionElement.appendChild(dateElement);
        distributionElement.appendChild(unitElement);
        distributionElement.appendChild(quantityDistributedElement);
        distributionElement.appendChild(quantityReturnedElement);
        distributionElement.appendChild(statusElement);

        root.appendChild(distributionElement);

        XMLUtils.saveXML(doc, filePath);
    }


    public void updateDistributionInXML(Distribution distribution, Distribution newDistribution, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("distribution");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String date = element.getElementsByTagName("distributionDate").item(0).getTextContent();
            if (date.equals(dateFormat.format(distribution.getDistributionDate()))) {
                element.getElementsByTagName("distributionDate").item(0).setTextContent(dateFormat.format(newDistribution.getDistributionDate()).trim());
                element.getElementsByTagName("distributionUnit").item(0).setTextContent(newDistribution.getDistributionUnit().trim());
                element.getElementsByTagName("quantityDistributed").item(0).setTextContent(Integer.toString(newDistribution.getQuantityDistributed()).trim());
                element.getElementsByTagName("quantityReturned").item(0).setTextContent(Integer.toString(newDistribution.getQuantityReturned()).trim());
                element.getElementsByTagName("distributionStatus").item(0).setTextContent(Boolean.toString(newDistribution.isDistributionStatus()).trim());
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }

    public void deleteDistributionFromXML(Distribution distribution, String filePath) throws Exception {
        Document doc = XMLUtils.loadXML(filePath);

        NodeList nodeList = doc.getElementsByTagName("distribution");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String distributionDate = element.getElementsByTagName("distributionDate").item(0).getTextContent();
            if (distributionDate.equals(dateFormat.format(distribution.getDistributionDate()))) {
                Node parentNode = element.getParentNode();
                parentNode.removeChild(element);
                break;
            }
        }

        XMLUtils.saveXML(doc, filePath);
    }

    public static void main(String[] args) {
        DistributionManager distributionManager = new DistributionManager();

        try {
            distributionManager.loadDistributionList("src/main/resources/distributions.xml");

            // Hiển thị thông tin các phân phối đã nạp
            System.out.println("Danh sách các phân phối:");
            for (Distribution distribution : distributionManager.getDistributionList()) {
                System.out.println("Date: " + dateFormat.format(distribution.getDistributionDate())  + ", Unit: " + distribution.getDistributionUnit() +
                        ", Quantity Distributed: " + distribution.getQuantityDistributed() +
                        ", Quantity Returned: " + distribution.getQuantityReturned() +
                        ", Distribution Status: " + (distribution.isDistributionStatus() ? "True" : "False"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi nạp dữ liệu từ file XML.");
        }
    }
}