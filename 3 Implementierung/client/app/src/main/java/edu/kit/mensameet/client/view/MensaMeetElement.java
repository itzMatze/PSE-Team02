package edu.kit.mensameet.client.view;

import android.content.Context;
import android.util.Pair;
import android.view.View;

import edu.kit.mensameet.client.util.SingleLiveEvent;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.StateInterface;
//TODO: remove it?

public abstract class MensaMeetElement {

    protected SingleLiveEvent<Pair<MensaMeetElement, StateInterface>> uiEventLiveData;

    /**
     * @return UI event
     */
    public SingleLiveEvent<Pair<MensaMeetElement, StateInterface>> getUiEventLiveData() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<MensaMeetElement, StateInterface>(this, MensaMeetElement.State.DEFAULT));
        }
        return uiEventLiveData;
    }

    public enum State implements StateInterface { DEFAULT }
}
