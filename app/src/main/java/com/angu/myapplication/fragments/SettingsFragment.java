package com.angu.myapplication.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.room.Room;

import com.angu.myapplication.R;
import com.angu.myapplication.data.Statistics;
import com.angu.myapplication.data.StatisticsDatabase;

public class SettingsFragment extends PreferenceFragmentCompat {

    private StatisticsDatabase database;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        final Context context = getActivity();

        database = Room.databaseBuilder(
                context,
                StatisticsDatabase.class,
                "statistics"
        ).build();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        Preference resetHighscores = findPreference("resetHighscores");
        resetHighscores.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            new AsyncTask<Void, Void, Boolean>() {
                                @Override
                                protected Boolean doInBackground(Void... voids) {
                                    database.statisticsDao().deleteAll();
                                    return true;
                                }
                            }.execute();

                            Toast.makeText(context, "Highscores have been reset", Toast.LENGTH_SHORT).show();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;

            }
        });
    }
}
