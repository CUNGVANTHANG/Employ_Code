package func;

import entity.Student;
import utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentManager {
    private List<Student> students;
    private String filePath = "src/main/resources/students.xml";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public StudentManager() {
        this.students = new ArrayList<>();
        loadStudentsFromXML(filePath);
    }

    // Tìm kiếm sinh viên theo tên
    public List<Student> searchByName(String name) {
        List<Student> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("^" + name.toLowerCase());
        for (Student student : students) {
            Matcher matcher = pattern.matcher(student.getName().toLowerCase());
            if (matcher.find()) {
                results.add(student);
            }
        }
        return results;
    }

    // Thống kê sinh viên theo kết quả thi
    public Map<String, Integer> statisticStudentByExamResult() {
        Map<String, Integer> statistics = new HashMap<>();
        for (Student student : students) {
            String examResult = student.getResult();
            statistics.put(examResult, statistics.getOrDefault(examResult, 0) + 1);
        }
        return statistics;
    }

    private void loadStudentsFromXML(String filePath) {
        Document doc = XMLUtils.loadXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String cccd = element.getElementsByTagName("cccd").item(0).getTextContent();
            String registeredLicenseType = element.getElementsByTagName("registeredLicenseType").item(0).getTextContent();
            String examDateStr = element.getElementsByTagName("examDate").item(0).getTextContent();
            String result = element.getElementsByTagName("result").item(0).getTextContent();

            Date examDate = null;
            try {
                examDate = dateFormat.parse(examDateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            students.add(new Student(name, cccd, registeredLicenseType, examDate, result));
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student newStudent) {
        students.add(newStudent);
        saveStudents();
    }

    public void updateStudent(int index, Student student) {
        students.set(index, student);
        saveStudents();
    }

    public void deleteStudent(int index) {
        students.remove(index);
        saveStudents();
    }

    public void saveStudents() {
        try {
            Document doc = XMLUtils.loadXML(filePath);
            Element root = doc.getDocumentElement();

            // Xóa tất cả các phần tử con
            while (root.hasChildNodes()) {
                root.removeChild(root.getFirstChild());
            }

            for (Student student : students) {
                Element studentElement = doc.createElement("student");

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(student.getName()));
                studentElement.appendChild(name);

                Element cccd = doc.createElement("cccd");
                cccd.appendChild(doc.createTextNode(student.getCccd()));
                studentElement.appendChild(cccd);

                Element registeredLicenseType = doc.createElement("registeredLicenseType");
                registeredLicenseType.appendChild(doc.createTextNode(student.getRegisteredLicenseType()));
                studentElement.appendChild(registeredLicenseType);

                Element examDate = doc.createElement("examDate");
                examDate.appendChild(doc.createTextNode(dateFormat.format(student.getExamDate())));
                studentElement.appendChild(examDate);

                Element result = doc.createElement("result");
                result.appendChild(doc.createTextNode(student.getResult()));
                studentElement.appendChild(result);

                root.appendChild(studentElement);
            }

            XMLUtils.saveXML(filePath, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
