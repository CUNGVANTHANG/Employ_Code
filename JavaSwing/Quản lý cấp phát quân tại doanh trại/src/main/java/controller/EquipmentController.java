package controller;

import entity.Equipment;
import func.EquipmentManager;
import view.EquipmentView;

public class EquipmentController {
    private EquipmentManager equipmentManager;
    private EquipmentView equipmentView;
    private String filePath = "src/main/resources/equipments.xml";

    public EquipmentController(EquipmentManager equipmentManager, EquipmentView equipmentView) {
        this.equipmentManager = equipmentManager;
        this.equipmentView = equipmentView;
    }

    public void loadData() {
        try {
            equipmentManager.getEquipmentList().clear();
            equipmentManager.loadEquipmentList(filePath);
            equipmentView.displayEquipments(equipmentManager.getEquipmentList());
        } catch (Exception e) {
            equipmentView.displayError("Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    public void addEquipment(Equipment equipment) {
        try {
            equipmentManager.addEquipmentToXML(equipment, filePath);
            loadData();
        } catch (Exception e) {
            equipmentView.displayError("Error adding equipment: " + e.getMessage());
        }
    }

    public void updateEquipment(Equipment oldEquipment, Equipment updatedEquipment) {
        try {
            equipmentManager.updateEquipmentInXML(oldEquipment, updatedEquipment, filePath);
            loadData();
        } catch (Exception e) {
            equipmentView.displayError("Error updating equipment: " + e.getMessage());
        }
    }

    public void deleteEquipment(Equipment equipment) {
        try {
            equipmentManager.deleteEquipmentFromXML(equipment.getName(), filePath);
            loadData();
        } catch (Exception e) {
            equipmentView.displayError("Error deleting equipment: " + e.getMessage());
        }
    }

}
