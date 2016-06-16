package com.feicuiedu.treasure.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class UserPrefs {

    private static final String PREFS_NAME = "user_info";

    private static final String KEY_PHOTO = "key_photo";
    private static final String KEY_TOKENID = "key_tokenid";

    private final SharedPreferences preferences;

    private static UserPrefs userPrefs;

    public static void init(Context context) {
        userPrefs = new UserPrefs(context);
    }

    public static UserPrefs getInstance(){
        return userPrefs;
    }

    private UserPrefs(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setPhoto(String photoUrl) {
        preferences.edit().putString(KEY_PHOTO, photoUrl).apply();
    }

    public String getPhoto() {
        return preferences.getString(KEY_PHOTO, null);
    }

    public void setTokenid(int tokenid) {
        preferences.edit().putInt(KEY_TOKENID, tokenid).apply();
    }

    public int getTokenid() {
        return preferences.getInt(KEY_TOKENID, -1);
    }
}