package com.example.ezchat.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * View model for chat fragment
 * @author Dengyun Ma
 */

public class ChatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChatViewModel() {
        mText = new MutableLiveData<>();

        //mText.setValue("This is chat fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
