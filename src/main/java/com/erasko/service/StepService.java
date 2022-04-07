package com.erasko.service;

import com.erasko.model.Step;

import java.util.List;

public interface StepService {

    void addStep(Step step);
    List<Step> findAllStep();
    Step getLastStep();
    int getStepsSize();
    void clear();
}
