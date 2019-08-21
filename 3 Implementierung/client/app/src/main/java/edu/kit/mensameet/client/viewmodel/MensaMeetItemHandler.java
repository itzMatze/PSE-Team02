package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * Abstract general handler class for a MensaMeetItem
 */
public abstract class MensaMeetItemHandler {

    /**
     * Livedata object for communication with item
     */
    protected SingleLiveEvent<Pair<String, StateInterface>> eventLiveData;

    public MensaMeetItemHandler() { }

    /**
     * Getter for the livedata
     *
     * @return UI event
     */
    public SingleLiveEvent<Pair<String, StateInterface>> getEventLiveData() {
        if (eventLiveData == null) {
            eventLiveData = new SingleLiveEvent<>();
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.DEFAULT));
        }
        return eventLiveData;
    }

    public enum State implements StateInterface {
        DEFAULT
    }
}
