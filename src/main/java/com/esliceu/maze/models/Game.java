package com.esliceu.maze.models;

import java.time.LocalDateTime;

public class Game {
    private Long id;
    private Long userId;
    private int coins;
    private Long currentRoomId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long mapId;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getCurrentRoomId() {
        return currentRoomId;
    }
    public void setCurrentRoomId(Long currentRoomId) {
        this.currentRoomId = currentRoomId;
    }
    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
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
    public String toString() {
        return "Game [id=" + id + ", userId=" + userId + ", coins=" + coins + ", currentRoomId=" + currentRoomId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}
