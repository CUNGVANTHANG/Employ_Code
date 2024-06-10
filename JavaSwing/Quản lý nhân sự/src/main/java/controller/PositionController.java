package controller;

import entity.Position;
import func.PositionManager;
import view.PositionView;

public class PositionController {
    private PositionManager positionManager;
    private PositionView positionView;

    public PositionController(PositionManager positionManager, PositionView positionView) {
        this.positionManager = positionManager;
        this.positionView = positionView;
    }

    public void loadPositions() {
        positionManager.getAllPositions().clear();
        positionView.displayPositions(positionManager.getAllPositions());
    }

    public void addPosition(Position position) {
        positionManager.addPosition(position);
        loadPositions(); // Reload the position list after adding
    }

    public void removePosition(Position position) {
        positionManager.removePosition(position);
        loadPositions(); // Reload the position list after deleting
    }

    public void updatePosition(Position oldPosition, Position newPosition) {
        positionManager.updatePosition(oldPosition, newPosition);
        loadPositions(); // Reload the position list after editing
    }
}
