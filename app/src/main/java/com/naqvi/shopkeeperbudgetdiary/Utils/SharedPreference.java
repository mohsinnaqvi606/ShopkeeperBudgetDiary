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


}