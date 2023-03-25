package com.RestroConnect.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.RestroConnect.app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Menu_Update extends AppCompatActivity {
    Button btn_select, btn_upload, btn_nxt, btn_display4;
    ImageView img_up;
    TextInputLayout img_name;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_update);

        btn_select = findViewById(R.id.selectImageButton);
        btn_upload = findViewById(R.id.submitButton);
        img_up = findViewById(R.id.firebaseimage);
        btn_nxt = findViewById(R.id.page3);
        btn_display4 = findViewById(R.id.page4);

        btn_select.setOnClickListener(v -> {
            selectImage();
        });
        btn_upload.setOnClickListener(v -> uploadImage());
        btn_nxt.setOnClickListener(v -> {
            Intent intent = new Intent(this, Menu_Display.class);
            startActivity(intent);
        });
        btn_display4.setOnClickListener(v -> {
            Intent intent = new Intent(this, Menu_Edit.class);
            startActivity(intent);
        });
    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();

        updateDatabase();

        img_name = findViewById(R.id.dish_name_text);
        String fileName = img_name.getEditText().getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        img_name.getEditText().setText("");

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                img_up.setImageURI(null);
                Toast.makeText(Menu_Update.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(Menu_Update.this,"Failed to Upload",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateDatabase(){
        TextInputLayout D_prize,D_name;
        D_prize = findViewById(R.id.dish_price);
        D_name = findViewById(R.id.dish_name_text);

        DatabaseReference tableRef = FirebaseDatabase.getInstance().getReference().child("Menu").child(D_name.getEditText().getText().toString());

        HashMap<String, Integer> menuDetail = new HashMap<>();
        menuDetail.put("Price", Integer.valueOf((D_prize.getEditText().getText().toString())));
        D_prize.getEditText().setText("");

        tableRef.setValue(menuDetail);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            img_up.setImageURI(imageUri);

        }
    }

}