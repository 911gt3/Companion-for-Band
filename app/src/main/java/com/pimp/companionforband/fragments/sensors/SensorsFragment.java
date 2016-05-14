package com.pimp.companionforband.fragments.sensors;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.microsoft.band.BandIOException;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandAltimeterEventListener;
import com.microsoft.band.sensors.BandAmbientLightEventListener;
import com.microsoft.band.sensors.BandBarometerEventListener;
import com.microsoft.band.sensors.BandCaloriesEventListener;
import com.microsoft.band.sensors.BandContactEventListener;
import com.microsoft.band.sensors.BandDistanceEventListener;
import com.microsoft.band.sensors.BandGsrEventListener;
import com.microsoft.band.sensors.BandGyroscopeEventListener;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.BandPedometerEventListener;
import com.microsoft.band.sensors.BandRRIntervalEventListener;
import com.microsoft.band.sensors.BandSkinTemperatureEventListener;
import com.microsoft.band.sensors.BandUVEventListener;
import com.microsoft.band.sensors.GsrSampleRate;
import com.microsoft.band.sensors.SampleRate;
import com.pimp.companionforband.R;
import com.pimp.companionforband.activities.main.MainActivity;
import com.pimp.companionforband.utils.band.listeners.AccelerometerEventListener;
import com.pimp.companionforband.utils.band.listeners.AltimeterEventListener;
import com.pimp.companionforband.utils.band.listeners.AmbientLightEventListener;
import com.pimp.companionforband.utils.band.listeners.BarometerEventListener;
import com.pimp.companionforband.utils.band.listeners.CaloriesEventListener;
import com.pimp.companionforband.utils.band.listeners.ContactEventListener;
import com.pimp.companionforband.utils.band.listeners.DistanceEventListener;
import com.pimp.companionforband.utils.band.listeners.GsrEventListener;
import com.pimp.companionforband.utils.band.listeners.GyroscopeEventListener;
import com.pimp.companionforband.utils.band.listeners.HeartRateEventListener;
import com.pimp.companionforband.utils.band.listeners.PedometerEventListener;
import com.pimp.companionforband.utils.band.listeners.RRIntervalEventListener;
import com.pimp.companionforband.utils.band.listeners.SkinTemperatureEventListener;
import com.pimp.companionforband.utils.band.listeners.UVEventListener;
import com.pimp.companionforband.utils.band.subscription.Band1SubscriptionTask;
import com.pimp.companionforband.utils.band.subscription.Band2SubscriptionTask;
import com.pimp.companionforband.utils.band.subscription.HeartRateSubscriptionTask;
import com.pimp.companionforband.utils.band.subscription.RRIntervalSubscriptionTask;

import java.lang.ref.WeakReference;

public class SensorsFragment extends Fragment {

    public static Spinner chart_spinner;
    public static WeakReference<Activity> reference;
    public static TextView band2TV, logTV, backlogTV, accelerometerTV, altimeterTV, ambientLightTV,
            barometerTV, caloriesTV, contactTV, distanceTV, gsrTV, gyroscopeTV, heartRateTV,
            pedometerTV, rrTV, skinTempTV, uvTV;
    int permissionCheck;
    public static GraphView graph;
    public static LineGraphSeries<DataPoint> series1, series2, series3;
    public static double graphLastValueX = 0;

    public static BandAccelerometerEventListener bandAccelerometerEventListener = new AccelerometerEventListener();
    public static BandAltimeterEventListener bandAltimeterEventListener = new AltimeterEventListener();
    public static BandAmbientLightEventListener bandAmbientLightEventListener = new AmbientLightEventListener();
    public static BandBarometerEventListener bandBarometerEventListener = new BarometerEventListener();
    public static BandCaloriesEventListener bandCaloriesEventListener = new CaloriesEventListener();
    public static BandContactEventListener bandContactEventListener = new ContactEventListener();
    public static BandDistanceEventListener bandDistanceEventListener = new DistanceEventListener();
    public static BandGsrEventListener bandGsrEventListener = new GsrEventListener();
    public static BandGyroscopeEventListener bandGyroscopeEventListener = new GyroscopeEventListener();
    public static BandHeartRateEventListener bandHeartRateEventListener = new HeartRateEventListener();
    public static BandPedometerEventListener bandPedometerEventListener = new PedometerEventListener();
    public static BandRRIntervalEventListener bandRRIntervalEventListener = new RRIntervalEventListener();
    public static BandSkinTemperatureEventListener bandSkinTemperatureEventListener = new SkinTemperatureEventListener();
    public static BandUVEventListener bandUVEventListener = new UVEventListener();

    View.OnClickListener sampleRateRadioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (v.getId()) {
                    case R.id.accelerometer_ms16:
                        MainActivity.client.getSensorManager().registerAccelerometerEventListener(
                                bandAccelerometerEventListener, SampleRate.MS16);
                        editor.putInt("acc_hz", R.id.accelerometer_ms16);
                        break;
                    case R.id.accelerometer_ms32:
                        MainActivity.client.getSensorManager().registerAccelerometerEventListener(
                                bandAccelerometerEventListener, SampleRate.MS32);
                        editor.putInt("acc_hz", R.id.accelerometer_ms32);
                        break;
                    case R.id.accelerometer_ms128:
                        MainActivity.client.getSensorManager().registerAccelerometerEventListener(
                                bandAccelerometerEventListener, SampleRate.MS128);
                        editor.putInt("acc_hz", R.id.accelerometer_ms128);
                        break;
                    case R.id.gyroscope_ms16:
                        MainActivity.client.getSensorManager().registerGyroscopeEventListener(
                                bandGyroscopeEventListener, SampleRate.MS16);
                        editor.putInt("gyr_hz", R.id.gyroscope_ms16);
                        break;
                    case R.id.gyroscope_ms32:
                        MainActivity.client.getSensorManager().registerGyroscopeEventListener(
                                bandGyroscopeEventListener, SampleRate.MS32);
                        editor.putInt("gyr_hz", R.id.gyroscope_ms32);
                        break;
                    case R.id.gyroscope_ms128:
                        MainActivity.client.getSensorManager().registerGyroscopeEventListener(
                                bandGyroscopeEventListener, SampleRate.MS128);
                        editor.putInt("gyr_hz", R.id.gyroscope_ms128);
                        break;
                    case R.id.gsr_ms200:
                        if (MainActivity.band2)
                            MainActivity.client.getSensorManager().registerGsrEventListener(
                                    bandGsrEventListener, GsrSampleRate.MS200);
                        editor.putInt("gsr_hz", R.id.gsr_ms200);
                        break;
                    case R.id.gsr_ms5000:
                        if (MainActivity.band2)
                            MainActivity.client.getSensorManager().registerGsrEventListener(
                                    bandGsrEventListener, GsrSampleRate.MS5000);
                        editor.putInt("gsr_hz", R.id.gsr_ms5000);
                        break;
                }
                editor.apply();
            } catch (Exception e) {
                //
            }
        }
    };
    private TextView scrubInfoTextView;

    public static SensorsFragment newInstance() {
        return new SensorsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        logTV = (TextView) view.findViewById(R.id.logStatus);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
            logTV.setText(getResources().getString(R.string.permit_log));
        else
            logTV.setText(getResources().getString(R.string.log_text));

        backlogTV = (TextView) view.findViewById(R.id.backlogStatus);
        band2TV = (TextView) view.findViewById(R.id.band2TxtStatus);
        accelerometerTV = (TextView) view.findViewById(R.id.accelerometerStatus);
        altimeterTV = (TextView) view.findViewById(R.id.altimeterStatus);
        ambientLightTV = (TextView) view.findViewById(R.id.lightStatus);
        barometerTV = (TextView) view.findViewById(R.id.barometerStatus);
        caloriesTV = (TextView) view.findViewById(R.id.caloriesStatus);
        contactTV = (TextView) view.findViewById(R.id.contactStatus);
        distanceTV = (TextView) view.findViewById(R.id.distanceStatus);
        gsrTV = (TextView) view.findViewById(R.id.gsrStatus);
        gyroscopeTV = (TextView) view.findViewById(R.id.gyroscopeStatus);
        heartRateTV = (TextView) view.findViewById(R.id.heartStatus);
        pedometerTV = (TextView) view.findViewById(R.id.pedometerStatus);
        rrTV = (TextView) view.findViewById(R.id.rrStatus);
        skinTempTV = (TextView) view.findViewById(R.id.temperatureStatus);
        uvTV = (TextView) view.findViewById(R.id.UVStatus);

        final Switch SheartStatus = (Switch) view.findViewById(R.id.heart_rate_switch);
        SheartStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SheartStatus.isChecked()) {
                    MainActivity.editor.putBoolean("heart", false);
                    MainActivity.editor.apply();
                    heartRateTV.setVisibility(View.GONE);
                    try {
                        MainActivity.client.getSensorManager().unregisterHeartRateEventListeners();
                    } catch (Exception e) {
                        //
                    }
                } else {
                    MainActivity.editor.putBoolean("heart", true);
                    MainActivity.editor.apply();
                    heartRateTV.setVisibility(View.VISIBLE);
                    new HeartRateSubscriptionTask().execute();
                }
            }
        });

        final Switch SrrStatus = (Switch) view.findViewById(R.id.rr_switch);
        SrrStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SrrStatus.isChecked()) {
                    MainActivity.editor.putBoolean("rr", false);
                    MainActivity.editor.apply();
                    rrTV.setVisibility(View.GONE);
                    try {
                        MainActivity.client.getSensorManager().unregisterRRIntervalEventListeners();
                    } catch (Exception e) {
                        //
                    }
                } else {
                    MainActivity.editor.putBoolean("rr", true);
                    MainActivity.editor.apply();
                    rrTV.setVisibility(View.VISIBLE);
                    new RRIntervalSubscriptionTask().execute();
                }
            }
        });

        ((Switch) view.findViewById(R.id.log_switch)).setChecked(MainActivity.sharedPreferences.getBoolean("log", false));
        if (!MainActivity.sharedPreferences.getBoolean("log", false))
            view.findViewById(R.id.logStatus).setVisibility(View.GONE);
        else {
            view.findViewById(R.id.logStatus).setVisibility(View.VISIBLE);
            view.findViewById(R.id.backlog_switch).setVisibility(View.VISIBLE);
        }
        ((Switch) view.findViewById(R.id.backlog_switch)).setChecked(MainActivity.sharedPreferences.getBoolean("backlog", false));
        if (!MainActivity.sharedPreferences.getBoolean("backlog", false))
            view.findViewById(R.id.backlogStatus).setVisibility(View.GONE);
        else
            view.findViewById(R.id.backlogStatus).setVisibility(View.VISIBLE);

        ((Switch) view.findViewById(R.id.heart_rate_switch)).setChecked(MainActivity.sharedPreferences.getBoolean("heart", true));
        if (!MainActivity.sharedPreferences.getBoolean("heart", true))
            view.findViewById(R.id.heartStatus).setVisibility(View.GONE);
        else {
            new HeartRateSubscriptionTask().execute();
            view.findViewById(R.id.heartStatus).setVisibility(View.VISIBLE);
        }
        ((Switch) view.findViewById(R.id.rr_switch)).setChecked(MainActivity.sharedPreferences.getBoolean("rr", true));
        if (!MainActivity.sharedPreferences.getBoolean("rr", true))
            view.findViewById(R.id.rrStatus).setVisibility(View.GONE);
        else {
            new RRIntervalSubscriptionTask().execute();
            view.findViewById(R.id.rrStatus).setVisibility(View.VISIBLE);
        }

        int[] ids = {R.id.altimeter_switch, R.id.light_switch, R.id.barometer_switch,
                R.id.calories_switch, R.id.contact_switch, R.id.distance_switch,
                R.id.pedometer_switch, R.id.temperature_switch, R.id.UV_switch};
        String[] strings = {"alt", "light", "bar", "cal", "con", "dis", "ped", "tem", "uv"};
        TextView[] textViews = {altimeterTV, ambientLightTV, barometerTV, caloriesTV, contactTV,
                distanceTV, pedometerTV, skinTempTV, uvTV};

        for (int i = 0; i < ids.length; i++) setSwitch(view, ids[i], strings[i], textViews[i]);
        reference = new WeakReference<Activity>(getActivity());

        int[] hzIds = {R.id.accelerometer_switch, R.id.gyroscope_switch, R.id.gsr_switch};
        String[] hzStrings = {"acc", "gyr", "gsr"};
        TextView[] hzTextViews = {accelerometerTV, gyroscopeTV, gsrTV};
        int[] hzRadioGroups = {R.id.accelerometer_radioGroup, R.id.gyroscope_radioGroup, R.id.gsr_radioGroup};
        for (int i = 0; i < hzIds.length; i++)
            setSwitch(view, hzIds[i], hzStrings[i], hzTextViews[i], hzRadioGroups[i]);

        new Band2SubscriptionTask().execute();
        new Band1SubscriptionTask().execute();

        chart_spinner = (Spinner) view.findViewById(R.id.chart_spinner);

        view.findViewById(R.id.accelerometer_ms16).setOnClickListener(sampleRateRadioButtonClickListener);
        view.findViewById(R.id.accelerometer_ms32).setOnClickListener(sampleRateRadioButtonClickListener);
        view.findViewById(R.id.accelerometer_ms128).setOnClickListener(sampleRateRadioButtonClickListener);
        view.findViewById(R.id.gyroscope_ms16).setOnClickListener(sampleRateRadioButtonClickListener);
        view.findViewById(R.id.gyroscope_ms32).setOnClickListener(sampleRateRadioButtonClickListener);
        view.findViewById(R.id.gyroscope_ms128).setOnClickListener(sampleRateRadioButtonClickListener);
        view.findViewById(R.id.gsr_ms200).setOnClickListener(sampleRateRadioButtonClickListener);
        view.findViewById(R.id.gsr_ms5000).setOnClickListener(sampleRateRadioButtonClickListener);

        graph = (GraphView) view.findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();
        series1.setColor(getResources().getColor(R.color.accent));
        series2 = new LineGraphSeries<>();
        series2.setColor(getResources().getColor(R.color.primary_dark));
        series3 = new LineGraphSeries<>();
        series3.setColor(getResources().getColor(R.color.primary_light));
        graph.addSeries(series1);
        graph.addSeries(series2);
        graph.addSeries(series3);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
    }

    public static void appendToUI(final String string, final TextView textView) {
        try {
            MainActivity.sActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(string);
                }
            });
        } catch (Exception e) {
            //
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = MainActivity.sharedPreferences;
        if (!sharedPreferences.getBoolean("backlog", false)) {
            if (MainActivity.client != null) {
                try {
                    MainActivity.client.getSensorManager().unregisterAllListeners();
                } catch (BandIOException e) {
                    MainActivity.appendToUI(e.getMessage(), "Style.ALERT");
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = MainActivity.sharedPreferences;

        if (sharedPreferences.getBoolean("rr", true))
            new RRIntervalSubscriptionTask().execute();

        if (sharedPreferences.getBoolean("heart", true))
            new HeartRateSubscriptionTask().execute();

        new Band1SubscriptionTask().execute();
        new Band2SubscriptionTask().execute();
    }

    private void setSwitch(View view, int id, String string, TextView textView) {
        ((Switch) view.findViewById(id)).setChecked(MainActivity.sharedPreferences.getBoolean(string, true));
        if (!MainActivity.sharedPreferences.getBoolean(string, true))
            textView.setVisibility(View.GONE);
        else
            textView.setVisibility(View.VISIBLE);
    }

    private void setSwitch(View view, int id, String string, TextView textView, int radioGroupId) {
        int radioButtonId = MainActivity.sharedPreferences.getInt(string + "_hz", R.id.accelerometer_ms128);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(radioGroupId);
        RadioButton radioButton = (RadioButton) view.findViewById(radioButtonId);
        radioButton.setChecked(true);

        ((Switch) view.findViewById(id)).setChecked(MainActivity.sharedPreferences.getBoolean(string, true));
        if (!MainActivity.sharedPreferences.getBoolean(string, true)) {
            textView.setVisibility(View.GONE);
            radioGroup.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
        }
    }
}