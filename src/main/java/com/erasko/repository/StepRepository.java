package com.erasko.repository;

import com.erasko.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class StepRepository {

    ArrayList<Step> steps;

    public StepRepository() {
    }

    @Autowired
    public StepRepository(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void addStep(Step step) {
        steps.add(step);
        System.out.println(steps);
    }

    public void clear() {
        steps.clear();
    }

    public int getSize() {
        return steps.size();
    }

    public Step getLastStep() {
        return steps.get(getSize()-1);
    }

}
