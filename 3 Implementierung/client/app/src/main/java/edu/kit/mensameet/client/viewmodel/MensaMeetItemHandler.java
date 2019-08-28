package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * Abstract general handler class for a MensaMeetItem
 */
public abstract class MensaMeetItemHandler<T> {

    protected T objectData;

    /**
     * Livedata object for communication with item
     */
    protected SingleLiveEvent<Pair<String, StateInterface>> eventLiveData = new SingleLiveEvent<>();

    public MensaMeetItemHandler(T objectData) {
        this.objectData = objectData;
    }

    /**
     * Getter for the livedata
     *
     * @return UI event
     */
    public SingleLiveEvent<Pair<String, StateInterface>> getEventLiveData() {

        return eventLiveData;
    }

    /**
     *  Sets the object data.
     *
     * @param objectData The object data.
     */
    public void setObjectData(T objectData) {
        this.objectData = objectData;
    }

    public T getObjectData() {
        return objectData;
    }

    public User getCurrentUser() {
        return MensaMeetSession.getInstance().getUser();
    }

    public enum State implements StateInterface {    }
}
