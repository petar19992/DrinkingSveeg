package company.drink.com.drinkingsveeg.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by petar on 11.10.16..
 */

public class SharedPreferenceManager {

    public static final String TOKEN="tokenValue";
    private static SharedPreferenceManager instance;

    public static SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    private SharedPreferenceManager(Context context) {
        mContext = context;
        preferences = context.getSharedPreferences(mContext.getPackageName(), Context.MODE_APPEND);
    }

    static SharedPreferences preferences;
    Context mContext;

    public void setInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        if (preferences.contains(key))
            return preferences.getInt(key, defaultValue);
        else return defaultValue;
    }

    public void setString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }
    public String getString(String key, String defaultValue) {
        if (preferences.contains(key))
            return preferences.getString(key, defaultValue);
        else return defaultValue;
    }
}
