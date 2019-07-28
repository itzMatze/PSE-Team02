package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;

public abstract class MensaMeetViewModel extends ViewModel {

    protected SingleLiveEvent<Pair<MensaMeetViewModel, StateInterface>> uiEventLiveData;

    public MensaMeetViewModel(LiveData<MensaMeetSession> session) {

    }

    public MensaMeetViewModel() {

    }

    /**
     * @return UI event
     */
    public SingleLiveEvent<Pair<MensaMeetViewModel, StateInterface>> getUiEventLiveData2() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, MensaMeetViewModel.State.DEFAULT));
        }
        return uiEventLiveData;
    }

    public enum State implements StateInterface {DEFAULT}
}
