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
import com.msaggik.examplerestapiproject.model.Quote;
import com.msaggik.examplerestapiproject.network.HttpsHelper;
import com.msaggik.examplerestapiproject.viewmodel.adapters.AdapterQuotes;

import java.util.ArrayList;
import java.util.List;

public class QuotesFragment extends Fragment implements Runnable{
    private List<Quote> quotesData = new ArrayList<>();
    private AdapterQuotes adapterQuotes;
    private RecyclerView recyclerView;
    private Handler handler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quotesData.add(new Quote(1, "... загрузка с сервера", "..."));
        handler = new Handler(Looper.getMainLooper()); // создание объекта обработчика сообщений
        new Thread(this).start();


        View view = inflater.inflate(R.layout.fragment_quotes, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_quotes);
        adapterQuotes = new AdapterQuotes(this, quotesData);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapterQuotes);
        return view;
    }

    @Override
    public void run() {
        quotesData = new HttpsHelper().serverDataQuote(getActivity());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapterQuotes = new AdapterQuotes(QuotesFragment.this, quotesData);
                recyclerView.setAdapter(adapterQuotes);
            }
        },0);

    }
}