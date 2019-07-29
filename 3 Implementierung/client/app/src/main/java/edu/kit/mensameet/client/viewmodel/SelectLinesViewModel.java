package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.util.SingleLiveEvent;

/**
 * View model for SelectLinesActivity.
 */
public class SelectLinesViewModel extends MensaMeetViewModel {

    private List<Line> selectedLines = new ArrayList<Line>();

    public void setSelectedLines(List<Line> selectedLines) {
        this.selectedLines = selectedLines;
    }

    /**
     * Save the selected lines to the session and give the signal to move next.
     */
    public void saveLinesAndNext()
    {
        if (saveLines()) {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.LINES_SAVED_NEXT));
        } else {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.NO_LINES_SELECTED));
        }
    }

    /**
     * Save the selected lines to the session and give the signal to move back.
     */
    public void saveLinesAndBack()
    {
        if (saveLines()) {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.LINES_SAVED_BACK));
        } else {
            uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.NO_LINES_SELECTED));
        }
    }

    /**
     * Internal line saving method.
     * @return Whether lines were selected.
     */
    private Boolean saveLines() {
        if (selectedLines.size() > 0) {
            MensaMeetSession.getInstance().setChosenLines(selectedLines);
            return true;
        } else {
            return false;
        }
    }


    public enum State implements StateInterface {
        NO_LINES_SELECTED, LINES_SAVED_NEXT, LINES_SAVED_BACK, DEFAULT
    }
}
