package com.erasko.model;
import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String name;
    private int winsCount;

    public Player() {
    }

    public Player(Integer id, String name, int winsCount) {
        this.id = id;
        this.name = name;
        this.winsCount = winsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWinsCount() {
        return winsCount;
    }

    public void setWinsCount() {
        winsCount++;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", winsCount=" + winsCount +
                '}';
    }
}