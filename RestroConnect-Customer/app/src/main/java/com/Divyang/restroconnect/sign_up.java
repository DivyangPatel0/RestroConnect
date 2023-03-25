package com.Divyang.restroconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.restroconnect.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {


    TextInputLayout regName,regUsername,regEmail,regPhoneNo,regPass;
    Button calllogin,callregister;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        regName=findViewById(R.id.reg_name);
        regUsername=findViewById(R.id.reg_username);
        regEmail=findViewById(R.id.reg_email);
        regPass=findViewById(R.id.reg_pass);
        regPhoneNo=findViewById(R.id.reg_phoneno);
        callregister=findViewById(R.id.register_admin);
        calllogin = findViewById(R.id.log_in_screen);


        calllogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sign_up.this,Login.class);
                startActivity(intent);

            }
        });
    }
    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if(val.isEmpty()){
            regName.setError("Feild cannot be empty!");
            return false;
        }else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();

        if(val.isEmpty()){
            regUsername.setError("Feild cannot be empty!");
            return false;
        }else if(val.length()>=15){
            regUsername.setError("Username too long!");
            return false;

        }
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = regPass.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPass.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPass.setError("Password is too weak");
            return false;
        } else {
            regPass.setError(null);
            regPass.setErrorEnabled(false);
            return true;
        }
    }
    public void RegisterUser(View view) {

        if(!validateName() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()){
            return;
        }
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String passcode = regPass.getEditText().getText().toString();

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("customers");
        UserHelperClass helperClass = new UserHelperClass(name,username,email,phoneNo,passcode);
        reference.child(username).setValue(helperClass);
        Toast.makeText(sign_up.this,"you are registered",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(sign_up.this,Login.class);
        startActivity(intent);

    }
}