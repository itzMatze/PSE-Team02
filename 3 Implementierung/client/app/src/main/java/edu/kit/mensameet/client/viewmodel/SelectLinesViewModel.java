package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;

public class SelectLinesViewModel extends MensaMeetViewModel {

    private List<Line> selectedLines = new ArrayList<Line>();

    public void setSelectedLines(List<Line> selectedLines) {
        this.selectedLines = selectedLines;
    }

    public void saveLinesAndNext() {
        if (selectedLines.size() > 0) {
            MensaMeetSession.getInstance().setChosenLines(selectedLines);
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.LINES_SAVED));
        } else {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.NO_LINES_SELECTED));
        }
    }

    public enum State implements StateInterface {
        NO_LINES_SELECTED, LINES_SAVED, DEFAULT
    }
}
