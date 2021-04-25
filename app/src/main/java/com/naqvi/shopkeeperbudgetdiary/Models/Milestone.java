package com.naqvi.shopkeeperbudgetdiary.Models;

public class Milestone {
    public int ID;
    public String StartDate;
    public String EndDate;
    public String TotalDays;
    public String TotalPrice;
    public String Percentage;
    public String AchievedPrice;
    public String Status;

    public Milestone(int ID, String startDate, String endDate, String totalDays, String totalPrice, String percentage, String achievedPrice, String status) {
        this.ID = ID;
        StartDate = startDate;
        EndDate = endDate;
        TotalDays = totalDays;
        TotalPrice = totalPrice;
        Percentage = percentage;
        AchievedPrice = achievedPrice;
        Status = status;
    }

    public Milestone() {
    }
}
