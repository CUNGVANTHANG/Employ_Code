package view;

import controller.PrisonController;
import entity.Prison;
import entity.Prisoner;
import entity.Visitor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private PrisonController controller;
    private JComboBox<Prison> prisonComboBox;
    private JComboBox<String> prisonerComboBox;
    private JTable prisonerTable;
    private JTable visitorTable;
    private JTable prisonTable;
    private DefaultTableModel prisonerTableModel;
    private DefaultTableModel visitorTableModel;
    private DefaultTableModel prisonTableModel;

    public MainFrame() {
        controller = new PrisonController();

        setTitle("Quản lý nhà tù");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Prisoner Panel
        JPanel prisonerPanel = new JPanel(new BorderLayout());
        prisonComboBox = new JComboBox<>();
        prisonerComboBox = new JComboBox<>();
        List<Prison> prisons = controller.getAllPrisons();
        for (Prison prison : prisons) {
            prisonComboBox.addItem(prison);
        }

        prisonComboBox.addActionListener(e -> loadPrisonerData());
        prisonerPanel.add(prisonComboBox, BorderLayout.NORTH);

        prisonerTableModel = new DefaultTableModel(new Object[]{"ID", "Tên", "Tuổi", "Tội danh", "Năm kết án", "Nhà tù"}, 0);
        prisonerTable = new JTable(prisonerTableModel);
        prisonerTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                loadVisitorData();
            }
        });
        prisonerPanel.add(new JScrollPane(prisonerTable), BorderLayout.CENTER);

        JButton statisticsButton = new JButton("Thống kê");
        statisticsButton.addActionListener(e -> generateStatistics());

        JButton addPrisonerButton = new JButton("Thêm");
        addPrisonerButton.addActionListener(e -> showAddPrisonerDialog());

        JButton editPrisonerButton = new JButton("Sửa");
        editPrisonerButton.addActionListener(e -> editPrisoner());

        JButton deletePrisonerButton = new JButton("Xóa");
        deletePrisonerButton.addActionListener(e -> deletePrisoner());

        JTextField searchPrisonerField = new JTextField(20);
        JButton searchPrisonerButton = new JButton("Tìm kiếm");
        searchPrisonerButton.addActionListener(e -> {
            String searchTerm = searchPrisonerField.getText();
            List<Prisoner> searchResult = searchPrisoners(searchTerm);
            displayPrisonerSearchResult(searchResult);
        });

        JButton showAllPrisonersButton = new JButton("Tất cả");
        showAllPrisonersButton.addActionListener(e -> loadAllPrisoners());

        JLabel searchPrisonerLabel = new JLabel("Tìm kiếm");

        JPanel prisonerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prisonerButtonPanel.add(statisticsButton);
        prisonerButtonPanel.add(showAllPrisonersButton);
        prisonerButtonPanel.add(searchPrisonerLabel);
        prisonerButtonPanel.add(searchPrisonerField);
        prisonerButtonPanel.add(searchPrisonerButton);
        prisonerButtonPanel.add(addPrisonerButton);
        prisonerButtonPanel.add(editPrisonerButton);
        prisonerButtonPanel.add(deletePrisonerButton);

        prisonerPanel.add(prisonerButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Tù nhân", prisonerPanel);

        // Visitor Panel
        JPanel visitorPanel = new JPanel(new BorderLayout());
        visitorTableModel = new DefaultTableModel(new Object[]{"ID", "Tên", "Mối quan hệ", "ID Tù nhân"}, 0);
        visitorTable = new JTable(visitorTableModel);
        visitorPanel.add(prisonerComboBox, BorderLayout.NORTH);
        visitorPanel.add(new JScrollPane(visitorTable), BorderLayout.CENTER);

        JButton addVisitorButton = new JButton("Thêm");
        addVisitorButton.addActionListener(e -> showAddVisitorDialog());

        JButton editVisitorButton = new JButton("Sửa");
        editVisitorButton.addActionListener(e -> editVisitor());

        JButton deleteVisitorButton = new JButton("Xóa");
        deleteVisitorButton.addActionListener(e -> deleteVisitor());

        JTextField searchVisitorField = new JTextField(20);
        JButton searchVisitorButton = new JButton("Tìm kiếm");
        searchVisitorButton.addActionListener(e -> {
            String searchTerm = searchVisitorField.getText();
            List<Visitor> searchResult = searchVisitors(searchTerm);
            displayVisitorSearchResult(searchResult);
        });

        JButton showAllVisitorsButton = new JButton("Tất cả");
        showAllVisitorsButton.addActionListener(e -> loadAllVisitors());

        JLabel searchVisitorLabel = new JLabel("Tìm kiếm");

        JPanel visitorButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        visitorButtonPanel.add(showAllVisitorsButton);
        visitorButtonPanel.add(searchVisitorLabel);
        visitorButtonPanel.add(searchVisitorField);
        visitorButtonPanel.add(searchVisitorButton);
        visitorButtonPanel.add(addVisitorButton);
        visitorButtonPanel.add(editVisitorButton);
        visitorButtonPanel.add(deleteVisitorButton);

        visitorPanel.add(visitorButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Người thăm", visitorPanel);

        // Prison Panel
        JPanel prisonPanel = new JPanel(new BorderLayout());
        prisonTableModel = new DefaultTableModel(new Object[]{"Tên", "Địa điểm", "Sức chứa"}, 0);
        prisonTable = new JTable(prisonTableModel);
        prisonPanel.add(new JScrollPane(prisonTable), BorderLayout.CENTER);
        loadPrisonData();

        JButton addPrisonButton = new JButton("Thêm");
        addPrisonButton.addActionListener(e -> showAddPrisonDialog());

        JButton editPrisonButton = new JButton("Sửa");
        editPrisonButton.addActionListener(e -> editPrison());

        JButton deletePrisonButton = new JButton("Xóa");
        deletePrisonButton.addActionListener(e -> deletePrison());

        JTextField searchPrisonField = new JTextField(20);
        JButton searchPrisonButton = new JButton("Tìm kiếm");
        searchPrisonButton.addActionListener(e -> {
            String searchTerm = searchPrisonField.getText();
            List<Prison> searchResult = searchPrisons(searchTerm);
            displayPrisonSearchResult(searchResult);
        });

        JButton showAllPrisonsButton = new JButton("Tất cả");
        showAllPrisonsButton.addActionListener(e -> loadAllPrisons());

        JLabel searchPrisonLabel = new JLabel("Tìm kiếm");

        JPanel prisonButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        prisonButtonPanel.add(showAllPrisonsButton);
        prisonButtonPanel.add(searchPrisonLabel);
        prisonButtonPanel.add(searchPrisonField);
        prisonButtonPanel.add(searchPrisonButton);
        prisonButtonPanel.add(addPrisonButton);
        prisonButtonPanel.add(editPrisonButton);
        prisonButtonPanel.add(deletePrisonButton);

        prisonPanel.add(prisonButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Nhà tù", prisonPanel);

        add(tabbedPane);
    }

    private void generateStatistics() {
        // Tạo một cửa sổ mới để hiển thị thống kê
        JFrame statisticsFrame = new JFrame("Thống kê");
        statisticsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statisticsFrame.setSize(400, 300);
        statisticsFrame.setLocationRelativeTo(this); // Hiển thị cửa sổ ở giữa MainFrame

        // Tạo một JTextArea để hiển thị thông tin thống kê
        JTextArea statisticsTextArea = new JTextArea();
        statisticsTextArea.setEditable(false); // Không cho phép chỉnh sửa nội dung

        // Gọi các phương thức tạo thống kê và hiển thị kết quả trong JTextArea
        String statisticsResult = generatePrisonerStatistics() + "\n\n" + generateVisitorStatistics() + "\n\n" + generatePrisonStatistics();
        statisticsTextArea.setText(statisticsResult);

        // Thêm JTextArea vào JScrollPane và đưa vào cửa sổ
        JScrollPane scrollPane = new JScrollPane(statisticsTextArea);
        statisticsFrame.add(scrollPane);

        // Hiển thị cửa sổ
        statisticsFrame.setVisible(true);
    }

    // Phương thức tạo thống kê cho phạm nhân
    private String generatePrisonerStatistics() {
        List<Prisoner> allPrisoners = controller.getAllPrisoners();
        int totalPrisoners = allPrisoners.size();
        int totalAges = allPrisoners.stream().mapToInt(Prisoner::getAge).sum();
        double averageAge = totalAges / (double) totalPrisoners;

        return "   Thống kê Tù nhân:\n        - Tổng số tù nhân: " + totalPrisoners + "\n        - Tuổi trung bình: " + String.format("%.2f", averageAge);
    }

    // Phương thức tạo thống kê cho người thăm
    private String generateVisitorStatistics() {
        List<Visitor> allVisitors = controller.getAllVisitors();
        int totalVisitors = allVisitors.size();

        return "   Thống kê Người thăm:\n        - Tổng số người thăm: " + totalVisitors;
    }

    // Phương thức tạo thống kê cho nhà tù
    private String generatePrisonStatistics() {
        List<Prison> allPrisons = controller.getAllPrisons();
        int totalPrisons = allPrisons.size();

        return "   Thống kê Nhà tù:\n        - Tổng số nhà tù: " + totalPrisons;
    }

    private void loadAllPrisoners() {
        prisonerTableModel.setRowCount(0);
        List<Prisoner> prisoners = controller.getAllPrisoners();
        for (Prisoner prisoner : prisoners) {
            prisonerTableModel.addRow(new Object[]{prisoner.getId(), prisoner.getName(), prisoner.getAge(), prisoner.getCrime(), prisoner.getSentenceYears(), prisoner.getPrison()});
        }
    }

    // Method to load all visitors
    private void loadAllVisitors() {
        prisonerComboBox.removeAllItems();
        prisonerComboBox.addItem("All");
        visitorTableModel.setRowCount(0);
        List<Visitor> visitors = controller.getAllVisitors();
        for (Visitor visitor : visitors) {
            visitorTableModel.addRow(new Object[]{visitor.getId(), visitor.getName(), visitor.getRelationship(), visitor.getPrisonerId()});
        }
    }

    // Method to load all prisons
    private void loadAllPrisons() {
        prisonTableModel.setRowCount(0);
        List<Prison> prisons = controller.getAllPrisons();
        for (Prison prison : prisons) {
            prisonTableModel.addRow(new Object[]{prison.getName(), prison.getLocation(), prison.getCapacity()});
        }
    }

    private void displayPrisonerSearchResult(List<Prisoner> searchResult) {
        // Tạo một cửa sổ mới để hiển thị kết quả tìm kiếm
        if (searchResult != null) {
            JFrame searchResultFrame = new JFrame("Kết quả tìm kiếm tù nhân");
            searchResultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            searchResultFrame.setSize(600, 400);
            searchResultFrame.setLocationRelativeTo(this); // Hiển thị cửa sổ ở giữa MainFrame

            // Tạo một bảng để hiển thị kết quả tìm kiếm
            DefaultTableModel searchResultTableModel = new DefaultTableModel(new Object[]{"ID", "Tên", "Tuổi", "Tội danh", "Năm kết án", "Nhà tù"}, 0);
            JTable searchResultTable = new JTable(searchResultTableModel);

            // Thêm dữ liệu từ kết quả tìm kiếm vào bảng
            for (Prisoner prisoner : searchResult) {
                searchResultTableModel.addRow(new Object[]{prisoner.getId(), prisoner.getName(), prisoner.getAge(), prisoner.getCrime(), prisoner.getSentenceYears(), prisoner.getPrison()});
            }

            // Thêm bảng vào JScrollPane và đưa vào cửa sổ
            JScrollPane scrollPane = new JScrollPane(searchResultTable);
            searchResultFrame.add(scrollPane);

            // Hiển thị cửa sổ
            searchResultFrame.setVisible(true);
        }
    }

    private void displayVisitorSearchResult(List<Visitor> searchResult) {
        // Tạo một cửa sổ mới để hiển thị kết quả tìm kiếm
        if (searchResult != null) {
            JFrame searchResultFrame = new JFrame("Kết quả tìm kiếm người thăm");
            searchResultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            searchResultFrame.setSize(600, 400);
            searchResultFrame.setLocationRelativeTo(this); // Hiển thị cửa sổ ở giữa MainFrame

            // Tạo một bảng để hiển thị kết quả tìm kiếm
            DefaultTableModel searchResultTableModel = new DefaultTableModel(new Object[]{"ID", "Tên", "Mối quan hệ", "ID Tù nhân"}, 0);
            JTable searchResultTable = new JTable(searchResultTableModel);

            // Thêm dữ liệu từ kết quả tìm kiếm vào bảng
            for (Visitor visitor : searchResult) {
                searchResultTableModel.addRow(new Object[]{visitor.getId(), visitor.getName(), visitor.getRelationship(), visitor.getPrisonerId()});
            }

            // Thêm bảng vào JScrollPane và đưa vào cửa sổ
            JScrollPane scrollPane = new JScrollPane(searchResultTable);
            searchResultFrame.add(scrollPane);

            // Hiển thị cửa sổ
            searchResultFrame.setVisible(true);
        }
    }

    private void displayPrisonSearchResult(List<Prison> searchResult) {
        // Tạo một cửa sổ mới để hiển thị kết quả tìm kiếm
        JFrame searchResultFrame = new JFrame("Kết quả tìm kiếm nhà tù");
        searchResultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchResultFrame.setSize(600, 400);
        searchResultFrame.setLocationRelativeTo(this); // Hiển thị cửa sổ ở giữa MainFrame

        // Tạo một bảng để hiển thị kết quả tìm kiếm
        DefaultTableModel searchResultTableModel = new DefaultTableModel(new Object[]{"Tên", "Địa điểm", "Sức chứa"}, 0);
        JTable searchResultTable = new JTable(searchResultTableModel);

        // Thêm dữ liệu từ kết quả tìm kiếm vào bảng
        for (Prison prison : searchResult) {
            searchResultTableModel.addRow(new Object[]{prison.getName(), prison.getLocation(), prison.getCapacity()});
        }

        // Thêm bảng vào JScrollPane và đưa vào cửa sổ
        JScrollPane scrollPane = new JScrollPane(searchResultTable);
        searchResultFrame.add(scrollPane);

        // Hiển thị cửa sổ
        searchResultFrame.setVisible(true);
    }

    private void loadPrisonerData() {
        prisonerTableModel.setRowCount(0);
        Prison selectedPrison = (Prison) prisonComboBox.getSelectedItem();
        if (selectedPrison != null) {
            List<Prisoner> prisoners = controller.getPrisonersByPrison(selectedPrison.getName());
            for (Prisoner prisoner : prisoners) {
                prisonerTableModel.addRow(new Object[]{prisoner.getId(), prisoner.getName(), prisoner.getAge(), prisoner.getCrime(), prisoner.getSentenceYears(), prisoner.getPrison()});
            }
        }
    }

    private void loadVisitorData() {
        visitorTableModel.setRowCount(0);
        prisonerComboBox.removeAllItems();
        int selectedRow = prisonerTable.getSelectedRow();
        if (selectedRow != -1) {
            String prisonerId = (String) prisonerTableModel.getValueAt(selectedRow, 0);
            Prisoner selectedPrisoner = controller.getPrisonerById(prisonerId);
            List<Visitor> visitors = controller.getVisitorsByPrisoner(prisonerId);
            for (Visitor visitor : visitors) {
                visitorTableModel.addRow(new Object[]{visitor.getId(), visitor.getName(), visitor.getRelationship(), visitor.getPrisonerId()});
            }

            if (selectedPrisoner != null) {
                prisonerComboBox.addItem(selectedPrisoner.getName()); // Add prisoner name to combo box
            }
        }
    }

    private void loadPrisonData() {
        prisonTableModel.setRowCount(0);
        List<Prison> prisons = controller.getAllPrisons();
        for (Prison prison : prisons) {
            prisonTableModel.addRow(new Object[]{prison.getName(), prison.getLocation(), prison.getCapacity()});
        }
    }

    private void showAddPrisonerDialog() {
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(20);
        JTextField ageField = new JTextField(5);
        JTextField crimeField = new JTextField(20);
        JTextField sentenceYearsField = new JTextField(5);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(5, 2));
        dialogPanel.add(new JLabel("ID:"));
        dialogPanel.add(idField);
        dialogPanel.add(new JLabel("Tên:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Tuổi:"));
        dialogPanel.add(ageField);
        dialogPanel.add(new JLabel("Tội danh:"));
        dialogPanel.add(crimeField);
        dialogPanel.add(new JLabel("Năm kết án:"));
        dialogPanel.add(sentenceYearsField);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Thêm tù nhân", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Prison selectedPrison = (Prison) prisonComboBox.getSelectedItem();
            if (selectedPrison != null) {
                String prisonerId = idField.getText();
                if (controller.getPrisonerById(prisonerId) != null) {
                    JOptionPane.showMessageDialog(this, "ID tù nhân đã tồn tại!", "Error", JOptionPane.ERROR_MESSAGE);
                    showAddPrisonerDialog();
                    return;
                }

                Prisoner prisoner = new Prisoner(idField.getText(), nameField.getText(), Integer.parseInt(ageField.getText()), crimeField.getText(), Integer.parseInt(sentenceYearsField.getText()), selectedPrison.getName());
                controller.addPrisoner(prisoner);
                loadPrisonerData();
            }
        }
    }

    private void showAddVisitorDialog() {
        JTextField idField = new JTextField(5);
        JTextField nameField = new JTextField(20);
        JTextField relationshipField = new JTextField(20);
        JTextField prisonerIdField = new JTextField(5);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(4, 2));
        dialogPanel.add(new JLabel("ID:"));
        dialogPanel.add(idField);
        dialogPanel.add(new JLabel("Tên:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Mối quan hệ:"));
        dialogPanel.add(relationshipField);
        dialogPanel.add(new JLabel("ID Tù nhân:"));
        dialogPanel.add(prisonerIdField);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Thêm người thăm", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String visitorId = idField.getText();
            if (controller.getVisitorById(visitorId) != null) {
                JOptionPane.showMessageDialog(this, "ID người thăm đã tồn tại!", "Error", JOptionPane.ERROR_MESSAGE);
                showAddVisitorDialog();
                return;
            }

            Visitor visitor = new Visitor(idField.getText(), nameField.getText(), relationshipField.getText(), prisonerIdField.getText());
            controller.addVisitor(visitor);
            loadVisitorData();
        }
    }

    private void showAddPrisonDialog() {
        JTextField nameField = new JTextField(20);
        JTextField locationField = new JTextField(20);
        JTextField capacityField = new JTextField(5);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(3, 2));
        dialogPanel.add(new JLabel("Tên:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Địa điểm:"));
        dialogPanel.add(locationField);
        dialogPanel.add(new JLabel("Sức chứa:"));
        dialogPanel.add(capacityField);

        int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Thêm", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String prisonName = nameField.getText();
            if (controller.getPrisonByName(prisonName) != null) {
                JOptionPane.showMessageDialog(this, "Tên nhà tù đã tồn tại!", "Error", JOptionPane.ERROR_MESSAGE);
                showAddPrisonDialog();
                return;
            }

            Prison prison = new Prison(nameField.getText(), locationField.getText(), Integer.parseInt(capacityField.getText()));
            controller.addPrison(prison);
            loadPrisonData();
            prisonComboBox.addItem(prison);
        }
    }

    private void deletePrison() {
        int selectedRow = prisonTable.getSelectedRow();
        if (selectedRow != -1) {
            String prisonName = (String) prisonTableModel.getValueAt(selectedRow, 0);
            // Xác nhận xóa nhà tù
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thông tin về nhà tù này không?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Lấy danh sách tù nhân thuộc về nhà tù cần xóa
                List<Prisoner> prisonersToDelete = controller.getPrisonersByPrison(prisonName);
                // Xóa tất cả các tù nhân thuộc về nhà tù đó
                for (Prisoner prisoner : prisonersToDelete) {
                    controller.deletePrisoner(prisoner.getId());
                }

                // Xóa nhà tù
                controller.deletePrison(prisonName);

                prisonComboBox.removeAllItems();
                List<Prison> prisons = controller.getAllPrisons();
                for (Prison prison : prisons) {
                    prisonComboBox.addItem(prison);
                }

                // Cập nhật lại dữ liệu
                loadPrisonData();
            }
        }
    }

    private void deletePrisoner() {
        int selectedRow = prisonerTable.getSelectedRow();
        if (selectedRow != -1) {
            String prisonerId = (String) prisonerTableModel.getValueAt(selectedRow, 0);
            // Xác nhận xóa phạm nhân
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thông tin tù nhân này?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.deletePrisoner(prisonerId);
                loadPrisonerData();
            }
        }
    }

    private void deleteVisitor() {
        int selectedRow = visitorTable.getSelectedRow();
        if (selectedRow != -1) {
            String visitorId = (String) visitorTableModel.getValueAt(selectedRow, 0);
            // Xác nhận xóa người đến thăm
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa thông tin người thăm này?", "Xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.deleteVisitor(visitorId);
                loadVisitorData();
            }
        }
    }

    // Thêm phương thức sửa phạm nhân
    private void editPrisoner() {
        int selectedRow = prisonerTable.getSelectedRow();
        if (selectedRow != -1) {
            String prisonerId = (String) prisonerTableModel.getValueAt(selectedRow, 0);
            Prisoner existingPrisoner = controller.getPrisonerById(prisonerId);
            if (existingPrisoner != null) {
                // Hiển thị cửa sổ chỉnh sửa phạm nhân
                JTextField idField = new JTextField(5);
                idField.setText(existingPrisoner.getId());
                idField.setEditable(false); // Không cho phép chỉnh sửa ID

                JTextField nameField = new JTextField(20);
                nameField.setText(existingPrisoner.getName());

                JTextField ageField = new JTextField(5);
                ageField.setText(String.valueOf(existingPrisoner.getAge()));

                JTextField crimeField = new JTextField(20);
                crimeField.setText(existingPrisoner.getCrime());

                JTextField sentenceYearsField = new JTextField(5);
                sentenceYearsField.setText(String.valueOf(existingPrisoner.getSentenceYears()));

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(5, 2));
                dialogPanel.add(new JLabel("ID:"));
                dialogPanel.add(idField);
                dialogPanel.add(new JLabel("Tên:"));
                dialogPanel.add(nameField);
                dialogPanel.add(new JLabel("Tuổi:"));
                dialogPanel.add(ageField);
                dialogPanel.add(new JLabel("Tội danh:"));
                dialogPanel.add(crimeField);
                dialogPanel.add(new JLabel("Năm kết án:"));
                dialogPanel.add(sentenceYearsField);

                int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Edit Prisoner", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Cập nhật thông tin phạm nhân
                    existingPrisoner.setName(nameField.getText());
                    existingPrisoner.setAge(Integer.parseInt(ageField.getText()));
                    existingPrisoner.setCrime(crimeField.getText());
                    existingPrisoner.setSentenceYears(Integer.parseInt(sentenceYearsField.getText()));
                    controller.updatePrisoner(existingPrisoner);
                    loadPrisonerData();
                }
            }
        }
    }

    // Thêm phương thức sửa người đến thăm
    private void editVisitor() {
        int selectedRow = visitorTable.getSelectedRow();
        if (selectedRow != -1) {
            String visitorId = (String) visitorTableModel.getValueAt(selectedRow, 0);
            Visitor existingVisitor = controller.getVisitorById(visitorId);
            if (existingVisitor != null) {
                // Hiển thị cửa sổ chỉnh sửa người đến thăm
                JTextField idField = new JTextField(5);
                idField.setText(existingVisitor.getId());
                idField.setEditable(false); // Không cho phép chỉnh sửa ID

                JTextField nameField = new JTextField(20);
                nameField.setText(existingVisitor.getName());

                JTextField relationshipField = new JTextField(20);
                relationshipField.setText(existingVisitor.getRelationship());

                JTextField prisonerIdField = new JTextField(5);
                prisonerIdField.setText(existingVisitor.getPrisonerId());

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(4, 2));
                dialogPanel.add(new JLabel("ID:"));
                dialogPanel.add(idField);
                dialogPanel.add(new JLabel("Tên:"));
                dialogPanel.add(nameField);
                dialogPanel.add(new JLabel("Mối quan hệ:"));
                dialogPanel.add(relationshipField);
                dialogPanel.add(new JLabel("ID Tù nhân:"));
                dialogPanel.add(prisonerIdField);

                int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Edit Visitor", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Cập nhật thông tin người đến thăm
                    existingVisitor.setName(nameField.getText());
                    existingVisitor.setRelationship(relationshipField.getText());
                    existingVisitor.setPrisonerId(prisonerIdField.getText());
                    controller.updateVisitor(existingVisitor);
                    loadVisitorData();
                }
            }
        }
    }

    // Thêm phương thức sửa nhà tù
    private void editPrison() {
        int selectedRow = prisonTable.getSelectedRow();
        if (selectedRow != -1) {
            String prisonName = (String) prisonTableModel.getValueAt(selectedRow, 0);
            Prison existingPrison = controller.getPrisonByName(prisonName);
            if (existingPrison != null) {
                // Hiển thị cửa sổ chỉnh sửa nhà tù
                JTextField nameField = new JTextField(20);
                nameField.setText(existingPrison.getName());
                nameField.setEditable(false); // Không cho phép chỉnh sửa tên nhà tù

                JTextField locationField = new JTextField(20);
                locationField.setText(existingPrison.getLocation());

                JTextField capacityField = new JTextField(5);
                capacityField.setText(String.valueOf(existingPrison.getCapacity()));

                JPanel dialogPanel = new JPanel();
                dialogPanel.setLayout(new GridLayout(3, 2));
                dialogPanel.add(new JLabel("Tên:"));
                dialogPanel.add(nameField);
                dialogPanel.add(new JLabel("Địa điểm:"));
                dialogPanel.add(locationField);
                dialogPanel.add(new JLabel("Sức chứa:"));
                dialogPanel.add(capacityField);

                int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Edit Prison", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Cập nhật thông tin nhà tù
                    existingPrison.setLocation(locationField.getText());
                    existingPrison.setCapacity(Integer.parseInt(capacityField.getText()));
                    controller.updatePrison(existingPrison);
                    loadPrisonData();
                }
            }
        }
    }

    public List<Prisoner> searchPrisoners(String searchTerm) {
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập cụm từ tìm kiếm", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        List<Prisoner> allPrisoners = controller.getAllPrisoners(); // Lấy danh sách tất cả tù nhân từ controller
        List<Prisoner> searchResult = new ArrayList<>();

        try {
            Pattern pattern = Pattern.compile("^" + searchTerm.toLowerCase());
            for (Prisoner prisoner : allPrisoners) {
                Matcher matcher = pattern.matcher(prisoner.getName().toLowerCase());
                if (matcher.find()) {
                    searchResult.add(prisoner);
                }
            }
        } catch (PatternSyntaxException e) {
            JOptionPane.showMessageDialog(this, "Invalid regular expression: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (searchResult.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tù nhân", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return searchResult;
    }

    public List<Visitor> searchVisitors(String searchTerm) {
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập cụm từ tìm kiếm", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        List<Visitor> allVisitors = controller.getAllVisitors(); // Lấy danh sách tất cả người đến thăm từ controller
        List<Visitor> searchResult = new ArrayList<>();

        try {
            Pattern pattern = Pattern.compile("^" + searchTerm.toLowerCase());
            for (Visitor visitor : allVisitors) {
                Matcher matcher = pattern.matcher(visitor.getName().toLowerCase());
                if (matcher.find()) {
                    searchResult.add(visitor);
                }
            }
        } catch (PatternSyntaxException e) {
            JOptionPane.showMessageDialog(this, "Invalid regular expression: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (searchResult.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin người thăm", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return searchResult;
    }

    public List<Prison> searchPrisons(String searchTerm) {
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập cụm từ tìm kiếm", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        List<Prison> allPrisons = controller.getAllPrisons(); // Lấy danh sách tất cả nhà tù từ controller
        List<Prison> searchResult = new ArrayList<>();

        try {
            Pattern pattern = Pattern.compile("^" + searchTerm.toLowerCase());
            for (Prison prison : allPrisons) {
                Matcher matcher = pattern.matcher(prison.getName().toLowerCase());
                if (matcher.find()) {
                    searchResult.add(prison);
                }
            }
        } catch (PatternSyntaxException e) {
            JOptionPane.showMessageDialog(this, "Invalid regular expression: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (searchResult.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhà tù", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return searchResult;
    }
}