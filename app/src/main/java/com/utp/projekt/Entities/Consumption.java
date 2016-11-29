package com.utp.projekt.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ninjo on 28.11.2016.
 */

public class Consumption implements Parcelable {
    private Long id;
    private Products product;
    private Date date;
    private int amount;


    public Consumption(Long id, Products product, Date date, int amount) {
        this.id = id;
        this.product = product;
        this.date = date;
        this.amount = amount;
    }

    public Consumption(Parcel parcel)
    {
        this.id = parcel.readLong();
        this.product = parcel.readParcelable(Products.class.getClassLoader());
        this.date = (Date) parcel.readSerializable();
        this.amount = parcel.readInt();

    }

    public static final Creator<Consumption> CREATOR = new Creator<Consumption>() {
        @Override
        public Consumption createFromParcel(Parcel in) {
            return new Consumption(in);
        }

        @Override
        public Consumption[] newArray(int size) {
            return new Consumption[size];
        }
    };

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeParcelable(product,i);
        parcel.writeSerializable(date);
        parcel.writeInt(amount);
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "id=" + id +
                ", product=" + product +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}