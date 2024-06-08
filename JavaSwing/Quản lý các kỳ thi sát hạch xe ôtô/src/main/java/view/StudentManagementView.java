package view;

import entity.Exam;
import entity.Student;
import func.ExamManager;
import func.StudentManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StudentManagementView extends JFrame {
    private ExamManager examManager;
    private StudentManager studentManager;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private boolean sortAscending = true;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public StudentManagementView(StudentManager studentManager) {
        this.examManager = new ExamManager();
        this.studentManager = studentManager;

        setTitle("Quản lý học viên");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setupUI();
    }

    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Họ và tên", "CCCD", "Loại bằng đăng ký thi", "Ngày thi", "Kết quả"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);

        updateTable();

        JScrollPane scrollPane = new JScrollPane(studentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        JLabel lblSearch = new JLabel("Tìm kiếm theo tên:");

        JTextField txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(150, 25));

        JButton btnSearch = new JButton("Tìm kiếm");

        JButton btnStatistic = new JButton("Thống kê");

        JButton btnSort = new JButton("Sắp xếp theo tên");


        // Xử lý sự kiện thống kê
        btnStatistic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticStudentByExamResult();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editStudent();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtSearch.getText();
                if (!name.isEmpty()) {
                    searchByStudent(name);
                } else {
                    updateTable(); // Nếu không có ngày được nhập, hiển thị lại toàn bộ dữ liệu
                }
            }
        });

        btnSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSortOrder(); // Sort by the first column, which is "Họ và tên"
            }
        });

        controlPanel.add(lblSearch);
        controlPanel.add(txtSearch);
        controlPanel.add(btnSearch);
        controlPanel.add(btnStatistic);
        controlPanel.add(btnAdd);
        controlPanel.add(btnEdit);
        controlPanel.add(btnDelete);
        controlPanel.add(btnSort);

        panel.add(controlPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void toggleSortOrder() {
        List<Student> students = studentManager.getStudents();
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (sortAscending) {
                    return s1.getName().compareToIgnoreCase(s2.getName());
                } else {
                    return s2.getName().compareToIgnoreCase(s1.getName());
                }
            }
        });
        sortAscending = !sortAscending;
        updateTable();
    }

    private void searchByStudent(String name) {
        List<Student> results = studentManager.searchByName(name);
        if (!results.isEmpty()) {
            StudentResultDialog resultDialog = new StudentResultDialog(this, "Kết quả tìm kiếm", results);
            resultDialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy tên " + name, "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void statisticStudentByExamResult() {
        Map<String, Integer> statistics = studentManager.statisticStudentByExamResult();
        StringBuilder message = new StringBuilder("Thống kê số lượng học viên theo kết quả thi:\n");
        for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
            message.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString(), "Thống kê", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Student student : studentManager.getStudents()) {
            Object[] row = {student.getName(), student.getCccd(), student.getRegisteredLicenseType(), dateFormat.format(student.getExamDate()), student.getResult()};
            tableModel.addRow(row);
        }
    }

    private void addStudent() {
        StudentDialog dialog = new StudentDialog(this, "Thêm học viên mới", null);
        dialog.setVisible(true);
        Student newStudent = dialog.getStudent();
        if (newStudent != null) {
            // Kiểm tra xem CCCD đã tồn tại trong danh sách học viên chưa
            if (isDuplicateCCCD(newStudent.getCccd())) {
                JOptionPane.showMessageDialog(this, "CCCD đã tồn tại. Vui lòng nhập CCCD khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                // Kiểm tra xem học viên đã được thêm vào một kỳ thi cụ thể hay không
                if (!isExamExist(newStudent.getExamDate(), newStudent.getRegisteredLicenseType())) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy kỳ thi vào ngày " + dateFormat.format(newStudent.getExamDate()) + " và loại bằng " + newStudent.getRegisteredLicenseType(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Nếu không có lỗi, thêm học viên vào danh sách và cập nhật bảng
                    studentManager.addStudent(newStudent);
                    updateTable();
                }
            }
        }
    }

    // Kiểm tra xem kỳ thi có tồn tại với ngày và loại bằng đã cho hay không
    private boolean isExamExist(Date date, String licenseType) {
        List<Exam> exams = examManager.searchByDateAndLicenseType(dateFormat.format(date), licenseType);
        return !exams.isEmpty();
    }

    private boolean isDuplicateCCCD(String cccd) {
        for (Student student : studentManager.getStudents()) {
            if (student.getCccd().equals(cccd)) {
                return true;
            }
        }
        return false;
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            Student existingStudent = studentManager.getStudents().get(selectedRow);
            StudentDialog dialog = new StudentDialog(this, "Sửa học viên", existingStudent);
            dialog.setVisible(true);
            Student updatedStudent = dialog.getStudent();
            if (updatedStudent != null) {
                // Kiểm tra xem CCCD có trùng với học viên khác không
                boolean isDuplicateCCCD = false;
                for (int i = 0; i < studentManager.getStudents().size(); i++) {
                    if (i != selectedRow && studentManager.getStudents().get(i).getCccd().equals(updatedStudent.getCccd())) {
                        isDuplicateCCCD = true;
                        break;
                    }
                }
                if (isDuplicateCCCD) {
                    JOptionPane.showMessageDialog(this, "CCCD đã tồn tại. Vui lòng nhập CCCD khác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Kiểm tra xem kỳ thi có tồn tại với ngày và loại bằng đã cho hay không
                    if (!isExamExist(updatedStudent.getExamDate(), updatedStudent.getRegisteredLicenseType())) {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy kỳ thi vào ngày " + dateFormat.format(updatedStudent.getExamDate()) + " và loại bằng " + updatedStudent.getRegisteredLicenseType(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Nếu không có lỗi, cập nhật học viên và bảng
                        studentManager.updateStudent(selectedRow, updatedStudent);
                        updateTable();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học viên để sửa");
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            studentManager.deleteStudent(selectedRow);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học viên để xóa");
        }
    }
}
