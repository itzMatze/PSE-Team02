package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import edu.kit.mensameet.client.util.SingleLiveEvent;

public class SelectLinesViewModel extends MensaMeetViewModel {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SingleLiveEvent<Pair<SelectLinesViewModel, String>> uiEventLiveData;
    /*
    private MutableLiveData<LineList> lineList;
     */

    /**
     * @return UI event
     */
    public SingleLiveEvent<Pair<SelectLinesViewModel, String>> getUiEventLiveData() {
        if (uiEventLiveData == null) {
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<SelectLinesViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }

}
