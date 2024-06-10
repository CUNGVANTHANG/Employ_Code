package controller;

import entity.Unit;
import func.UnitManager;
import view.UnitView;

public class UnitController {
    private UnitManager unitManager;
    private UnitView unitView;
    private String filePath = "src/main/resources/units.xml";

    public UnitController(UnitManager unitManager, UnitView unitView) {
        this.unitManager = unitManager;
        this.unitView = unitView;
    }

    public void loadData() {
        try {
            unitManager.getUnitList().clear();
            unitManager.loadUnitList(filePath);
            unitView.displayUnits(unitManager.getUnitList());
        } catch (Exception e) {
            unitView.displayError("Failed to load unit data: " + e.getMessage());
        }
    }

    public void addUnitToXML(Unit unit) {
        try {
            unitManager.addUnitToXML(unit, filePath);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isUnitIdExists(String id) {
        for (Unit unit : unitManager.getUnitList()) {
            if (unit.getId().equals(id)) {
                return true; // ID đã tồn tại
            }
        }
        return false; // ID không tồn tại
    }

    public void updateUnitInXML(Unit oldUnit, Unit newUnit) {
        try {
            unitManager.updateUnitInXML(oldUnit, newUnit, filePath);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUnitFromXML(Unit unit) {
        try {
            unitManager.deleteUnitFromXML(unit, filePath);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}