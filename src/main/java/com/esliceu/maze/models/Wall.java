package com.esliceu.maze.models;

public class Wall {
    private Long id;
    private String dir;
    private Long roomId;
    private Long doorId;
    private Door door;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setDoorId(Long doorId) {
        this.doorId = doorId;
    }

    public Long getDoorId() {
        return doorId;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return door;
    }
}
