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
