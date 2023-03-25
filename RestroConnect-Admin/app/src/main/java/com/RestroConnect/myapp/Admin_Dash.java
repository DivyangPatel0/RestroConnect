package com.RestroConnect.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.VerifiedInputEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.RestroConnect.app.R;

public class Admin_Dash extends AppCompatActivity {
    Button otp_c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_dash);

        otp_c=findViewById(R.id.otp_check);
        otp_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OTP_Chekin.class);
                startActivity(intent);
            }
        });
    }
    public void LogOutAdmin(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void GoToTableMenu(View view){
        Intent intent = new Intent(Admin_Dash.this,Table_Tab.class);
        startActivity(intent);
    }
    public void GoToMenu(View view){
        Intent intent = new Intent(Admin_Dash.this,Menu_Tab.class);
        startActivity(intent);
    }


}