package com.erasko.lns2.DTO;

import java.util.ArrayList;

public class GameDTO {

    private ArrayList<StepDTO> step;

    public GameDTO() {
    }

    public GameDTO(ArrayList<StepDTO> step) {
        this.step = step;
    }

    public ArrayList<StepDTO> getStep() {
        return step;
    }

    public void setStep(ArrayList<StepDTO> step) {
        this.step = step;
    }
}
