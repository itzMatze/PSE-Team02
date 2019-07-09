package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.kit.mensameet.client.model.Line;

public class HomeViewModel extends MensaMeetViewModel {
    public LiveData<List<Line>> loadLines() {
        return null;
    }

    public void saveLines(LiveData<List<Line>> chosenLines) {

    }

    public void logout() {

    }
}
