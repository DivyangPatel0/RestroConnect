package com.Divyang.restroconnect;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FirebaseImage extends Image {

    public FirebaseImage(String imageUrl) {super(imageUrl);}

    @Override
    public void displayImage(ImageView imageView) {
        Picasso.get().load(getImageUrl()).into(imageView);
    }
}
