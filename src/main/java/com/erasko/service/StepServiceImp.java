package com.erasko.service;

import com.erasko.model.Player;
import com.erasko.model.Step;
import com.erasko.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Предполагается, что будет интерфейс StepService
@Service
public class StepServiceImp {

    StepRepository stepRepository;

    @Autowired
    public StepServiceImp(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public void addStep(Step step) {
        stepRepository.addStep(step);
    }

    public void clear() {
        stepRepository.clear();
    }

    public Step getLastStep() {
        return stepRepository.getLastStep();
    }
    public int getStepsSize() {
        return stepRepository.getSize();
    }
}
