package com.Divyang.restroconnect;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.Divyang.restroconnect.adapter.ImageAdapter;
import com.example.restroconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class menudisplaypage extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<FirebaseImage> images;
    private StorageReference storageReference;
    public HashMap<String, Object> combinedData = new HashMap<>();
    public HashMap<String, Integer> menuData = new HashMap<>();
    public HashMap<String, String> userData = new HashMap<>();
    public ListView listView;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudisplaypage);

        FirebaseApp.initializeApp(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.setBehavior(new FloatingActionButton.Behavior());

        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInAnonymously:success");
                    Toast.makeText(menudisplaypage.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(menudisplaypage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView = findViewById(R.id.menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        images = new ArrayList<FirebaseImage>();
        imageAdapter = new ImageAdapter(images);
        recyclerView.setAdapter(imageAdapter);
        storageReference = FirebaseStorage.getInstance().getReference().child("images");
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseImage image = new FirebaseImage(uri.toString());
                            images.add(image);
                            imageAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        try{
            if(Login.Global.the_table_allocated == null)
                fab.setVisibility(View.INVISIBLE);
        }catch (NullPointerException e){
            fab.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(v -> showPopup());


    }

    public void placeOrder(){
        DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference().child("Order").child(Login.Global.the_table_allocated);
        // Save the menu data to Firebase storage

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        String currentDateTime = sdf.format(new Date());

        HashMap<String, Object> datetimeMap = new HashMap<>();
        datetimeMap.put("datetime", currentDateTime);

        combinedData.putAll(datetimeMap);
        combinedData.putAll(userData);

        menuRef.setValue(combinedData);
        menuRef.child("items").setValue(menuData);
        Toast.makeText(menudisplaypage.this, "Your Order is Placed!", Toast.LENGTH_SHORT).show();

    }

    public void showPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popupView = getLayoutInflater().inflate(R.layout.confirmpop, null);

        builder.setView(popupView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Populate ListView with data from the menuData HashMap
        listView = popupView.findViewById(R.id.selectedOrderList);
        ArrayList<String> dataList = new ArrayList<>();

        userData.put("UserID", Login.Global.User_ID);

        for (Image cardData : imageAdapter.imageList) {
            if(cardData.getCount()!=0){
                menuData.put(cardData.getTitle(), cardData.getCount());
            }
        }
        for (String key : menuData.keySet()) {
            dataList.add(key + ": " + menuData.get(key));
        }
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.text_view, dataList);
        listView.setAdapter(adapter);

        Button yesButton = popupView.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
                dialog.dismiss();
            }
        });

        Button noButton = popupView.findViewById(R.id.no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();
                listView.setAdapter(null);
                adapter.clear();
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
