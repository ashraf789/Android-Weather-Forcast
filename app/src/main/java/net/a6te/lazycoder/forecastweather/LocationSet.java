package net.a6te.lazycoder.forecastweather;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ASUS on 4/22/2017.
 */

public class LocationSet {
    private static final String CITYNAME = "UserName";
    private static final String MESSAGE = "";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public LocationSet(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("LocationSet", Context.MODE_PRIVATE);
        editor = preferences.edit();

    }
    public void addCity(String cityname){

        editor.putString(CITYNAME,cityname);
        editor.commit();
    }
    public String getCity(){
        return preferences.getString(CITYNAME,MESSAGE);

    }
}
