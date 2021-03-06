package com.pimp.companionforband.utils.band.listeners;

import android.os.Environment;
import android.widget.TextView;

import com.microsoft.band.sensors.BandContactEvent;
import com.microsoft.band.sensors.BandContactEventListener;
import com.opencsv.CSVWriter;
import com.pimp.companionforband.R;
import com.pimp.companionforband.activities.main.MainActivity;
import com.pimp.companionforband.fragments.sensors.SensorActivity;
import com.pimp.companionforband.fragments.sensors.SensorsFragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class ContactEventListener implements BandContactEventListener {

    private TextView textView;
    private boolean graph;

    public void setViews(TextView textView, boolean graph) {
        this.textView = textView;
        this.graph = graph;
    }

    @Override
    public void onBandContactChanged(final BandContactEvent bandContactEvent) {
        if (bandContactEvent != null) {
            if (graph)
                MainActivity.sActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (bandContactEvent.getContactState()) {
                            case UNKNOWN:
                                SensorActivity.chartAdapter.add(-1f);
                                break;
                            case NOT_WORN:
                                SensorActivity.chartAdapter.add(0f);
                                break;
                            case WORN:
                                SensorActivity.chartAdapter.add(2f);
                                break;
                        }
                    }
                });

            SensorsFragment.appendToUI(MainActivity.sContext.getString(R.string.contact_status) + " = " + bandContactEvent.getContactState(), textView);

            if (MainActivity.sharedPreferences.getBoolean("log", false)) {
                MainActivity.bandSensorData.setContactData(bandContactEvent);

                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CompanionForBand" + File.separator + "Contact");
                if (file.exists() || file.isDirectory()) {
                    try {
                        Date date = new Date();
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        if (new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CompanionForBand" + File.separator + "Contact" + File.separator + "Contact_" + DateFormat.getDateInstance().format(date) + ".csv").exists()) {
                            String str = DateFormat.getDateTimeInstance().format(bandContactEvent.getTimestamp());
                            CSVWriter csvWriter = new CSVWriter(new FileWriter(path + File.separator + "CompanionForBand" + File.separator + "Contact" + File.separator + "Contact_" + DateFormat.getDateInstance().format(date) + ".csv", true));
                            csvWriter.writeNext(new String[]{String.valueOf(bandContactEvent.getTimestamp()), str,
                                    String.valueOf(bandContactEvent.getContactState())});
                            csvWriter.close();
                        } else {
                            CSVWriter csvWriter = new CSVWriter(new FileWriter(path + File.separator + "CompanionForBand" + File.separator + "Contact" + File.separator + "Contact_" + DateFormat.getDateInstance().format(date) + ".csv", true));
                            csvWriter.writeNext(new String[]{MainActivity.sContext.getString(R.string.timestamp), MainActivity.sContext.getString(R.string.date_time), MainActivity.sContext.getString(R.string.contact_status)});
                            csvWriter.close();
                        }
                    } catch (IOException e) {
                        //
                    }
                } else {
                    file.mkdirs();
                }
            }
        }
    }
}
