package com.example.testpageparser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class PreferenceSourcesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String APP_PREFERENCES = "PreferenceSettings";

    SharedPreferences sharedPreferences;
    Intent intent;
    CheckBoxPreference notif1;
    CheckBoxPreference notif2;
    CheckBoxPreference notif3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.LOG_TAG, " Created ");
        addPreferencesFromResource(R.xml.sources_preference);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean is9pm = sharedPreferences.getBoolean("notif1",false);
        boolean is10pm = sharedPreferences.getBoolean("notif2",false);
        boolean is11pm = sharedPreferences.getBoolean("notif3",false);
        Log.d(MainActivity.LOG_TAG," is 9 pm? "+is9pm);
    }
}