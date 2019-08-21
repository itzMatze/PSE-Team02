package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;

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
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.LINES_SAVED_NEXT));
        } else {
            eventLiveData.setValue(new Pair<String, StateInterface>(null, State.NO_LINES_SELECTED));
        }
    }

    /**
     * Save the selected lines to the session and give the signal to move back.
     */
    public void saveLinesAndBack()
    {
        saveLines();
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.LINES_SAVED_BACK));

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
