package com.Divyang.restroconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restroconnect.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Table_allocation extends AppCompatActivity {
    TextInputLayout Occupancy_need;
    Button allocate_btn,page_next;
    TextView Table_given;
    long occupancy;
    Boolean CheckFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_allocation);

        allocate_btn = findViewById(R.id.table_check_btn);
        Table_given = findViewById(R.id.tablegivenTxt);
        Occupancy_need = findViewById(R.id.occupancy_needed);
        page_next = findViewById(R.id.back_to_menu_btn);

        page_next.setOnClickListener(v ->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } );
        allocate_btn.setOnClickListener(v -> checkTableStatus());
    }

    public void checkTableStatus() {

        DatabaseReference tableDataRef = FirebaseDatabase.getInstance().getReference("TableData");
        String inputText = Objects.requireNonNull(Occupancy_need.getEditText()).getText().toString();
        int Occupancy = Integer.parseInt(inputText);

        tableDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot tableSnapshot : snapshot.getChildren()) {
                    try{
                        occupancy = (long) tableSnapshot.child("Occupancy").getValue();
                    }catch(NullPointerException e){
                        break;
                    }
                    if (occupancy >= Occupancy) {
                        long status = (long) tableSnapshot.child("Status").getValue();
                        if (status == 0) {
                            Long tableNo = (Long) tableSnapshot.child("tableNo").getValue();
                            String table_allocated = "table" + tableNo;
                            Login.Global.the_table_allocated = table_allocated;
                            tableDataRef.child(table_allocated).child("Status").setValue(2);
                            tableDataRef.child(table_allocated).child("Customer_ID").setValue(Login.Global.User_ID);
                            Toast.makeText(Table_allocation.this, "Table Allocated.", Toast.LENGTH_SHORT).show();
                            Table_given.setText(table_allocated);
                            tableDataRef.removeEventListener(this);
                            CheckFlag = true;
                            break;
                        }
                    }
                }
                if(CheckFlag==false){
                    Toast.makeText(Table_allocation.this, "Sorry, No Table available currently.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Table_allocation.this, "Unable to connect database!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}