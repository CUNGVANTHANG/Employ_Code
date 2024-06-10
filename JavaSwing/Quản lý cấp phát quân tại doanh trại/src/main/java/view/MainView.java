package view;

import controller.DistributionController;
import controller.EquipmentController;
import controller.SoldierController;
import controller.UnitController;
import func.DistributionManager;
import func.EquipmentManager;
import func.SoldierManager;
import func.UnitManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JTabbedPane tabbedPane;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private DistributionController distributionController;
    private EquipmentController equipmentController;
    private SoldierController soldierController;
    private UnitController unitController;

    public MainView() {
        setTitle("Military Camp Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        DistributionView distributionView = new DistributionView();
        EquipmentView equipmentView = new EquipmentView();
        SoldierView soldierView = new SoldierView();
        UnitView unitView = new UnitView();

        distributionController = new DistributionController(new DistributionManager(), distributionView);
        equipmentController = new EquipmentController(new EquipmentManager(), equipmentView);
        soldierController = new SoldierController(new SoldierManager(), soldierView);
        unitController = new UnitController(new UnitManager(), unitView);

        addTab("Distributions", distributionView);
        addTab("Equipments", equipmentView);
        addTab("Soldier", soldierView);
        addTab("Unit", unitView);

        getContentPane().add(tabbedPane);

        // Thêm các button vào MainView
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton statsButton = new JButton("Statistics");
        statsPanel.add(statsButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchPanel);
        buttonPanel.add(statsPanel);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện khi chuyển tab
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Lấy index của tab được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Hiển thị hoặc ẩn các button tùy thuộc vào tab được chọn
                switch (selectedIndex) {
                    case 0: // Distributions tab
                        distributionController.loadData();
                        break;
                    case 1: // Equipments tab
                        equipmentController.loadData();
                        break;
                    case 2: // Soldier tab
                        soldierController.loadData();
                        break;
                    case 3: // Unit tab
                        unitController.loadData();
                        break;
                    default:
                        break;
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy index của tab được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Hiển thị dialog thêm dữ liệu tùy thuộc vào tab được chọn
                switch (selectedIndex) {
                    case 0: // Distributions tab
                        distributionView.showAddDistributionDialog(distributionController);
                        break;
                    case 1: // Equipments tab
                        equipmentView.showAddEquipmentDialog(equipmentController);
                        break;
                    case 2: // Soldier tab
                        soldierView.showAddSoldierDialog(soldierController);
                        break;
                    case 3: // Unit tab
                        unitView.showAddUnitDialog(unitController);
                        break;
                    default:
                        break;
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy index của tab được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Hiển thị dialog thêm dữ liệu tùy thuộc vào tab được chọn
                switch (selectedIndex) {
                    case 0: // Distributions tab
                        distributionView.showEditDistributionDialog(distributionController);
                        break;
                    case 1: // Equipments tab
                        equipmentView.showEditEquipmentDialog(equipmentController);
                        break;
                    case 2: // Soldier tab
                        soldierView.showEditSoldierDialog(soldierController);
                        break;
                    case 3: // Unit tab
                        unitView.showEditUnitDialog(unitController);
                        break;
                    default:
                        break;
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy index của tab được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();
                // Hiển thị dialog thêm dữ liệu tùy thuộc vào tab được chọn
                switch (selectedIndex) {
                    case 0: // Distributions tab
                        distributionView.showDeleteDistributionDialog(distributionController);
                        break;
                    case 1: // Equipments tab
                        equipmentView.showDeleteEquipmentDialog(equipmentController);
                        break;
                    case 2: // Soldier tab
                        soldierView.showDeleteSoldierDialog(soldierController);
                        break;
                    case 3: // Unit tab
                        unitView.showDeleteUnitDialog(unitController);
                        break;
                    default:
                        break;
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                int selectedIndex = tabbedPane.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: // Distributions tab
                        distributionView.searchByDate(keyword);
                        searchField.setText("");
                        break;
                    case 1: // Equipments tab
                        equipmentView.searchByName(keyword);
                        searchField.setText("");
                        break;
                    case 2: // Soldier tab
                        soldierView.searchByName(keyword);
                        searchField.setText("");
                        break;
                    case 3: // Unit tab
                        unitView.searchByName(keyword);
                        searchField.setText("");
                        break;
                    default:
                        break;
                }
            }
        });

        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: // Distributions tab
                        distributionView.showStatistics();
                        break;
                    case 1: // Equipments tab
                        equipmentView.showStatistics();
                        break;
                    case 2: // Soldier tab
                        soldierView.showStatistics();
                        break;
                    case 3: // Unit tab
                        unitView.showStatistics();
                        break;
                    default:
                        break;
                }
            }
        });


    }

    private void addTab(String title, JPanel panel) {
        tabbedPane.addTab(title, panel);
    }

    public void display() {
        distributionController.loadData();
        setVisible(true);
    }
}