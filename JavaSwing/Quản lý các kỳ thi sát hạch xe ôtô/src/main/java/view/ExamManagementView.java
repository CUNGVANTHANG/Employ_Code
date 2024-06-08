package view;

import com.toedter.calendar.JDateChooser;
import entity.Exam;
import entity.Student;
import func.ExamManager;
import func.StudentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ExamManagementView extends JFrame {
    private ExamManager examManager;
    private JTable examTable;
    private DefaultTableModel tableModel;
    private StudentManager studentManager;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ExamManagementView(ExamManager examManager, StudentManager studentManager) {
        this.examManager = examManager;
        this.studentManager = studentManager;

        setTitle("Quản lý kỳ thi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
    }

    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Ngày thi", "Địa điểm", "Số lượng xe", "Loại bằng", "Hình thức thi"};
        tableModel = new DefaultTableModel(columnNames, 0);
        examTable = new JTable(tableModel);

        updateTable();

        JScrollPane scrollPane = new JScrollPane(examTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JLabel lblSearch = new JLabel("Tìm kiếm:");
        JLabel lblDay = new JLabel("Ngày:");
        JLabel lblMonth = new JLabel("Tháng:");
        JLabel lblYear = new JLabel("Năm:");

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 25));

        JTextField txtDay = new JTextField();
        txtDay.setPreferredSize(new Dimension(50, 25));

        JTextField txtMonth = new JTextField();
        txtMonth.setPreferredSize(new Dimension(50, 25));

        JTextField txtYear = new JTextField();
        txtYear.setPreferredSize(new Dimension(70, 25));

        JButton btnSearch = new JButton("Tìm kiếm");

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int searchDay, searchMonth, searchYear;
                if (!txtDay.getText().isEmpty() && !txtMonth.getText().isEmpty() && !txtYear.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByDayMonthYear(searchDay, searchMonth, searchYear));
                } else if (!txtDay.getText().isEmpty() && !txtMonth.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    showSearchResults(searchByDayMonth(searchDay, searchMonth));
                } else if (!txtDay.getText().isEmpty() && !txtYear.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByDayYear(searchDay, searchYear));
                } else if (!txtMonth.getText().isEmpty() && !txtYear.getText().isEmpty()) {
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByMonthYear(searchMonth, searchYear));
                } else if (!txtDay.getText().isEmpty()) {
                    searchDay = Integer.parseInt(txtDay.getText());
                    showSearchResults(searchByDay(searchDay));
                } else if (!txtMonth.getText().isEmpty()) {
                    searchMonth = Integer.parseInt(txtMonth.getText());
                    showSearchResults(searchByMonth(searchMonth));
                } else if (!txtYear.getText().isEmpty()) {
                    searchYear = Integer.parseInt(txtYear.getText());
                    showSearchResults(searchByYear(searchYear));
                }
            }
        });

        controlPanel.add(lblSearch);
        controlPanel.add(lblDay);
        controlPanel.add(txtDay);
        controlPanel.add(lblMonth);
        controlPanel.add(txtMonth);
        controlPanel.add(lblYear);
        controlPanel.add(txtYear);
        controlPanel.add(btnSearch);

        JButton btnStatistic = new JButton("Thống kê");
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        btnStatistic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticVehicleCountByLicenseType();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExam();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editExam();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExam();
            }
        });

        controlPanel.add(btnStatistic);
        controlPanel.add(btnAdd);
        controlPanel.add(btnEdit);
        controlPanel.add(btnDelete);

        panel.add(controlPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public List<Exam> searchByMonth(int month) {
        List<Exam> results = new ArrayList<>();
        for (Exam exam : examManager.getExams()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(exam.getDate());
            if (calendar.get(Calendar.MONTH) + 1 == month) {
                results.add(exam);
            }
        }
        return results;
    }

    public List<Exam> searchByDayMonthYear(int day, int month, int year) {
        List<Exam> results = new ArrayList<>();
        for (Exam exam : examManager.getExams()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(exam.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day && calendar.get(Calendar.MONTH) + 1 == month && calendar.get(Calendar.YEAR) == year) {
                results.add(exam);
            }
        }
        return results;
    }

    public List<Exam> searchByYear(int year) {
        List<Exam> results = new ArrayList<>();
        for (Exam exam : examManager.getExams()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(exam.getDate());
            if (calendar.get(Calendar.YEAR) == year) {
                results.add(exam);
            }
        }
        return results;
    }

    public List<Exam> searchByDayYear(int day, int year) {
        List<Exam> results = new ArrayList<>();
        for (Exam exam : examManager.getExams()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(exam.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day && calendar.get(Calendar.YEAR) == year) {
                results.add(exam);
            }
        }
        return results;
    }

    public List<Exam> searchByMonthYear(int month, int year) {
        List<Exam> results = new ArrayList<>();
        for (Exam exam : examManager.getExams()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(exam.getDate());
            if (calendar.get(Calendar.MONTH) + 1 == month && calendar.get(Calendar.YEAR) == year) {
                results.add(exam);
            }
        }
        return results;
    }

    public List<Exam> searchByDayMonth(int day, int month) {
        List<Exam> results = new ArrayList<>();
        for (Exam exam : examManager.getExams()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(exam.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day && calendar.get(Calendar.MONTH) + 1 == month) {
                results.add(exam);
            }
        }
        return results;
    }

    public List<Exam> searchByDay(int day) {
        List<Exam> results = new ArrayList<>();
        for (Exam exam : examManager.getExams()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(exam.getDate());
            if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                results.add(exam);
            }
        }
        return results;
    }

    private void showSearchResults(List<Exam> results) {
        if (!results.isEmpty()) {
            ExamResultDialog resultDialog = new ExamResultDialog(this, "Kết quả tìm kiếm", results);
            resultDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy kỳ thi", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void statisticVehicleCountByLicenseType() {
        Map<String, Integer> statistics = examManager.statisticVehicleCountByLicenseType();
        StringBuilder message = new StringBuilder("Thống kê số lượng xe theo loại bằng:\n");
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            message.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Thống kê", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Exam exam : examManager.getExams()) {
            Object[] row = {dateFormat.format(exam.getDate()), exam.getLocation(), exam.getVehicleCount(), exam.getLicenseType(), exam.getExamForm()};
            tableModel.addRow(row);
        }
    }

    private void addExam() {
        ExamDialog dialog = new ExamDialog(this, "Thêm kỳ thi mới", null);
        dialog.setVisible(true);
        Exam newExam = dialog.getExam();
        if (newExam != null) {
            boolean found = false;
            for (Exam exam : examManager.getExams()) {
                if (exam.getDate().equals(newExam.getDate())
                        && exam.getExamForm().equals(newExam.getExamForm())
                        && exam.getLocation().equals(newExam.getLocation())
                        && exam.getLicenseType().equals(newExam.getLicenseType())) {
                    found = true;
                    break;
                }
            }
            if (found) {
                // Hiển thị thông báo về sự trùng lặp
                JOptionPane.showMessageDialog(this, "Kỳ thi đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                // Nếu không tìm thấy trùng lặp, thêm kỳ thi mới và cập nhật bảng
                examManager.addExam(newExam);
                updateTable();
            }
        }
    }

    private void editExam() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow >= 0) {
            Exam existingExam = examManager.getExams().get(selectedRow);
            ExamDialog dialog = new ExamDialog(this, "Sửa kỳ thi", existingExam);
            dialog.setVisible(true);
            Exam updatedExam = dialog.getExam();
            if (updatedExam != null) {
                // Kiểm tra xem ngày thi và loại bằng đã tồn tại hay chưa
                boolean dateConflict = false;
                for (Exam exam : examManager.getExams()) {
                    if (exam.getDate().equals(updatedExam.getDate())
                            && exam.getLicenseType().equals(updatedExam.getLicenseType())
                            && exam.getLocation().equals(updatedExam.getLocation())
                            && exam.getExamForm().equals(updatedExam.getExamForm())) {
                        dateConflict = true;
                        break;
                    }
                }

                if (dateConflict) {
                    JOptionPane.showMessageDialog(this, "Kỳ thi đã tồn tại. Vui lòng sửa lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Cập nhật ngày thi và loại bằng trong examManager
                    examManager.updateExam(selectedRow, updatedExam);

                    // Cập nhật ngày thi và loại bằng của các học viên liên quan trong studentManager
                    List<Student> students = studentManager.getStudents();
                    for (Student student : students) {
                        if (student.getRegisteredLicenseType().equals(existingExam.getLicenseType())
                                && student.getExamDate().equals(existingExam.getDate())) {
                            student.setExamDate(updatedExam.getDate());
                            student.setRegisteredLicenseType(updatedExam.getLicenseType());
                        }
                    }

                    // Lưu lại danh sách sinh viên sau khi cập nhật
                    studentManager.saveStudents();

                    // Cập nhật bảng hiển thị
                    updateTable();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kỳ thi để sửa");
        }
    }

    private void deleteExam() {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow >= 0) {
            Exam examToDelete = examManager.getExams().get(selectedRow);

            // Tìm tất cả sinh viên có ngày thi và loại bài thi trùng khớp với kỳ thi cần xóa
            for (int i = studentManager.getStudents().size() - 1; i >= 0; i--) {
                Student student = studentManager.getStudents().get(i);
                if (student.getExamDate().equals(examToDelete.getDate()) &&
                        student.getRegisteredLicenseType().equals(examToDelete.getLicenseType())) {
                    // Xóa thông tin của sinh viên được tìm thấy tại vị trí index
                    studentManager.deleteStudent(i);
                }
            }

            // Xóa kỳ thi
            examManager.deleteExam(selectedRow);

            // Cập nhật bảng hiển thị
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kỳ thi để xóa");
        }
    }
}
