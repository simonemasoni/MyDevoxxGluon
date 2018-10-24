/**
 * Copyright (c) 2016, Gluon Software
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation and/or other materials provided
 *    with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse
 *    or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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