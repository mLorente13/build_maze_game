package com.esliceu.maze.models;

import java.time.LocalDateTime;

public class Score {
    private Long position;
    private String username;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String time;
    private Long mapId;
    public Long getPosition() {
        return position;
    }
    public void setPosition(Long position) {
        this.position = position;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime starDate) {
        this.startDate = starDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    public Long getMapId() {
        return mapId;
    }
    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
