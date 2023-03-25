package com.Divyang.restroconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.restroconnect.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import java.util.Objects;

public class Verification extends AppCompatActivity {

    Button btn_verify;
    TextInputLayout OTP_Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);

        int width = dn.widthPixels;
        int height = dn.heightPixels;
        getWindow().setLayout((int) (width * .6), (int) (height * .25));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        OTP_Input = findViewById(R.id.OTP_Vy);
        btn_verify = findViewById(R.id.Verify_btn);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpCheck();
            }
        });


    }
    private void otpCheck(){
        final String userEnteredOTP = Objects.requireNonNull(OTP_Input.getEditText()).getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("customers");
        Query checkUser = reference.orderByChild("username").equalTo(Login.Global.User_ID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String otpFromDB = dataSnapshot.child(Login.Global.User_ID).child("OTP").getValue(String.class);
                    if(otpFromDB.equals(userEnteredOTP)){
                        OTP_Input.setError(null);
                        OTP_Input.setErrorEnabled(false);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else{
                        OTP_Input.setError("Invalid OTP!");
                        OTP_Input.requestFocus();
                    }

                }else{
                    OTP_Input.setError("Please Generate OTP!");
                    OTP_Input.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}