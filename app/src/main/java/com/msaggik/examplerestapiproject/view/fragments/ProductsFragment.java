package com.msaggik.examplerestapiproject.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.msaggik.examplerestapiproject.R;
import com.msaggik.examplerestapiproject.model.Product;
import com.msaggik.examplerestapiproject.network.HttpsHelper;
import com.msaggik.examplerestapiproject.viewmodel.adapters.AdapterProducts;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment implements Runnable{
    private List<Product> productsData = new ArrayList<>();
    private AdapterProducts adapterProducts;
    private RecyclerView recyclerView;
    private Handler handler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        productsData.add(new Product(1, "...", 0.0f, "... загрузка с сервера", null, null));
        handler = new Handler(Looper.getMainLooper()); // создание объекта обработчика сообщений
        new Thread(this).start();

        View view = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_products);
        adapterProducts = new AdapterProducts(this, productsData);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapterProducts);
        return view;
    }

    @Override
    public void run() {
        productsData = new HttpsHelper().serverDataProduct(getActivity());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapterProducts = new AdapterProducts(ProductsFragment.this, productsData);
                recyclerView.setAdapter(adapterProducts);
            }
        },0);
    }
}