package com.msaggik.examplerestapiproject.viewmodel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.msaggik.examplerestapiproject.R;
import com.msaggik.examplerestapiproject.model.Quote;
import com.msaggik.examplerestapiproject.view.fragments.QuotesFragment;

import java.util.List;

public class AdapterQuotes extends RecyclerView.Adapter<AdapterQuotes.ViewHolder> {

    // поля адаптера
    private QuotesFragment quotesFragment;
    private final List<Quote> quotes; // поле коллекции контейнера для хранения данных (объектов класса Quote)

    // конструктор адаптера
    public AdapterQuotes(QuotesFragment quotesFragment, List<Quote> quotes) {
        this.quotesFragment = quotesFragment;
        this.quotes = quotes;
    }

    @Override
    public AdapterQuotes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quotes, parent, false); // трансформация layout-файла во View-элемент
        return new ViewHolder(view);
    }

    // метод onBindViewHolder() выполняет привязку объекта ViewHolder к объекту Quote по определенной позиции
    @Override
    public void onBindViewHolder(AdapterQuotes.ViewHolder holder, int position) {
        Quote quote = quotes.get(position);
        holder.authorQuotes.setText(quote.getAuthor());
        holder.textQuotes.setText(quote.getQuote());
    }

    // метод getItemCount() возвращает количество объектов в списке
    @Override
    public int getItemCount() {
        return quotes.size();
    }

    // созданный статический класс ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // неизменяемые поля представления
        final TextView authorQuotes, textQuotes;

        // конструктор класса ViewHolder с помощью которого мы связываем поля и представление item_quotes.xml
        ViewHolder(View view) {
            super(view);
            authorQuotes = view.findViewById(R.id.authorQuotes);
            textQuotes = view.findViewById(R.id.textQuotes);
        }
    }
}