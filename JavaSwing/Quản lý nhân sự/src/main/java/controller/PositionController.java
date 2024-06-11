package controller;

import entity.Position;
import func.PositionManager;
import view.PositionView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PositionController {
    private PositionManager positionManager;
    private PositionView positionView;

    public PositionController(PositionManager positionManager, PositionView positionView) {
        this.positionManager = positionManager;
        this.positionView = positionView;
    }

    public void loadPositions() {
        positionManager.getAllPositions().clear();
        List<Position> positions = positionManager.getAllPositions();

        Collections.sort(positions, new Comparator<Position>() {
            @Override
            public int compare(Position p1, Position p2) {
                return p1.getPositionName().compareTo(p2.getPositionName());
            }
        });

        positionView.displayPositions(positions);
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

    public boolean isPositionIdUnique(String id) {
        for (Position position : positionManager.getAllPositions()) {
            if (position.getPositionID().equals(id)) {
                return false; // ID đã tồn tại
            }
        }
        return true; // ID là duy nhất
    }
}
