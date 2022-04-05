package com.erasko.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<CurrentPlayer> currentPlayers;

    @ElementCollection
    private List<Step> steps;

    @Column(length = 1000)
    private Result result;

    public Game() {
    }

    public Game(Long id,
                ArrayList<CurrentPlayer> currentPlayers,
                ArrayList<Step> steps,
                Result result) {
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.steps = steps;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CurrentPlayer> getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(List<CurrentPlayer> currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

