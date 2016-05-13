package com.pimp.companionforband.utils.band.listeners;

import android.os.Environment;

import com.microsoft.band.sensors.BandUVEvent;
import com.microsoft.band.sensors.BandUVEventListener;
import com.opencsv.CSVWriter;
import com.pimp.companionforband.R;
import com.pimp.companionforband.activities.main.MainActivity;
import com.pimp.companionforband.fragments.sensors.SensorsFragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class UVEventListener implements BandUVEventListener {
    @Override
    public void onBandUVChanged(final BandUVEvent bandUVEvent) {
        if (bandUVEvent != null) {
            if (MainActivity.band2) {
                try {
                    SensorsFragment.appendToUI(MainActivity.sContext.getString(R.string.uv_today) + " = " + bandUVEvent.getUVExposureToday() + "\n" +
                            MainActivity.sContext.getString(R.string.uv_index) + " = " + bandUVEvent.getUVIndexLevel(), SensorsFragment.uvTV);
                } catch (Exception e) {
                    SensorsFragment.appendToUI(e.toString(), SensorsFragment.uvTV);
                }
            } else {
                SensorsFragment.appendToUI(MainActivity.sContext.getString(R.string.uv_index) + " = " + bandUVEvent.getUVIndexLevel(), SensorsFragment.uvTV);
            }
            if (MainActivity.sharedPreferences.getBoolean("log", false)) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CompanionForBand" + File.separator + "UV");
                if (file.exists() || file.isDirectory()) {
                    try {
                        Date date = new Date();
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        if (new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CompanionForBand" + File.separator + "UV" + File.separator + "UV_" + DateFormat.getDateInstance().format(date) + ".csv").exists()) {
                            String str = DateFormat.getDateTimeInstance().format(bandUVEvent.getTimestamp());
                            CSVWriter csvWriter = new CSVWriter(new FileWriter(path + File.separator + "CompanionForBand" + File.separator + "UV" + File.separator + "UV_" + DateFormat.getDateInstance().format(date) + ".csv", true));
                            if (MainActivity.band2) {
                                try {
                                    csvWriter.writeNext(new String[]{String.valueOf(bandUVEvent.getTimestamp()),
                                            str, String.valueOf(bandUVEvent.getUVExposureToday()), String.valueOf(bandUVEvent.getUVIndexLevel())});
                                } catch (Exception e) {
                                    SensorsFragment.appendToUI(e.toString(), SensorsFragment.uvTV);
                                }
                            } else {
                                csvWriter.writeNext(new String[]{String.valueOf(bandUVEvent.getTimestamp()),
                                        str, String.valueOf(bandUVEvent.getUVIndexLevel())});
                            }
                            csvWriter.close();
                        } else {
                            CSVWriter csvWriter = new CSVWriter(new FileWriter(path + File.separator + "CompanionForBand" + File.separator + "UV" + File.separator + "UV_" + DateFormat.getDateInstance().format(date) + ".csv", true));
                            if (MainActivity.band2)
                                csvWriter.writeNext(new String[]{MainActivity.sContext.getString(R.string.timestamp), MainActivity.sContext.getString(R.string.date_time), MainActivity.sContext.getString(R.string.uv_today), MainActivity.sContext.getString(R.string.uv_index)});
                            else
                                csvWriter.writeNext(new String[]{MainActivity.sContext.getString(R.string.timestamp), MainActivity.sContext.getString(R.string.date_time), MainActivity.sContext.getString(R.string.uv_index)});
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
