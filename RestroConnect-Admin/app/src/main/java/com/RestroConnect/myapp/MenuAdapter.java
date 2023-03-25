package com.RestroConnect.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.RestroConnect.app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<Menu> {
    private Context mContext;
    private int mResource;
    private List<Menu> mMenuList;

    public MenuAdapter(Context context, int resource, List<Menu> objects) {
        super(context, resource,objects);
        mContext = context;
        mResource = resource;
        mMenuList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResource, parent, false);

        TextView dishNameTextView = view.findViewById(R.id.menu_name);
        TextView dishPriceTextView = view.findViewById(R.id.menu_price);
        Button deleteButton = view.findViewById(R.id.delete_menu_item);

        final Menu currentMenu = (Menu) mMenuList.get(position);

        dishNameTextView.setText(currentMenu.getDish_name());
        dishPriceTextView.setText(String.valueOf(currentMenu.getDish_prize()));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dName = currentMenu.getDish_name();
                DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("Menu").child(dName);
                menuRef.removeValue();
                StorageReference imgRef =  FirebaseStorage.getInstance().getReference("images/"+dName);
                imgRef.delete();
            }
        });

        return view;
    }
}
