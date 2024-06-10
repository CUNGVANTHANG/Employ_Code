package utils;

import entity.FinanceEntry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLHandler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static List<FinanceEntry> loadFromFile(String filePath) {
        List<FinanceEntry> entries = new ArrayList<>();
        try {
            File xmlFile = new File(filePath);
            if (!xmlFile.exists()) {
                return entries;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList nodeList = doc.getElementsByTagName("entry");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                boolean isIncome = Boolean.parseBoolean(element.getElementsByTagName("isIncome").item(0).getTextContent());
                double amount = Double.parseDouble(element.getElementsByTagName("amount").item(0).getTextContent());
                Date date = dateFormat.parse(element.getElementsByTagName("date").item(0).getTextContent());
                String notes = element.getElementsByTagName("notes").item(0).getTextContent();

                FinanceEntry entry = new FinanceEntry(isIncome, amount, date, notes);
                entries.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    public static void saveToFile(String filePath, List<FinanceEntry> entries) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("entries");
            doc.appendChild(rootElement);

            for (FinanceEntry entry : entries) {
                Element entryElement = doc.createElement("entry");

                Element isIncomeElement = doc.createElement("isIncome");
                isIncomeElement.appendChild(doc.createTextNode(String.valueOf(entry.isIncome())));
                entryElement.appendChild(isIncomeElement);

                Element amountElement = doc.createElement("amount");
                amountElement.appendChild(doc.createTextNode(String.valueOf(entry.getAmount())));
                entryElement.appendChild(amountElement);

                Element dateElement = doc.createElement("date");
                dateElement.appendChild(doc.createTextNode(dateFormat.format(entry.getDate())));
                entryElement.appendChild(dateElement);

                Element notesElement = doc.createElement("notes");
                notesElement.appendChild(doc.createTextNode(entry.getNotes()));
                entryElement.appendChild(notesElement);

                rootElement.appendChild(entryElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
