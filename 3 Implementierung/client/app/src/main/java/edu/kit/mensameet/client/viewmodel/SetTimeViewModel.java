package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;

import edu.kit.mensameet.client.model.MensaMeetTime;

public class SetTimeViewModel extends MensaMeetViewModel {
    public LiveData<MensaMeetTime> loadTime() {
        return null;
    }

    public void saveTime(LiveData<MensaMeetTime> time) {

    }
}
