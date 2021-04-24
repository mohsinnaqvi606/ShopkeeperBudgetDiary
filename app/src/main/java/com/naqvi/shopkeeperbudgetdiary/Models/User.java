package com.naqvi.shopkeeperbudgetdiary.Models;

public class User {
    public int ID;
    public String Name;
    public String Email;
    public String Password;

    public User(int ID, String name, String email, String password) {
        this.ID = ID;
        Name = name;
        Email = email;
        Password = password;
    }

    public User(){}
}
