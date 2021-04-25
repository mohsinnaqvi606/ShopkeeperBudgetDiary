package com.naqvi.shopkeeperbudgetdiary.Models;

public class SellProduct {
    public int ID;
    public String ProductID;
    public String BuyerName;
    public String BuyerNumber;
    public String SellingPrice;
    public String Quantity;
    public String Margin;
    public String Date;
    public String Time;
    public String Image;

    public SellProduct(int ID, String productID, String buyerName, String buyerNumber, String sellingPrice, String quantity, String margin, String date, String time, String image) {
        this.ID = ID;
        ProductID = productID;
        BuyerName = buyerName;
        BuyerNumber = buyerNumber;
        SellingPrice = sellingPrice;
        Quantity = quantity;
        Margin = margin;
        Date = date;
        Time = time;
        Image = image;
    }

    public SellProduct() {
    }
}
