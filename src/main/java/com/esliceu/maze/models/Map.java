package com.esliceu.maze.models;

import java.util.List;



public class Map {
    private Long id;
    private String name;
    private Long initialRoomId;
    private Long finalRoomId;
    private List<Room> rooms;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setInitialRoomId(Long initialRoomId) {
        this.initialRoomId = initialRoomId;
    }

    public Long getInitialRoomId() {
        return initialRoomId;
    }

    public void setFinalRoomId(Long finalRoomId) {
        this.finalRoomId = finalRoomId;
    }

    public Long getFinalRoomId() {
        return finalRoomId;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
