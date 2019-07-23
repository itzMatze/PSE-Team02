package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import edu.kit.mensameet.client.util.SingleLiveEvent;

public class SelectGroupViewModel extends MensaMeetViewModel {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SingleLiveEvent<Pair<SelectGroupViewModel, String>> uiEventLiveData;
    /*
    private MutableLiveData<LineList> lineList;
     */

    public SingleLiveEvent<Pair<SelectGroupViewModel, String>> getUiEventLiveData() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<SelectGroupViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }

}
