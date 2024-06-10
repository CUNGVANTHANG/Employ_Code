package func;

import entity.Department;
import org.w3c.dom.*;
import utils.XMLUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DepartmentManager {
    private List<Department> departments;
    private static final String filePath = "src/main/resources/departments.xml";

    public DepartmentManager() {
        this.departments = new ArrayList<>();
        loadDepartmentsFromXML();
    }

    public void addDepartment(Department department) {
        departments.add(department);
        saveDepartmentsToXML();
    }

    public void removeDepartment(Department department) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepartmentID().equals(department.getDepartmentID())) {
                departments.remove(i);
                saveDepartmentsToXML();
                break;
            }
        }
    }

    public Department findDepartmentByID(String departmentID) {
        for (Department department : departments) {
            if (department.getDepartmentID().equals(departmentID)) {
                return department;
            }
        }
        return null;
    }

    public void updateDepartment(Department oldDepartment, Department newDepartment) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepartmentID().equals(oldDepartment.getDepartmentID())) {
                departments.set(i, newDepartment);
                saveDepartmentsToXML();
                break;
            }
        }
    }

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }

    public int getDepartmentCount() {
        return departments.size();
    }

    public void loadDepartmentsFromXML() {
        Document doc = XMLUtils.loadXML(filePath);
        if (doc != null) {
            NodeList nodeList = doc.getElementsByTagName("department");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    departments.add(new Department(id, name));
                }
            }
        }
    }

    private void saveDepartmentsToXML() {
        try {
            Document doc = XMLUtils.loadXML(filePath);
            Element root = doc.getDocumentElement();

            while (root.hasChildNodes()) {
                root.removeChild(root.getFirstChild());
            }

            for (Department department : departments) {
                Element departmentElement = doc.createElement("department");

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(department.getDepartmentID()));
                departmentElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(department.getDepartmentName()));
                departmentElement.appendChild(nameElement);

                root.appendChild(departmentElement);
            }

            XMLUtils.saveXML(filePath, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
