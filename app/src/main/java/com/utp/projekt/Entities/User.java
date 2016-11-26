package com.utp.projekt.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marcin on 26.11.2016.
 */

public class User implements Parcelable{
    private Long id;
    private String firstName;
    private String lastName;
    private double potassium;
    private double water;
    private double sodium;

    public User(Long id, String firstName, String lastName, double potassium, double water, double sodium) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.potassium = potassium;
        this.water = water;
        this.sodium = sodium;
    }

    public User(Parcel parcel){
        this.id = parcel.readLong();
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.potassium = parcel.readDouble();
        this.water = parcel.readDouble();
        this.sodium = parcel.readDouble();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeDouble(potassium);
        parcel.writeDouble(water);
        parcel.writeDouble(sodium);
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
}
