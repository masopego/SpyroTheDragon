package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_SHOW_GUIDE = "show_guide";

    private SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setShowGuide(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SHOW_GUIDE, value);
        editor.apply();
    }

    public boolean isShowTheGuide() {
        return sharedPreferences.getBoolean(KEY_SHOW_GUIDE, true);
    }
}
