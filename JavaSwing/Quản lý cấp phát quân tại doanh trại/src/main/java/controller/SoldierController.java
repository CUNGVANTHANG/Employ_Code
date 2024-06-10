package controller;

import entity.Soldier;
import func.SoldierManager;
import view.SoldierView;

public class SoldierController {
    private SoldierManager soldierManager;
    private SoldierView soldierView;
    private String filePath = "src/main/resources/soldiers.xml";

    public SoldierController(SoldierManager soldierManager, SoldierView soldierView) {
        this.soldierManager = soldierManager;
        this.soldierView = soldierView;
    }

    public void loadData() {
        try {
            soldierManager.getSoldierList().clear();
            soldierManager.loadSoldierList(filePath);
            soldierView.displaySoldiers(soldierManager.getSoldierList());
        } catch (Exception e) {
            soldierView.displayError("Failed to load soldier data: " + e.getMessage());
        }
    }

    public void addSoldier(Soldier soldier) {
        try {
            soldierManager.addSoldierToXML(soldier, filePath);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSoldierIdExists(String id) {
        for (Soldier soldier : soldierManager.getSoldierList()) {
            if (soldier.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void updateSoldier(Soldier oldSoldier, Soldier newSoldier) {
        try {
            soldierManager.updateSoldierInXML(oldSoldier, newSoldier, filePath);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSoldier(Soldier soldier) {
        try {
            soldierManager.deleteSoldierFromXML(soldier, filePath);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}