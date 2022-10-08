package com.example.myapplication.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.List;

public class PermissionDialog extends Dialog implements View.OnClickListener {
    private List<String> permissions;
    private Activity activity;

    public PermissionDialog(Activity context, List<String> permissions) {
        super(context);
        this.permissions = permissions;
        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_permission);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button buttonOk = (Button) findViewById(R.id.button_dialog_ok);
        buttonOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_dialog_ok:
                break;

            default:
                break;
        }

        dismiss();

        MainActivity mainActivity = (MainActivity) this.activity;
        mainActivity.requestPermissions(permissions.toArray(new String[permissions.size()]));
    }
}
