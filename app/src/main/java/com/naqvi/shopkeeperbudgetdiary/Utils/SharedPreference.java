package com.naqvi.shopkeeperbudgetdiary.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreference {
    SharedPreferences ShredRef;

    public SharedPreference(Context context) {
        ShredRef = context.getSharedPreferences("myRef", Context.MODE_PRIVATE);
    }

    public void save_Email(String Email) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("Email", Email);
        editor.commit();
    }

    public String get_Email() {
        return ShredRef.getString("Email", "No Email Found");
    }


    public void save_Id(String Id) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("Id", Id);
        editor.commit();
    }

    public String get_Id() {
        return ShredRef.getString("Id", "No Id Found");
    }


    public void save_Language(String Language) {
        SharedPreferences.Editor editor = ShredRef.edit();
        editor.putString("Language", Language);
        editor.commit();
    }

    public String get_Language() {
        return ShredRef.getString("Language", "No Language Found");
    }

}