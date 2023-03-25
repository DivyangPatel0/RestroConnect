package com.RestroConnect.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.RestroConnect.app.R;

public class Menu_Tab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_tab);

    }

    public void GoToMenuUpdate(View view){
        Intent intent = new Intent(Menu_Tab.this,Menu_Update.class);
        startActivity(intent);
    }
    public void GoToMenuEdit(View view){
        Intent intent = new Intent(Menu_Tab.this,Menu_Edit.class);
        startActivity(intent);
    }
    public void GoToMenuView(View view){
        Intent intent = new Intent(Menu_Tab.this,Menu_Display.class);
        startActivity(intent);
    }
}