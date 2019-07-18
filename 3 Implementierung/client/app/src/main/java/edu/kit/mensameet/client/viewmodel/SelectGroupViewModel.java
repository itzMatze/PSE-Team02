package edu.kit.mensameet.client.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;

public class SelectGroupViewModel extends MensaMeetViewModel {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SingleLiveEvent<Pair<SelectGroupViewModel, String>> uiEventLiveData;
    /*
    private MutableLiveData<LineList> lineList;
     */

    public SingleLiveEvent<Pair<SelectGroupViewModel, String>> getUiEventLiveData() {
        if(uiEventLiveData == null){
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<SelectGroupViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }

}
