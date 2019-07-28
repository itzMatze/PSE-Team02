package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;

public abstract class MensaMeetItemHandler {

    protected SingleLiveEvent<Pair<MensaMeetItemHandler, StateInterface>> uiEventLiveData;

    public MensaMeetItemHandler() {

    }

    /**
     * @return UI event
     */
    public SingleLiveEvent<Pair<MensaMeetItemHandler, StateInterface>> getUiEventLiveData() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<MensaMeetItemHandler, StateInterface>(this, State.DEFAULT));
        }
        return uiEventLiveData;
    }

    public enum State implements StateInterface {
        DEFAULT
    }
}
