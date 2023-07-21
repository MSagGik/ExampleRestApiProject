package com.msaggik.examplerestapiproject.viewmodel.fragments;

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