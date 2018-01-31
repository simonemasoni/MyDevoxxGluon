package com.devoxx.model;

import javafx.scene.image.Image;

public class Leaderboard {

    private String name;
    private Image image;
    private Integer points;

    public Leaderboard(String name, Image image, Integer points) {
        this.name = name;
        this.image = image;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Integer getPoints() {
        return points;
    }
}
