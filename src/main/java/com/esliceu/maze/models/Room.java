package com.esliceu.maze.models;

import java.util.List;


public class Room {
    private Long id;
    private String name;
    boolean hasCoin;
    boolean hasKey;
    private Coin coin;
    private Key key;
    private Long mapId;
    private List<Wall> walls;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean hasCoin() {
        return hasCoin;
    }

    public void setHasCoin(boolean hasCoin) {
        this.hasCoin = hasCoin;
    }

    public Coin getCoin() {
        return coin;
    }
    public void setCoin(Coin coin) {
        this.coin = coin;
    }
    public Key getKey() {
        return key;
    }
    public void setKey(Key key) {
        this.key = key;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public Long getMapId() {
        return mapId;
    }
    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }
    public List<Wall> getWalls() {
        return walls;
    }
    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }
}
