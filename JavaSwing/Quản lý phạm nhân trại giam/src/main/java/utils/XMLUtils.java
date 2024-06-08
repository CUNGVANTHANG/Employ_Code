package utils;

import entity.Prisoner;
import entity.Visitor;
import entity.Prison;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLUtils {

    public static List<Prisoner> readPrisonersFromXML(String filePath) {
        List<Prisoner> prisoners = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return prisoners;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Prisoner");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String id = eElement.getElementsByTagName("Id").item(0).getTextContent();
                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    int age = Integer.parseInt(eElement.getElementsByTagName("Age").item(0).getTextContent());
                    String crime = eElement.getElementsByTagName("Crime").item(0).getTextContent();
                    int sentenceYears = Integer.parseInt(eElement.getElementsByTagName("SentenceYears").item(0).getTextContent());
                    String prison = eElement.getElementsByTagName("Prison").item(0).getTextContent();

                    Prisoner prisoner = new Prisoner(id, name, age, crime, sentenceYears, prison);
                    prisoners.add(prisoner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prisoners;
    }

    public static List<Visitor> readVisitorsFromXML(String filePath) {
        List<Visitor> visitors = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return visitors;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Visitor");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String id = eElement.getElementsByTagName("Id").item(0).getTextContent();
                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    String relationship = eElement.getElementsByTagName("Relationship").item(0).getTextContent();
                    String prisonerId = eElement.getElementsByTagName("PrisonerId").item(0).getTextContent();

                    Visitor visitor = new Visitor(id, name, relationship, prisonerId);
                    visitors.add(visitor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return visitors;
    }

    public static List<Prison> readPrisonsFromXML(String filePath) {
        List<Prison> prisons = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return prisons;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Prison");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String name = eElement.getElementsByTagName("Name").item(0).getTextContent();
                    String location = eElement.getElementsByTagName("Location").item(0).getTextContent();
                    int capacity = Integer.parseInt(eElement.getElementsByTagName("Capacity").item(0).getTextContent());

                    Prison prison = new Prison(name, location, capacity);
                    prisons.add(prison);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prisons;
    }

    public static void writePrisonsToXML(List<Prison> prisons, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Prisons");
            doc.appendChild(rootElement);

            for (Prison prison : prisons) {
                Element prisonElement = doc.createElement("Prison");

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(prison.getName()));
                prisonElement.appendChild(name);

                Element location = doc.createElement("Location");
                location.appendChild(doc.createTextNode(prison.getLocation()));
                prisonElement.appendChild(location);

                Element capacity = doc.createElement("Capacity");
                capacity.appendChild(doc.createTextNode(String.valueOf(prison.getCapacity())));
                prisonElement.appendChild(capacity);

                rootElement.appendChild(prisonElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writePrisonersToXML(List<Prisoner> prisoners, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Prisoners");
            doc.appendChild(rootElement);

            for (Prisoner prisoner : prisoners) {
                Element prisonerElement = doc.createElement("Prisoner");

                Element id = doc.createElement("Id");
                id.appendChild(doc.createTextNode(prisoner.getId()));
                prisonerElement.appendChild(id);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(prisoner.getName()));
                prisonerElement.appendChild(name);

                Element age = doc.createElement("Age");
                age.appendChild(doc.createTextNode(String.valueOf(prisoner.getAge())));
                prisonerElement.appendChild(age);

                Element crime = doc.createElement("Crime");
                crime.appendChild(doc.createTextNode(prisoner.getCrime()));
                prisonerElement.appendChild(crime);

                Element sentenceYears = doc.createElement("SentenceYears");
                sentenceYears.appendChild(doc.createTextNode(String.valueOf(prisoner.getSentenceYears())));
                prisonerElement.appendChild(sentenceYears);

                Element prison = doc.createElement("Prison");
                prison.appendChild(doc.createTextNode(prisoner.getPrison()));
                prisonerElement.appendChild(prison);

                rootElement.appendChild(prisonerElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeVisitorsToXML(List<Visitor> visitors, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Visitors");
            doc.appendChild(rootElement);

            for (Visitor visitor : visitors) {
                Element visitorElement = doc.createElement("Visitor");

                Element id = doc.createElement("Id");
                id.appendChild(doc.createTextNode(visitor.getId()));
                visitorElement.appendChild(id);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(visitor.getName()));
                visitorElement.appendChild(name);

                Element relationship = doc.createElement("Relationship");
                relationship.appendChild(doc.createTextNode(visitor.getRelationship()));
                visitorElement.appendChild(relationship);

                Element prisonerId = doc.createElement("PrisonerId");
                prisonerId.appendChild(doc.createTextNode(visitor.getPrisonerId()));
                visitorElement.appendChild(prisonerId);

                rootElement.appendChild(visitorElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

