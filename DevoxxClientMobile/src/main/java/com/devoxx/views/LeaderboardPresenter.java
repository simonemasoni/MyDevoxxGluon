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
package com.devoxx.views;

import com.devoxx.DevoxxApplication;
import com.devoxx.DevoxxView;
import com.devoxx.model.Leaderboard;
import com.devoxx.views.cell.LeaderboardCell;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.FXCollections;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import java.util.Comparator;

public class LeaderboardPresenter extends GluonPresenter<DevoxxApplication> {

    @FXML private View leaderboard;
    @FXML private ListView<Leaderboard> leadersList;

    public void initialize() {

        leaderboard.setOnShowing(event -> {
            AppBar appBar = getApp().getAppBar();
            appBar.setTitleText(DevoxxView.LEADERBOARD.getTitle());
            appBar.setNavIcon(getApp().getNavMenuButton());
        });

        leadersList.setCellFactory(param -> new LeaderboardCell());

        Image image = new Image(getClass().getResource("/icon.png").toExternalForm());
        SortedList<Leaderboard> sortedList = new SortedList<>(FXCollections.observableArrayList(
                new Leaderboard("Abhinay", image, 10),
                new Leaderboard("Joeri", image, 20),
                new Leaderboard("Johan", image, 30),
                new Leaderboard("Jose", image, 35),
                new Leaderboard("Jonathan", image, 40),
                new Leaderboard("Eugene", image, 45),
                new Leaderboard("Erwin", image, 50)));
        sortedList.setComparator(Comparator.comparing(Leaderboard::getPoints).reversed());
        leadersList.setItems(sortedList);
    }


}
