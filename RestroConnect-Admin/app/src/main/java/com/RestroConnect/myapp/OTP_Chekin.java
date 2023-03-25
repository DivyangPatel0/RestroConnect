package com.RestroConnect.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.RestroConnect.app.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.snapshot.StringNode;

import java.util.ArrayList;

public class OTP_Chekin extends AppCompatActivity {
    ListView myListView;
    DatabaseReference mRef;
    ArrayList<String> myArrayList = new ArrayList<>();
    FirebaseDatabase rootNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_chekin);

        ArrayAdapter<String> myArrayAdaptor = new ArrayAdapter<String>(OTP_Chekin.this, android.R.layout.simple_list_item_1,myArrayList);
        myListView = findViewById(R.id.otp_Table);
        myListView.setAdapter(myArrayAdaptor);
        mRef = FirebaseDatabase.getInstance().getReference("customers");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = (String) snapshot.child("OTP").getValue();
                String Key = snapshot.getKey();
                String Str = Key.toString();
                String Input_string =Str+" : "+value;
                myArrayList.add(Input_string);
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
    }
}