package com.rob.smartwatchcardio.ui.inicioFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class inicioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public inicioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}