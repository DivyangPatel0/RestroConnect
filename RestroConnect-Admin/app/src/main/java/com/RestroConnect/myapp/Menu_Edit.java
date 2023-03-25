package com.RestroConnect.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.RestroConnect.app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu_Edit extends AppCompatActivity {

    private ListView mListView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_edit);

        mListView = findViewById(R.id.menu_list_view);
        mDatabase = FirebaseDatabase.getInstance().getReference("Menu");

        final List<Menu> menuList = new ArrayList<Menu>();
        final MenuAdapter adapter = new MenuAdapter(this, R.layout.menu_items_list, menuList);
        mListView.setAdapter(adapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuList.clear();
                for (DataSnapshot menuSnapshot : dataSnapshot.getChildren()) {
                    String dishName = menuSnapshot.getKey();
                    Integer dishPrice =menuSnapshot.child("Price").getValue(int.class);
                    Menu menu = new Menu(dishName, dishPrice);
                    menuList.add(menu);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Menu_Edit.this, "Error loading menu.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}