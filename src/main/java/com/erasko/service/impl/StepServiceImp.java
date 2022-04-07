package com.erasko.service.impl;

import com.erasko.model.Player;
import com.erasko.model.Step;
import com.erasko.repository.StepRepository;
import com.erasko.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepServiceImp implements StepService {

    StepRepository stepRepository;

    @Autowired
    public StepServiceImp(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public void addStep(Step step) {
        stepRepository.save(step);
    }

    @Override
    public void clear() {
        stepRepository.deleteAll();
    }

    @Override
    public List<Step> findAllStep() {
        return stepRepository.getSteps();
    }

    // добавить исключение
    @Override
    public Step getLastStep() {
        return stepRepository.getLastStep();
    }

    @Override
    public int getStepsSize() {
        return stepRepository.getSize();
    }
}