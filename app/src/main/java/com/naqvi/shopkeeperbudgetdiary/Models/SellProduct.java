package com.naqvi.shopkeeperbudgetdiary.Models;

public class SellProduct {
    public int ID;
    public String ProductID;
    public String BuyerName;
    public String BuyerNumber;
    public String SellingPrice;
    public String Date;
    public String Time;

    public SellProduct(int ID, String productID, String buyerName, String buyerNumber, String sellingPrice, String date, String time) {
        this.ID = ID;
        ProductID = productID;
        BuyerName = buyerName;
        BuyerNumber = buyerNumber;
        SellingPrice = sellingPrice;
        Date = date;
        Time = time;
    }

    public SellProduct() {
    }
}
