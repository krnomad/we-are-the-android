package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private View mainLayout;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);

        RecyclerView recyclerView = findViewById(R.id.main_list);
        List<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i));
        }

        for (String s : list) {
            Log.i(TAG, "onCreate: s => " + s);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SimpleTextAdapter adapter = new SimpleTextAdapter(list);
        adapter.setListener(new SimpleTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String message = String.format("%s가 클릭 됐다. 그 내용은 %s이다", position, list.get(position));
                Snackbar mySnackbar = Snackbar.make(mainLayout, message, Snackbar.LENGTH_INDEFINITE);
                mySnackbar.setAction("확인", view -> {
                    mySnackbar.dismiss();
                });
                mySnackbar.show();
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}