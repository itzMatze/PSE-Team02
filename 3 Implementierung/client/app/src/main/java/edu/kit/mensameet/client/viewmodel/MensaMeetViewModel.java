package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * Abstract basic view model.
 */
public abstract class MensaMeetViewModel extends ViewModel {

    /**
     SingleLiveEvent: A lifecycle-aware observable that sends only new updates after subscription
     use uiEventLiveData to pass a string to relevant activity, and it executes relevant functions
     */
    protected SingleLiveEvent<Pair<MensaMeetViewModel, StateInterface>> uiEventLiveData;

    /**
     * Gets the live data element to observe.
     *
     * @return Live data element.
     */
    public SingleLiveEvent<Pair<MensaMeetViewModel, StateInterface>> getUiEventLiveData() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, MensaMeetViewModel.State.DEFAULT));
        }
        return uiEventLiveData;
    }

    public enum State implements StateInterface { DEFAULT }
}
