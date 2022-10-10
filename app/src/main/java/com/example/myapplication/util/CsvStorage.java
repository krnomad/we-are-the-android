package com.example.myapplication.util;

import android.os.Environment;
import android.util.Log;

import com.example.myapplication.MainActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvStorage {
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String TAG_WRITE_CSV = "Write CSV";
    private static final String TAG_DIRECTORY = "Create directory";

    private static String TAG = CsvStorage.class.getSimpleName();

    public static void writeCsvFile(String filename, String albumName, List<MainActivity.WifiData> list) {
        if (list == null || list.isEmpty()) return;

//        File file = new File(myDir, filename);
        File myDir = Environment.getExternalStorageDirectory();

        File dir = new File(myDir.getAbsolutePath()+"/test/");
        if( !dir.exists() ) {
            dir.mkdirs();
        }
        File file = new File(dir, "wifi.csv");
        Log.i(TAG, "writeCsvFile: file.getAbsolutePath() =>" + file.getAbsolutePath());
        if( file.exists() ) {
            file.delete();
            file = new File(myDir, filename);
        }

        FileWriter writer = null;

        try {
            writer = new FileWriter(file, true);

            for (MainActivity.WifiData wifiData : list) {
                writer.append(wifiData.getBSSID());
                writer.append(",");
                writer.append(Integer.toString(wifiData.getLevel()));
                writer.append(NEW_LINE_SEPARATOR);
            }
            Log.i(TAG_WRITE_CSV, "Success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                Log.e(TAG_WRITE_CSV, "Failed");
                e.printStackTrace();
            }
        }
    }

    private static File getPublicAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(TAG_DIRECTORY, "Directory not created");
            }
        }
        return file;
    }
}
