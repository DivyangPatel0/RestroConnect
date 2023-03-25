package com.Divyang.restroconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.Divyang.restroconnect.adapter.CategoryAdapter;
import com.Divyang.restroconnect.adapter.DiscountedProductAdapter;
import com.Divyang.restroconnect.adapter.RecentlyViewedAdapter;
import com.Divyang.restroconnect.model.Category;
import com.example.restroconnect.R;
import com.example.restroconnect.R.drawable;

import com.Divyang.restroconnect.model.DiscountedProducts;
import com.Divyang.restroconnect.model.RecentlyViewed;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    RecentlyViewedAdapter recentlyViewedAdapter;
    List<RecentlyViewed> recentlyViewedList;

    TextView allCategory;
    TextView moveToTablecheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);



        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = findViewById(R.id.recently_item);

        allCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this,menudisplaypage.class);
            startActivity(intent);
        });

        // adding data to model
        discountedProductsList = new ArrayList<>();
        discountedProductsList.add(new DiscountedProducts(1, drawable.burgerf));
        discountedProductsList.add(new DiscountedProducts(3, drawable.pizza));
        discountedProductsList.add(new DiscountedProducts(4, drawable.donut));
        discountedProductsList.add(new DiscountedProducts(5, drawable.cupcakes));
        discountedProductsList.add(new DiscountedProducts(6, drawable.indianrice));
        discountedProductsList.add(new DiscountedProducts(2, drawable.briyani));

        // adding data to model
        categoryList = new ArrayList<>();
        recentlyViewedList = new ArrayList<>();
        recentlyViewedList.add(new RecentlyViewed("ChickenChilly", "Chicken Chilli is an exotic fusion of soy sauce blended with indo-chinese spices along with some saute vegetables", "₹ 300", "Qty 1,  ", "Serves 2", drawable.chickenpieces, drawable.chickenb));
        recentlyViewedList.add(new RecentlyViewed("Hot and Garlic Noodles", "Authentic hakka noodles infused with Indian masalas.", "₹ 250", "Qty 1,  ", "Serves 1", drawable.noodleramen, drawable.noodleramenb));
        recentlyViewedList.add(new RecentlyViewed("Basil and tomato pizza", "Flat base of leavened wheat-based dough topped with tomatoes, cheese, and garnished with fresh basil leaves", "₹ 450", "Qty 1,  ", "12 inches", drawable.pizza2, drawable.pizza2));
        recentlyViewedList.add(new RecentlyViewed("Dal Makni", "Black lentils are cooked with a generous amount of white butter and cream. This one has a signature smooth velvety texture and lovely flavor.more special", "₹ 350", "Qty 1,  ", "Serves 2", drawable.dalmakni, drawable.dalmaknib));
        recentlyViewedList.add(new RecentlyViewed("Butter Tandoori Roti", "Hot crispy yet buttery roti grilled on tandoor", "₹ 35", "2 Portions,  ", "Serves 1", drawable.fishchips, drawable.fishchipsb));
        recentlyViewedList.add(new RecentlyViewed("Burger", "Crispy patties, lettuce, tomato, onion, mayo, special sauce and lots of Cheese ", "₹ 300", "Qty 1,  ", "Serves 1", drawable.burgerorder, drawable.burgerorderb));
        recentlyViewedList.add(new RecentlyViewed("BBQ Chicken", "For the ULTIMATE CHICKEN LOVER  the HOT, SMOKY  BBQ chicken covered with spicy bbq sauce ", "₹ 350", "Qty 1,  ", "Serves 2", drawable.barbequechicken, drawable.barbequechickenb));
        recentlyViewedList.add(new RecentlyViewed("Fish Chips", "Crispy fish chips that can fulfill your hunger in no time ", "₹ 350", "Qty 1,  ", "Serves 1 or 2", drawable.fishchips, drawable.fishchipsb));
        recentlyViewedList.add(new RecentlyViewed("Butter Tandoori Roti", "Hot crispy yet buttery roti grilled on tandoor", "₹ 35", "2 Portions,  ", "Serves 1", drawable.roti, drawable.rotib));
        recentlyViewedList.add(new RecentlyViewed("Gulab Jamun", "Sweet as a desert should be", "₹ 90", "Qty2,  ", "Serves 1", drawable.gulbajamun, drawable.gulabjamunb));
        recentlyViewedList.add(new RecentlyViewed("Rice", "Fresh & hot  basmati steamed rice that smells and tastes fantastic", "₹ 150", "Qty1,  ", "Serves 1 or 2", drawable.rice, drawable.riceb));

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewedRecycler(recentlyViewedList);

        moveToTablecheck = findViewById(R.id.Clickable_txt_am);
        moveToTablecheck.setOnClickListener(v -> {
            Intent intent = new Intent(this,Table_allocation.class);
            startActivity(intent);
        });

        try{
            if(Login.Global.the_table_allocated != null)
                moveToTablecheck.setText("Alloted Table : "+Login.Global.the_table_allocated);
        }catch (NullPointerException e){

        }
    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this,dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<RecentlyViewed> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this,recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }
    //Now again we need to create a adapter and model class for recently viewed items.
    // lets do it fast.

}