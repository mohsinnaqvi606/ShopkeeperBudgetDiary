package com.naqvi.shopkeeperbudgetdiary.Models;

public class Product {
    public int ID;
    public String Title;
    public String Price;
    public String Quantity;
    public String Date;
    public String Time;
    public String Address;
    public String Lat;
    public String Lng;
    public String Image;

    public Product(int ID, String title, String price, String quantity, String date, String time, String address, String lat, String lng, String image) {
        this.ID = ID;
        Title = title;
        Price = price;
        Quantity = quantity;
        Date = date;
        Time = time;
        Address = address;
        Lat = lat;
        Lng = lng;
        Image = image;
    }

    public Product() {
    }
}
