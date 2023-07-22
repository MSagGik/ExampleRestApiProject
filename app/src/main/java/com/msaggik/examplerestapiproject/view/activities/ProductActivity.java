package com.msaggik.examplerestapiproject.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.msaggik.examplerestapiproject.R;
import com.msaggik.examplerestapiproject.databinding.FragmentProductsBinding;
import com.msaggik.examplerestapiproject.model.Product;
import com.msaggik.examplerestapiproject.view.fragments.ProductsFragment;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    private TextView titleProductOne, priceProductOne, descriptionProductOne;
    private ImageView imageProductOne;
    private Toolbar toolbar;
    private Product product;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        titleProductOne = findViewById(R.id.titleProductOne);
        priceProductOne = findViewById(R.id.priceProductOne);
        descriptionProductOne = findViewById(R.id.descriptionProductOne);
        imageProductOne = findViewById(R.id.imageProductOne);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getResources().getString(R.string.menu_products));
        toolbar.setTitleTextColor(getResources().getColor(R.color.beige, getTheme()));
        toolbar.getMenu().findItem(R.id.toProducts).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                finish();
                return true;
            }
        });


        // считывание данных из главной активити
        Bundle bundleIntent = getIntent().getExtras();
        if (bundleIntent != null) { // если в данном контейнере что-то есть, то

            // инициализация поля сущности пользователя
            product = (Product) bundleIntent.getSerializable(Product.class.getSimpleName());

            // вывод на экран данных из фрагмента товаров
            titleProductOne.setText(product.getTitle());
            priceProductOne.setText(product.getPrice() + " coin");
            descriptionProductOne.setText(product.getDescription());
            Picasso.get().load(product.getImages()).into(imageProductOne);
        }
    }
}