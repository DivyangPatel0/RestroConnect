package com.RestroConnect.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.RestroConnect.app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class popUp extends Activity {
    Integer occupany_type;
    Button btn_remove,btn_empty,btn_occupy,btn_res;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    String table_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        btn_remove = findViewById(R.id.btn_remove);
        btn_empty = findViewById(R.id.btn_empty);
        btn_occupy = findViewById(R.id.btn_occupy);
        btn_res = findViewById(R.id.btn_res);

        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);

        int width = dn.widthPixels;
        int height = dn.heightPixels;
        getWindow().setLayout((int) (width * .6), (int) (height * .45));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        table_no = getIntent().getStringExtra("TableNo");

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRemove();
            }
        });
        btn_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupany_type=0;
                updateTable();
            }
        });
        btn_occupy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupany_type=1;
                updateTable();
            }
        });
        btn_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupany_type= 2;
                updateTable();
            }
        });
    
    }
    public void updateTable(){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("tables");
        reference.child(table_no).setValue(occupany_type);
        Toast.makeText(popUp.this,"Tables Updated!",Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(popUp.this, Table_Tab.class);
        startActivity(intent);
    }
    public void onRemove(){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("tables");
        reference.child(table_no).removeValue();
        Toast.makeText(popUp.this,"Tables Updated!",Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(popUp.this, Table_Tab.class);
        startActivity(intent);
    }
}
