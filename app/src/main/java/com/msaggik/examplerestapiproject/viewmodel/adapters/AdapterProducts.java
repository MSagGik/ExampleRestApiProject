package com.msaggik.examplerestapiproject.viewmodel.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.msaggik.examplerestapiproject.R;
import com.msaggik.examplerestapiproject.model.Product;
import com.msaggik.examplerestapiproject.view.activities.ProductActivity;
import com.msaggik.examplerestapiproject.view.fragments.ProductsFragment;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

    // поля адаптера
    private ProductsFragment productsFragment;
    private final List<Product> products; // поле коллекции контейнера для хранения данных (объектов класса Product)

    // конструктор адаптера
    public AdapterProducts(ProductsFragment productsFragment, List<Product> products) {
        this.productsFragment = productsFragment;
        this.products = products;
    }

    @Override
    public AdapterProducts.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false); // трансформация layout-файла во View-элемент
        return new AdapterProducts.ViewHolder(view);
    }

    // метод onBindViewHolder() выполняет привязку объекта ViewHolder к объекту Product по определенной позиции

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AdapterProducts.ViewHolder holder, int position) {
        Product product = products.get(position);
        Picasso.get().load(product.getThumbnail()).into(holder.thumbnailProduct);
        holder.titleProduct.setText(product.getTitle());
        holder.priceProduct.setText(product.getPrice() + " coin");
        holder.descriptionProduct.setText(product.getDescription());
        // слушатель нажатия на карточку
        holder.cardViewProduct.setOnClickListener(view -> {
            Intent intent = new Intent(productsFragment.getActivity(), ProductActivity.class);
            intent.putExtra(Product.class.getSimpleName(), product);
            Objects.requireNonNull(productsFragment.getActivity()).startActivity(intent);
        });
    }

    // метод getItemCount() возвращает количество объектов в списке
    @Override
    public int getItemCount() {
        return products.size();
    }

    // созданный статический класс ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // неизменяемые поля представления
        final CardView cardViewProduct;
        final ImageView thumbnailProduct;
        final TextView titleProduct, priceProduct, descriptionProduct;

        // конструктор класса ViewHolder с помощью которого мы связываем поля и представление item_products.xml
        ViewHolder(View view) {
            super(view);
            cardViewProduct = view.findViewById(R.id.cardViewProduct);
            thumbnailProduct = view.findViewById(R.id.thumbnailProduct);
            titleProduct = view.findViewById(R.id.titleProduct);
            priceProduct = view.findViewById(R.id.priceProduct);
            descriptionProduct = view.findViewById(R.id.descriptionProduct);
        }
    }
}