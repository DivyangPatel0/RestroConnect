package com.RestroConnect.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.RestroConnect.app.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Table_Tab extends AppCompatActivity {
    ListView myListView;
    String Str;
    int count=0;
    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference mRef;
    FirebaseDatabase rootNode;

    public void Show(View view) {

        if (myListView.getVisibility() == View.VISIBLE) {
            myListView.setVisibility(View.INVISIBLE);
        } else {
            myListView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_table_tab);

        ArrayAdapter<String> myArrayAdaptor = new ArrayAdapter<String>(Table_Tab.this, android.R.layout.simple_list_item_1,myArrayList);
        myListView = findViewById(R.id.allTables);
        myListView.setVisibility(View.INVISIBLE);
        myListView.setAdapter(myArrayAdaptor);
        mRef = FirebaseDatabase.getInstance().getReference("tables");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                count++;
                Integer value = snapshot.getValue(Integer.class);
                String Key = snapshot.getKey();
                Str = Key.toString();
                if (value == 0){
                    Str+=") Empty";
                    myArrayList.add(Str);

                }else if(value == 2) {
                    Str+=") Reserved";
                    myArrayList.add(Str);

                }else if(value == 1){
                    Str+=") Occupied";
                    myArrayList.add(Str);
                }else {
                    mRef.child(Str).removeValue();
                }
                myArrayAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myListView.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(),popUp.class);
                String tabNo = Integer.toString(i+1);
                intent.putExtra("TableNo",tabNo);
                startActivity(intent);
            }
        });
    }
    public void AddNewTable(View view){
        ArrayAdapter<String> myArrayAdaptor = new ArrayAdapter<String>(Table_Tab.this, android.R.layout.simple_list_item_1,myArrayList);
        rootNode = FirebaseDatabase.getInstance();
        String newTable = Integer.toString(count+1);
        mRef.child(newTable).setValue(0);
        myArrayAdaptor.notifyDataSetChanged();
        Toast.makeText(Table_Tab.this,"Table Added!",Toast.LENGTH_SHORT).show();
    }
    public void TableGoToAdminDash(View view){
        Intent intent = new Intent(getApplicationContext(), Admin_Dash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}