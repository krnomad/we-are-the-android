package com.example.myapplication;

import static com.example.myapplication.SimpleTextAdapter.*;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.ui.PermissionDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private String TAG = MainActivity.class.getSimpleName();
    private View mainLayout;

    private final static String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
    };

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);

        RecyclerView recyclerView = findViewById(R.id.main_list);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.format("TEXT %d", i));
        }

        for (String s : list) {
            Log.i(TAG, "onCreate: s => " + s);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SimpleTextAdapter adapter = new SimpleTextAdapter(list);
        adapter.setListener((viewType, position) -> {
            switch (viewType) {
                case VIEW_TEXT: {
                    String message = String.format("%s가 클릭 됐다. 그 내용은 %s이다", position, list.get(position));
                    Snackbar mySnackbar = Snackbar.make(mainLayout, message, Snackbar.LENGTH_INDEFINITE);
                    mySnackbar.setAction("확인", view -> {
                        mySnackbar.dismiss();
                    });
                    mySnackbar.show();
                }
                break;

                case VIEW_VTN: {
                    String message = position + " button이 눌려짐";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    break;
                }

                default:
                    return;
            }


        });

        recyclerView.setAdapter(adapter);
        handlePermissions();
    }

    public void handlePermissions() {
        if (android.os.Build.VERSION.SDK_INT < 23) {
            return;
        }

        List<String> permissionsToRequest = new ArrayList<String>();

        for (int i = 0; i < permissions.length; i++) {
            String p = permissions[i];
            if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(p);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            new PermissionDialog(this, permissionsToRequest).show();
        }
    }

    public void requestPermissions(String[] permissions) {
        requestPermissions(permissions, 111);
    }
}