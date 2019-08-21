package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.lifecycle.ViewModel;

import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * Abstract basic view model.
 */
public abstract class MensaMeetViewModel extends ViewModel {

    /**
     SingleLiveEvent: A lifecycle-aware observable that sends only new updates after subscription
     use eventLiveData to pass a string to relevant activity, and it executes relevant functions
     */
    protected SingleLiveEvent<Pair<String, StateInterface>> eventLiveData;

    /**
     * Gets the live data element to observe.
     *
     * @return Live data element.
     */
    public SingleLiveEvent<Pair<String, StateInterface>> getEventLiveData() {
        if (eventLiveData == null) {
            eventLiveData = new SingleLiveEvent<>();
            eventLiveData.setValue(new Pair<String, StateInterface>(null, MensaMeetViewModel.State.DEFAULT));
        }
        return eventLiveData;
    }

    public enum State implements StateInterface { DEFAULT }
}
