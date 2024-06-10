package controller;

import entity.Distribution;
import func.DistributionManager;
import view.DistributionView;

public class DistributionController {
    private DistributionManager distributionManager;
    private DistributionView distributionView;
    private String filePath = "src/main/resources/distributions.xml";

    public DistributionController(DistributionManager distributionManager, DistributionView distributionView) {
        this.distributionManager = distributionManager;
        this.distributionView = distributionView;
    }

    public void loadData() {
        try {
            distributionManager.getDistributionList().clear();
            distributionManager.loadDistributionList(filePath);
            distributionView.displayDistributions(distributionManager.getDistributionList());
        } catch (Exception e) {
            distributionView.displayError("Error loading data: " + e.getMessage());
        }
    }

    public void addDistribution(Distribution distribution) {
        try {
            distributionManager.addDistributionToXML(distribution, filePath);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDistribution(Distribution oldDistribution, Distribution newDistribution) {
        try {
            distributionManager.updateDistributionInXML(oldDistribution, newDistribution, filePath);
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDistribution(Distribution distribution) {
        try {
            distributionManager.deleteDistributionFromXML(distribution, filePath);
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
