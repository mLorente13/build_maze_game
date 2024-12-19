package com.esliceu.maze.models;

public class Door {
    private Long id;
    private Long keyId;
    private boolean isOpen;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setIsOpen(boolean open) {
        this.isOpen = open;
    }

    public boolean getIsOpen() {
        return isOpen;
    }
}
