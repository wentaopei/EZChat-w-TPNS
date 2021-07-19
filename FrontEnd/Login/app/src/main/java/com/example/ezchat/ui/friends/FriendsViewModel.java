package com.example.ezchat.ui.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * View model for friends fragment
 * @author Dengyun Ma
 */

public class FriendsViewModel extends ViewModel
{
    private MutableLiveData<String> mText;

    public FriendsViewModel()
    {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}