package com.erasko.lns2.DTO;

public class StepDTO {

    private String num;
    private String playerId;
    private int coors;

    public StepDTO() {
    }

    public StepDTO(String num, String playerId, int coors) {
        this.num = num;
        this.playerId = playerId;
        this.coors = coors;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getCoors() {
        return coors;
    }

    public void setCoors(int coors) {
        this.coors = coors;
    }
}
