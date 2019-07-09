package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import edu.kit.mensameet.client.model.MensaMeetSession;

public abstract class MensaMeetViewModel extends ViewModel {
    public MensaMeetViewModel(LiveData<MensaMeetSession> session) {

    }

    public MensaMeetViewModel() {

    }
}
