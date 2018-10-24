package com.devoxx.views.cell;

import com.devoxx.model.Leaderboard;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class LeaderboardCell extends CharmListCell<Leaderboard> {

    public LeaderboardCell() {
        getStyleClass().add("leader-cell");
    }

    @Override
    public void updateItem(Leaderboard item, boolean empty) {
        setText("");
        if (empty || item == null) {
            setGraphic(null);
        } else {
            HBox hBox = new LeaderboardBox(item);
            setGraphic(hBox);
        }
    }

    private class LeaderboardBox extends HBox {

        LeaderboardBox(Leaderboard leaderboard) {
            getStyleClass().add("box");

            HBox positionBox = new HBox();
            positionBox.getStyleClass().add("position-box");

            Label position = new Label();
            position.getStyleClass().add("position");
            Node graphic = MaterialDesignIcon.STARS.graphic();

            switch (getIndex()) {
                case 0:
                    position.getStyleClass().removeAll("silver", "bronze");
                    position.getStyleClass().add("gold");
                    position.setGraphic(graphic);
                    position.setText("");
                    break;
                case 1:
                    position.getStyleClass().removeAll("gold", "bronze");
                    position.getStyleClass().add("silver");
                    position.setGraphic(graphic);
                    position.setText("");
                    break;
                case 2:
                    position.getStyleClass().removeAll("gold", "silver");
                    position.getStyleClass().add("bronze");
                    position.setGraphic(graphic);
                    position.setText("");
                    break;
                default:
                    position.getStyleClass().removeAll("gold", "silver", "bronze");
                    position.setGraphic(null);
                    position.setText(String.valueOf(getIndex() + 1));
                    break;
            }
            positionBox.getChildren().add(position);

            Avatar avatar = new Avatar();
            avatar.setImage(leaderboard.getImage());

            Label name = new Label(leaderboard.getName());
            name.getStyleClass().add("name");

            Label points = new Label(String.valueOf(leaderboard.getPoints()));
            points.getStyleClass().add("point");

            Region gap = new Region();
            HBox.setHgrow(gap, Priority.ALWAYS);

            getChildren().addAll(positionBox, avatar, name, gap, points);
        }
    }
}