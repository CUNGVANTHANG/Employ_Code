package func;

import entity.Exam;
import utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamManager {
    private List<Exam> exams;
    private StudentManager studentManager;
    private String filePath = "src/main/resources/exams.xml";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ExamManager() {
        studentManager = new StudentManager();
        this.exams = new ArrayList<>();
        loadExamsFromXML(filePath);
    }

    public List<Exam> searchByDate(String dateStr) {
        try {
            Date date = dateFormat.parse(dateStr);
            List<Exam> results = new ArrayList<>();
            for (Exam exam : exams) {
                if (exam.getDate().equals(date)) {
                    results.add(exam);
                }
            }
            return results;
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Thống kê số lượng xe tối đa của mỗi loại bằng
    public Map<String, Integer> statisticVehicleCountByLicenseType() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Exam exam : exams) {
            String licenseType = exam.getLicenseType();
            int vehicleCount = exam.getVehicleCount();
            countMap.put(licenseType, countMap.getOrDefault(licenseType, 0) + vehicleCount);
        }

        System.out.println("Thống kê số lượng xe theo loại bằng:");
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        return countMap;
    }

    private void loadExamsFromXML(String filePath) {
        Document doc = XMLUtils.loadXML(filePath);
        NodeList nodeList = doc.getElementsByTagName("exam");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            try {
                Date date = dateFormat.parse(element.getElementsByTagName("date").item(0).getTextContent());
                String location = element.getElementsByTagName("location").item(0).getTextContent();
                int vehicleCount = Integer.parseInt(element.getElementsByTagName("vehicleCount").item(0).getTextContent());
                String licenseType = element.getElementsByTagName("licenseType").item(0).getTextContent();
                String examForm = element.getElementsByTagName("examForm").item(0).getTextContent();

                exams.add(new Exam(date, location, vehicleCount, licenseType, examForm));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void addExam(Exam newExam) {
        exams.add(newExam);
        saveExams();

    }

    public void updateExam(int index, Exam exam) {
        exams.set(index, exam);
        saveExams();
    }

    public void deleteExam(int index) {
        exams.remove(index);
        saveExams();
    }

    private void saveExams() {
        try {
            Document doc = XMLUtils.loadXML(filePath);
            Element root = doc.getDocumentElement();

            // Xóa tất cả các phần tử con
            while (root.hasChildNodes()) {
                root.removeChild(root.getFirstChild());
            }

            for (Exam exam : exams) {
                Element examElement = doc.createElement("exam");

                Element date = doc.createElement("date");
                date.appendChild(doc.createTextNode(dateFormat.format(exam.getDate())));
                examElement.appendChild(date);

                Element location = doc.createElement("location");
                location.appendChild(doc.createTextNode(exam.getLocation()));
                examElement.appendChild(location);

                Element vehicleCount = doc.createElement("vehicleCount");
                vehicleCount.appendChild(doc.createTextNode(String.valueOf(exam.getVehicleCount())));
                examElement.appendChild(vehicleCount);

                Element licenseType = doc.createElement("licenseType");
                licenseType.appendChild(doc.createTextNode(exam.getLicenseType()));
                examElement.appendChild(licenseType);

                Element examForm = doc.createElement("examForm");
                examForm.appendChild(doc.createTextNode(exam.getExamForm()));
                examElement.appendChild(examForm);

                root.appendChild(examElement);
            }

            XMLUtils.saveXML(filePath, doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Exam> searchByDateAndLicenseType(String dateStr, String licenseType) {
        try {
            Date date = dateFormat.parse(dateStr);
            List<Exam> results = new ArrayList<>();
            for (Exam exam : exams) {
                if (exam.getDate().equals(date) && exam.getLicenseType().equals(licenseType)) {
                    results.add(exam);
                }
            }
            return results;
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Exam> searchByDayMonthYear(int day, int month, int year) {
        List<Exam> results = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (Exam exam : exams) {
            calendar.setTime(exam.getDate());
            int examDay = calendar.get(Calendar.DAY_OF_MONTH);
            int examMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH bắt đầu từ 0
            int examYear = calendar.get(Calendar.YEAR);
            if (examDay == day && examMonth == month && examYear == year) {
                results.add(exam);
            }
        }
        return results;
    }

}
