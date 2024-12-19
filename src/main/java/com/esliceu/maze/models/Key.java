package com.esliceu.maze.models;

public class Key extends Coordinate{
    private Long id;
    private String name;
    private int value;
    private Long roomId;


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

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setCx(int x) {
        super.setCx(x);
    }

    public int getCx() {
        return super.getCx();
    }

    public void setCy(int y) {
        super.setCy(y);
    }

    public int getCy() {
        return super.getCy();
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
