/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author w.fahed
 */
public class AvailableHotelBean {

    private String provider;
    private String hotelName;
    private double fare;
    private String amenities ;
    private String rate;
    private double price;
    private double discount;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

   

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "AvailableHotelBean{" + "provider=" + provider + ", hotelName=" + hotelName + ", fare=" + fare + ", amenities=" + amenities + ", rate=" + rate + ", price=" + price + ", discount=" + discount + '}';
    }

}
