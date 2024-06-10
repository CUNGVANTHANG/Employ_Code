package func;

import entity.Employee;
import org.w3c.dom.*;

import utils.XMLUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;
    private static final String filePath = "src/main/resources/employees.xml";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public EmployeeManager() {
        this.employees = new ArrayList<>();
        loadEmployeesFromXML();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveEmployeesToXML();
    }

    public void removeEmployee(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(employee.getId())) {
                employees.remove(i);
                saveEmployeesToXML();
                break;
            }
        }
    }

    public void updateEmployee(Employee oldEmployee, Employee newEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(oldEmployee.getId())) {
                employees.set(i, newEmployee);
                saveEmployeesToXML();
                break;
            }
        }
    }

    public Employee findEmployeeByID(String employeeID) {
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeID)) {
                return employee;
            }
        }
        return null;
    }
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public int getEmployeeCount() {
        return employees.size();
    }

    private void loadEmployeesFromXML() {
        Document doc = XMLUtils.loadXML(filePath);
        if (doc != null) {
            NodeList nodeList = doc.getElementsByTagName("employee");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    Date dateOfBirth = null;
                    try {
                        dateOfBirth = dateFormat.parse(element.getElementsByTagName("dateOfBirth").item(0).getTextContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String gender = element.getElementsByTagName("gender").item(0).getTextContent();
                    String hometown = element.getElementsByTagName("hometown").item(0).getTextContent();
                    double salary = Double.parseDouble(element.getElementsByTagName("salary").item(0).getTextContent());
                    String department = element.getElementsByTagName("department").item(0).getTextContent();
                    String position = element.getElementsByTagName("position").item(0).getTextContent();

                    employees.add(new Employee(id, name, dateOfBirth, gender, hometown, salary, department, position));
                }
            }
        }
    }

    private void saveEmployeesToXML() {
        try {
            Document doc = XMLUtils.loadXML(filePath);
            Element rootElement = doc.getDocumentElement();

            // Xóa tất cả các phần tử con
            while (rootElement.hasChildNodes()) {
                rootElement.removeChild(rootElement.getFirstChild());
            }

            for (Employee employee : employees) {
                Element employeeElement = doc.createElement("employee");

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(employee.getId()));
                employeeElement.appendChild(idElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(employee.getName()));
                employeeElement.appendChild(nameElement);

                Element dobElement = doc.createElement("dateOfBirth");
                dobElement.appendChild(doc.createTextNode(dateFormat.format(employee.getDateOfBirth())));
                employeeElement.appendChild(dobElement);

                Element genderElement = doc.createElement("gender");
                genderElement.appendChild(doc.createTextNode(employee.getGender()));
                employeeElement.appendChild(genderElement);

                Element hometownElement = doc.createElement("hometown");
                hometownElement.appendChild(doc.createTextNode(employee.getHometown()));
                employeeElement.appendChild(hometownElement);

                Element salaryElement = doc.createElement("salary");
                salaryElement.appendChild(doc.createTextNode(Double.toString(employee.getSalary())));
                employeeElement.appendChild(salaryElement);

                Element departmentElement = doc.createElement("department");
                departmentElement.appendChild(doc.createTextNode(employee.getDepartment()));
                employeeElement.appendChild(departmentElement);

                Element positionElement = doc.createElement("position");
                positionElement.appendChild(doc.createTextNode(employee.getPosition()));
                employeeElement.appendChild(positionElement);

                rootElement.appendChild(employeeElement);
            }

            XMLUtils.saveXML(filePath, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
