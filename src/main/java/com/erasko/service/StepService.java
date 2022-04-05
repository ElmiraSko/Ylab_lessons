package com.erasko.service;

import com.erasko.model.Player;
import com.erasko.model.Step;
import com.erasko.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Предполагается, что будет интерфейс StepService
@Service
public class StepService {

    StepRepository stepRepository;

    @Autowired
    public StepService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    public void addStep(Step step) {
        stepRepository.save(step);
    }

    public void clear() {
        stepRepository.deleteAll();
    }

    public List<Step> findAllStep() {
        return stepRepository.getSteps();
    }

    // добавить исключение
    public Step getLastStep() {
        return stepRepository.getLastStep();
    }

    public int getStepsSize() {
        return stepRepository.getSize();
    }
}