package com.utp.projekt.Entities;

/**
 * Created by Marcin on 26.11.2016.
 */

public class Products {
    private Long id;
    private String name;
    private Double potassium;
    private Double water;
    private Double sodium;
    private int category;

    public Products(Long id, String name, Double potassium, Double water, Double sodium, int category) {
        this.id = id;
        this.name = name;
        this.potassium = potassium;
        this.water = water;
        this.sodium = sodium;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPotassium() {
        return potassium;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    public Double getWater() {
        return water;
    }

    public void setWater(Double water) {
        this.water = water;
    }

    public Double getSodium() {
        return sodium;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
