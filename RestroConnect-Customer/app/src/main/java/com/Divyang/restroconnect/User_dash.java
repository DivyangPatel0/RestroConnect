package com.Divyang.restroconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restroconnect.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class User_dash extends AppCompatActivity {

    TextView user_title;
    private Button btnGenerate,menu_tab;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        user_title = findViewById(R.id.username_field);
        user_title.setText(Login.Global.User_ID);

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnGenerate = findViewById(R.id.btn_generate);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        menu_tab=findViewById(R.id.menu_Card);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String randomKey = generateRandomKey();
                databaseReference.child("customers").child(Login.Global.User_ID).child("OTP").setValue(randomKey);
                Toast.makeText(User_dash.this, "OTP GENERATED!", Toast.LENGTH_SHORT).show();
            }
        });

        menu_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Verification.class);
                startActivity(intent);
            }
        });

    }
    private String generateRandomKey() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

}