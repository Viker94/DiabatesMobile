package com.utp.projekt.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marcin on 26.11.2016.
 */

public class Products implements Parcelable{
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

    public Products(Parcel parcel) {
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.potassium = parcel.readDouble();
        this.water = parcel.readDouble();
        this.sodium = parcel.readDouble();
        this.category = parcel.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeDouble(potassium);
        parcel.writeDouble(water);
        parcel.writeDouble(sodium);
        parcel.writeInt(category);
    }

    public static final Parcelable.Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel parcel) {
            return new Products(parcel);
        }

        @Override
        public Products[] newArray(int i) {
            return new Products[i];
        }
    };

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", potassium=" + potassium +
                ", water=" + water +
                ", sodium=" + sodium +
                ", category=" + category +
                '}';
    }
}
