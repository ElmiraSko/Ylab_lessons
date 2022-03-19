package com.erasko.lns2.DTO;

import java.util.ArrayList;

public class GameDTO {

    private ArrayList<StepDTO> steps;

    public GameDTO() {
    }

    public GameDTO(ArrayList<StepDTO> steps) {
        this.steps = steps;
    }

    public ArrayList<StepDTO> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepDTO> steps) {
        this.steps = steps;
    }
}
