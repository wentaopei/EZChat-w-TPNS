package com.example.ezchat.ui.me;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * View model for me fragment
 * @author Wentao Pei
 */

public class MeViewModel extends ViewModel
{
    private MutableLiveData<String> mText;

    public MeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is me fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
