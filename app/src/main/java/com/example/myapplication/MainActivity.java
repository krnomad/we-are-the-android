package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.util.CsvStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private final static String[] permissions = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private WifiManager wm;
    private BroadcastReceiver brScanResults;
    private final String filename = "myCSV.csv";
    private final String albumName = "Sensor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // <- activity_main.xml
        Log.i(TAG, "onCreate: hello world");

        // 권한 취득
        // 권한이 있는지 체크 (생략)
        // 권한이 없는 경우 사용자에게 권한을 취득할 수 있게하는 dialog 생성 (생략)
        // 권한 요청에 대한한 후 처리 (생략)

        TextView textView = findViewById(R.id.textView); // <- "@+id/textView"
        Button button = findViewById(R.id.button12);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("hello world");
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, 123);
        }

        wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Timer scheduler = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                wm.startScan();
                Log.i(TAG, "run: time task call startScan ");
            }
        };

        scheduler.scheduleAtFixedRate(task, 500, 1000); // 1초 뒤 매 500 ms 간격으로 task 실행

        brScanResults = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "onReceive: !!!!!!!!! data received !!!!!!!!!!!");
                List<ScanResult> scanResultList = wm.getScanResults();
                List<WifiData> wifiDataList = new ArrayList<>();

                for (ScanResult scanResult : scanResultList) {
                    Log.i(TAG, "onReceive: scanResult => " + scanResult);
                    WifiData wifiData = new WifiData(scanResult.BSSID, scanResult.level);
                    wifiDataList.add(wifiData);
                }

                // FIXME: permission error
//                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    Log.i(TAG, "onReceive: !! possible ! ");
//                    CsvStorage.writeCsvFile(filename, albumName, wifiDataList);
//                }

//                for (int i = 0; i < scanResultList.size(); i++) {
//                    Log.i(TAG, "onReceive: scanResult => " + scanResultList.get(i));
//                    wifiDataList.add(new WifiData(scanResultList.get(i).BSSID, scanResultList.get(i).level));
//                }

                textView.setText(wifiDataList.toString());
//                textView.setText(wifiDataList.get(0).toString());
//                textView.setText(String.format("%s %d", wifiDataList.get(0).BSSID, wifiDataList.get(0).level));
            }
        };

        registerReceiver(brScanResults, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public static class WifiData {
        public String getBSSID() {
            return BSSID;
        }

        public int getLevel() {
            return level;
        }

        private final String BSSID;
        private final int level;

        @NonNull
        @Override
        public String toString() {
            return "WifiData{" +
                    "BSSID='" + BSSID + '\'' +
                    ", level=" + level +
                    '}' + "\n";
        }

        public WifiData(String BSSID, int level) {
            this.BSSID = BSSID;
            this.level = level;
        }
    }
}