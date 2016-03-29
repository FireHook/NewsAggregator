package com.example.testpageparser;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferenceTimeActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    AlarmManagerReceiver alarmManagerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmManagerReceiver = new AlarmManagerReceiver();
        addPreferencesFromResource(R.xml.schedule_preference);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String hours = sharedPreferences.getString("Hours", "");
        String minutes = sharedPreferences.getString("Minutes", "");

        alarmManagerReceiver.cancelAlarm(this);
        alarmManagerReceiver.setAlarm(this, Integer.valueOf(hours), Integer.valueOf(minutes));

    }
}