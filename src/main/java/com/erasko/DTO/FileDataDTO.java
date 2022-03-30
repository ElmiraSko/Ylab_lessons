package com.erasko.DTO;

import java.util.ArrayList;

public class FileDataDTO {

    ArrayList<String> crPayerList;
    ArrayList<int[][]> plSteps;
    String winnerOrDraw;

    public FileDataDTO() {
    }

    public FileDataDTO(ArrayList<String> crPayerList, ArrayList<int[][]> plSteps, String winnerOrDraw) {
        this.crPayerList = crPayerList;
        this.plSteps = plSteps;
        this.winnerOrDraw = winnerOrDraw;
    }

    public ArrayList<String> getCrPayerList() {
        return crPayerList;
    }

    public void setCrPayerList(ArrayList<String> crPayerList) {
        this.crPayerList = crPayerList;
    }

    public ArrayList<int[][]> getPlSteps() {
        return plSteps;
    }

    public void setPlSteps(ArrayList<int[][]> plSteps) {
        this.plSteps = plSteps;
    }

    public String getWinnerOrDraw() {
        return winnerOrDraw;
    }

    public void setWinnerOrDraw(String winnerOrDraw) {
        this.winnerOrDraw = winnerOrDraw;
    }
}
