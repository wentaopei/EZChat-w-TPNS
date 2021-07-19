package com.example.ezchat.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * View model for discover fragment
 * @author Wentao Pei
 */

public class DiscoverViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiscoverViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is discover fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}