package com.RestroConnect.myapp;

import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public abstract class Image {
    private String imageUrl;
    private String title;
    private int count;
    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.count = count;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getTitle() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
        String fileName = storageReference.getName();
        return fileName;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public abstract void displayImage(ImageView imageView);
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}