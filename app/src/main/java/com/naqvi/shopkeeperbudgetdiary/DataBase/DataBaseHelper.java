package com.naqvi.shopkeeperbudgetdiary.DataBase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.naqvi.shopkeeperbudgetdiary.Activities.Milestone_Detail_Activity;
import com.naqvi.shopkeeperbudgetdiary.Models.Milestone;
import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.Models.SellProduct;
import com.naqvi.shopkeeperbudgetdiary.Models.User;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;
    SharedPreference pref;
    public static final String DATABASE_NAME = "ShookeeperBudgetDiary.db";
    String Table_Products = "Products";
    String Table_Users = "Users";
    String Table_SellProducts = "SellProducts";
    String Table_Milestones = "Milestones";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        pref = new SharedPreference(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create  table " + Table_Products + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT,Price TEXT,Quantity TEXT,PerItemPrice TEXT,Date TEXT,Time TEXT,Address TEXT,Lat TEXT,Lng TEXT,Image TEXT,Email Text)";
        db.execSQL(sql);
        sql = "create  table " + Table_Users + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Email TEXT,Password TEXT)";
        db.execSQL(sql);
        sql = "create  table " + Table_SellProducts + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductID TEXT,BuyerName TEXT,BuyerNumber TEXT,SellingPrice TEXT,Quantity TEXT,Margin TEXT,Date TEXT,Time TEXT,Image TEXT,Email TEXT)";
        db.execSQL(sql);
        sql = "create  table " + Table_Milestones + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,StartDate TEXT,EndDate TEXT,TotalDays TEXT,TotalPrice TEXT,Percentage TEXT,AchievedPrice TEXT,Status TEXT,Email TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS " + Table_Products);
        onCreate(db);
    }

    public boolean insert_Product(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", p.Title);
        contentValues.put("Price", p.Price);
        contentValues.put("Quantity", p.Quantity);
        contentValues.put("PerItemPrice", p.PerItemPrice);
        contentValues.put("Date", p.Date);
        contentValues.put("Time", p.Time);
        contentValues.put("Address", p.Address);
        contentValues.put("Lat", p.Lat);
        contentValues.put("Lng", p.Lng);
        contentValues.put("Image", p.Image);
        contentValues.put("Email", pref.get_Email());
        long result = db.insert(Table_Products, null, contentValues);

        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insert_User(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", u.Name);
        contentValues.put("Email", u.Email);
        contentValues.put("Password", u.Password);
        long result = db.insert(Table_Users, null, contentValues);

        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insert_SellProduct(SellProduct s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ProductID", s.ProductID);
        contentValues.put("BuyerName", s.BuyerName);
        contentValues.put("BuyerNumber", s.BuyerNumber);
        contentValues.put("SellingPrice", s.SellingPrice);
        contentValues.put("Quantity", s.Quantity);
        contentValues.put("Margin", s.Margin);
        contentValues.put("Date", s.Date);
        contentValues.put("Time", s.Time);
        contentValues.put("Image", s.Image);
        contentValues.put("Email", pref.get_Email());
        long result = db.insert(Table_SellProducts, null, contentValues);

        db.close();
        calculate_Milestone();
        if (result == -1) {
            return false;
        } else {
            calculate_Milestone();
            return true;
        }
    }


    public boolean insert_Milestone(Milestone m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("StartDate", m.StartDate);
        contentValues.put("EndDate", m.EndDate);
        contentValues.put("TotalDays", m.TotalDays);
        contentValues.put("TotalPrice", m.TotalPrice);
        contentValues.put("Percentage", m.Percentage);
        contentValues.put("AchievedPrice", m.AchievedPrice);
        contentValues.put("Status", m.Status);
        contentValues.put("Email", pref.get_Email());
        long result = db.insert(Table_Milestones, null, contentValues);

        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean get_User(User u) {
        boolean isExist = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From Users where Email = '" + u.Email + "' and Password = '" + u.Password + "'", null);
        isExist = res.moveToNext();
        res.close();
        db.close();
        calculate_Milestone();
        return isExist;
    }

    public ArrayList<Product> get_Products() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + Table_Products + " where Email = '" + pref.get_Email() + "'", null);
        ArrayList<Product> list = new ArrayList<Product>();
        while (res.moveToNext()) {
            Product p = new Product();
            p.ID = res.getInt(0);
            p.Title = res.getString(1);
            p.Price = res.getString(2);
            p.Quantity = res.getString(3);
            p.PerItemPrice = res.getString(4);
            p.Date = res.getString(5);
            p.Time = res.getString(6);
            p.Address = res.getString(7);
            p.Lat = res.getString(8);
            p.Lng = res.getString(9);
            p.Image = res.getString(10);
            list.add(p);
        }
        res.close();
        db.close();
        return list;
    }


    public Product get_ProductById(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + Table_Products + " where Email = '" + pref.get_Email() + "' and ID = '" + Id + "'", null);
        res.moveToNext();
        Product p = new Product();
        p.ID = res.getInt(0);
        p.Title = res.getString(1);
        p.Price = res.getString(2);
        p.Quantity = res.getString(3);
        p.PerItemPrice = res.getString(4);
        p.Date = res.getString(5);
        p.Time = res.getString(6);
        p.Address = res.getString(7);
        p.Lat = res.getString(8);
        p.Lng = res.getString(9);
        p.Image = res.getString(10);
        res.close();
        db.close();
        return p;
    }

    public ArrayList<Milestone> get_Milestones() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + Table_Milestones + " where Email = '" + pref.get_Email() + "'", null);
        ArrayList<Milestone> list = new ArrayList<Milestone>();
        while (res.moveToNext()) {

            Milestone m = new Milestone();
            m.ID = res.getInt(0);
            m.StartDate = res.getString(1);
            m.EndDate = res.getString(2);
            m.TotalDays = res.getString(3);
            m.TotalPrice = res.getString(4);
            m.Percentage = res.getString(5);
            m.AchievedPrice = res.getString(6);
            m.Status = res.getString(7);
            list.add(m);
        }
        res.close();
        db.close();
        return list;
    }

    public Milestone get_MilestoneById(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + Table_Milestones + " where Email = '" + pref.get_Email() + "' and ID = '" + Id + "'", null);
        res.moveToNext();
        Milestone m = new Milestone();
        m.ID = res.getInt(0);
        m.StartDate = res.getString(1);
        m.EndDate = res.getString(2);
        m.TotalDays = res.getString(3);
        m.TotalPrice = res.getString(4);
        m.Percentage = res.getString(5);
        m.AchievedPrice = res.getString(6);
        m.Status = res.getString(7);
        res.close();
        db.close();
        return m;
    }


    public int update_Milestone(Milestone m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("StartDate", m.StartDate);
        contentValues.put("EndDate", m.EndDate);
        contentValues.put("TotalDays", m.TotalDays);
        contentValues.put("TotalPrice", m.TotalPrice);
        contentValues.put("Percentage", m.Percentage);
        contentValues.put("AchievedPrice", m.AchievedPrice);
        contentValues.put("Status", m.Status);
        contentValues.put("Email", pref.get_Email());
        int isUpdated = db.update(Table_Milestones, contentValues, "ID = ?", new String[]{m.ID + ""});
        db.close();
        return isUpdated;
    }


    public int update_Product(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Title", p.Title);
        contentValues.put("Price", p.Price);
        contentValues.put("Quantity", p.Quantity);
        contentValues.put("PerItemPrice", p.PerItemPrice);
        contentValues.put("Date", p.Date);
        contentValues.put("Time", p.Time);
        contentValues.put("Address", p.Address);
        contentValues.put("Lat", p.Lat);
        contentValues.put("Lng", p.Lng);
        contentValues.put("Image", p.Image);
        contentValues.put("Email", pref.get_Email());

        int isUpdated = db.update(Table_Products, contentValues, "ID = ?", new String[]{p.ID + ""});
        db.close();

        return isUpdated;
    }


    public ArrayList<SellProduct> get_SellProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + Table_SellProducts + " where Email = '" + pref.get_Email() + "'", null);
        ArrayList<SellProduct> list = new ArrayList<SellProduct>();
        while (res.moveToNext()) {
            SellProduct p = new SellProduct();
            p.ID = res.getInt(0);
            p.ProductID = res.getString(1);
            p.BuyerName = res.getString(2);
            p.BuyerNumber = res.getString(3);
            p.SellingPrice = res.getString(4);
            p.Quantity = res.getString(5);
            p.Margin = res.getString(6);
            p.Date = res.getString(7);
            p.Time = res.getString(8);
            p.Image = res.getString(9);
            list.add(p);
        }
        res.close();
        db.close();
        return list;
    }


    public SellProduct get_SellProductById(String Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + Table_SellProducts + " where Email = '" + pref.get_Email() + "' and ID = '" + Id + "'", null);
        res.moveToNext();
        SellProduct p = new SellProduct();
        p.ID = res.getInt(0);
        p.ProductID = res.getString(1);
        p.BuyerName = res.getString(2);
        p.BuyerNumber = res.getString(3);
        p.SellingPrice = res.getString(4);
        p.Quantity = res.getString(5);
        p.Margin = res.getString(6);
        p.Date = res.getString(7);
        p.Time = res.getString(8);
        p.Image = res.getString(9);

        res.close();
        db.close();
        return p;
    }

    public int update_SoldProduct(SellProduct s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ProductID", s.ProductID);
        contentValues.put("BuyerName", s.BuyerName);
        contentValues.put("BuyerNumber", s.BuyerNumber);
        contentValues.put("SellingPrice", s.SellingPrice);
        contentValues.put("Quantity", s.Quantity);
        contentValues.put("Margin", s.Margin);
        contentValues.put("Date", s.Date);
        contentValues.put("Time", s.Time);
        contentValues.put("Image", s.Image);
        contentValues.put("Email", pref.get_Email());

        int isUpdated = db.update(Table_Products, contentValues, "ID = ?", new String[]{s.ID + ""});
        db.close();

        return isUpdated;
    }


    public Integer delete_Product(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(Table_Products, "ID = ?", new String[]{id});

        db.close();
        return res;
    }


    public Integer delete_SellProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(Table_SellProducts, "ID = ?", new String[]{id});
        db.close();
        calculate_Milestone();
        return res;
    }

    public Integer delete_Milestone(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(Table_Milestones, "ID = ?", new String[]{id});
        db.close();
        return res;
    }


    public void calculate_Milestone() {
        ArrayList<Milestone> milestonesList = get_Milestones();
        Milestone m = new Milestone();
        SQLiteDatabase db;
        Cursor res;
        Double margin = 0.0;
        String query;
        for (int i = 0; i < milestonesList.size(); i++) {
            m = milestonesList.get(i);
            db = this.getWritableDatabase();
            query = "Select Sum(Margin) from " + Table_SellProducts + " where Email = '" + pref.get_Email() + "' and Date >= '" + m.StartDate + "' and DATE <= '" + m.EndDate + "'";
            res = db.rawQuery(query, null);
            if (res.moveToNext()) {
                try {
                    margin += Double.parseDouble(res.getString(0));
                    double milestonePrice = Double.parseDouble(m.TotalPrice);
                    if (margin < milestonePrice) {
                        m.AchievedPrice = margin + "";
                        m.Percentage = ((margin * 100) / Double.parseDouble(m.TotalPrice)) + "";
                        m.Status = "Incomplete";
                    } else {
                        m.Status = "Completed";
                        m.Percentage = "100";
                        m.AchievedPrice = m.TotalPrice;
                        //  sendNotification(m);
                        showNotification();
                    }
                    db.close();
                    update_Milestone(m);
                } catch (Exception e) {
                    db.close();
                    Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            } else {
                db.close();
            }
        }
    }

    void sendNotification(Milestone m) {

        Intent intent = new Intent(context, Milestone_Detail_Activity.class);
        intent.putExtra("Id", m.ID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, m.ID + "")
                .setSmallIcon(R.drawable.img4)
                .setContentTitle("Milestone Achived")
                .setContentText("you have achived milestone of " + m.TotalPrice)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(m.ID, builder.build());
    }

    void showNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.img5)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(context, Milestone_Detail_Activity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
