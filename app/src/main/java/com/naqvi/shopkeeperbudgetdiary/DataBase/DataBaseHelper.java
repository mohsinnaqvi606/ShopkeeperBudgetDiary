package com.naqvi.shopkeeperbudgetdiary.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.Models.SellProduct;
import com.naqvi.shopkeeperbudgetdiary.Models.User;
import com.naqvi.shopkeeperbudgetdiary.Utils.SharedPreference;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;
    SharedPreference pref;
    public static final String DATABASE_NAME = "CustomCategories.db";
    String Table_Products = "Products";
    String Table_Users = "Users";
    String Table_SellProducts = "SellProducts";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        pref = new SharedPreference(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create  table " + Table_Products + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT,Price TEXT,Quantity TEXT,Date TEXT,Time TEXT,Address TEXT,Lat TEXT,Lng TEXT,Image TEXT,Email Text)";
        db.execSQL(sql);
        sql = "create  table " + Table_Users + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Email TEXT,Password TEXT)";
        db.execSQL(sql);
        sql = "create  table " + Table_SellProducts + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,ProductID TEXT,BuyerName TEXT,BuyerNumber TEXT,SellingPrice TEXT,Date TEXT,Time TEXT,Email TEXT)";
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
        contentValues.put("Date", s.Date);
        contentValues.put("Time", s.Time);
        contentValues.put("Email", pref.get_Email());
        long result = db.insert(Table_SellProducts, null, contentValues);

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
            p.Date = res.getString(4);
            p.Time = res.getString(5);
            p.Address = res.getString(6);
            p.Lat = res.getString(7);
            p.Lng = res.getString(8);
            p.Image = res.getString(9);
            list.add(p);
        }
        res.close();
        db.close();
        return list;
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
            p.Date = res.getString(5);
            p.Time = res.getString(6);
            list.add(p);
        }
        res.close();
        db.close();
        return list;
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
        return res;
    }

//    public void create_Table(String query) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(query);
//        db.close();
//    }
//
//    public ArrayList<String> get_Table_Columns(String Table) {
//        ArrayList<String> list = new ArrayList<>();
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("Select * from " + Table.replace(" ", "_"), null);
//        res.moveToNext();
//
//        for (int i = 0; i < res.getColumnCount(); i++) {
//            list.add(res.getColumnName(i));
//        }
//
//        res.close();
//        db.close();
//        return list;
//    }
//
//    public void delete_Table(String Table_Name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + Table_Name.replace(" ", "_"));
//        db.close();
//    }
//
//    public boolean insert_Data(ArrayList<String> values, String Table_Name) {
//        ArrayList<String> fields = get_Table_Columns(Table_Name);
//        fields.remove(0);
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        for (int i = 0; i < values.size(); i++) {
//            contentValues.put(fields.get(i), values.get(i));
//        }
//
//        long result = db.insert(Table_Name.replace(" ", "_"), null, contentValues);
//
//        db.close();
//        if (result == -1) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public ArrayList<Category> get_Data_For_Recycler(String Tabel_Name) {
//        ArrayList<Category> categories = new ArrayList<>();
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("Select * from " + Tabel_Name.replace(" ", "_"), null);
//
//        while (res.moveToNext()) {
//            Category category = new Category();
//            category.ID = res.getInt(0);
//            category.Title = res.getString(1);
//            categories.add(category);
//        }
//        res.close();
//        db.close();
//        return categories;
//    }
//
//    public int delete_Record(String Tabel_Name, int Id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        int res = db.delete(Tabel_Name.replace(" ", "_"), "ID = ?", new String[]{Id + ""});
//        db.close();
//        return res;
//    }
//
//    public ArrayList<String> get_Record(String Table_Name, int Id) {
//        ArrayList<String> fields = get_Table_Columns(Table_Name);
//        ArrayList<String> values = new ArrayList<>();
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = " Select * from " + Table_Name.replace(" ", "_") + " Where ID = '" + Id + "'";
//        Cursor res = db.rawQuery(query, null);
//        res.moveToNext();
//        for (int i = 0; i < fields.size(); i++) {
//            values.add(res.getString(i));
//        }
//        res.close();
//        db.close();
//        return values;
//    }
//
//    public void update_Record(ArrayList<String> values, String Table_Name, int Id) {
//        ArrayList<String> fields = get_Table_Columns(Table_Name);
//        fields.remove(0);
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        for (int i = 0; i < fields.size(); i++) {
//            contentValues.put(fields.get(i), values.get(i));
//        }
//
//        db.update(Table_Name.replace(" ", "_"), contentValues, "ID = ?", new String[]{String.valueOf(Id)});
//        db.close();
//    }
}
