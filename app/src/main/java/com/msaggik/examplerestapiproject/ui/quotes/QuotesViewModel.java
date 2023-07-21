package com.msaggik.examplerestapiproject.ui.quotes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuotesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public QuotesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Цитаты");
    }

    public LiveData<String> getText() {
        return mText;
    }
}