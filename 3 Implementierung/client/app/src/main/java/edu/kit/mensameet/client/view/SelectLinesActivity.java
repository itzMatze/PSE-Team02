package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.view.databinding.ActivitySelectLinesBinding;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * In this activity, the user can select several mensa lines where he would like to eat.
 */
public class SelectLinesActivity extends MensaMeetActivity {

    private SelectLinesViewModel viewModel;
    private ActivitySelectLinesBinding binding;
    /**
     * List of lines.
     */
    private LineList lineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SelectLinesViewModel.class);
        super.initializeViewModel(viewModel);

        if (!checkAccess()) {
            return;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_lines);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

        LinearLayout container = findViewById(R.id.container);

        // TODO: put .getMensaData().getLines() into one method

        List<Line> linesList = null;
        Line[] lines = viewModel.getMensaLines();
        if (lines != null) {
            linesList = new ArrayList<Line>(Arrays.asList(lines));
        }

        lineList = new LineList(this, linesList,
                MensaMeetList.DisplayMode.MULTIPLE_SELECT,
                true);

        container.addView(lineList.getView());

        reloadData();

    }

    @Override
    protected void reloadData() {

        super.reloadData();

        List<Line> chosenLines = viewModel.getChosenLines();

        if (chosenLines != null) {

            lineList.setSelectedObjects(chosenLines);

        }
    }

    @Override
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == SelectLinesViewModel.State.LINES_SAVED_NEXT) {
            gotoActivity(SetTimeActivity.class);
        } else if (it.second == SelectLinesViewModel.State.LINES_SAVED_BACK) {
            gotoActivity(HomeActivity.class);
        } else if (it.second == SelectLinesViewModel.State.NO_LINES_SELECTED) {
            showMessage(this, R.string.selectALine, it);
        }
    }

    /**
     * When next is clicked, the lines are locally saved before moving on.
     */
    @Override
    public void onClickNext() {
        viewModel.setSelectedLines(lineList.getSelectedObjects());
        viewModel.saveLinesAndNext();
    }

    /**
     * When back is clicked, the lines are locally saved before moving back.
     */
    @Override
    public void onClickBack() {
        viewModel.setSelectedLines(lineList.getSelectedObjects());
        viewModel.saveLinesAndBack();
    }

    @Override
    protected Boolean checkAccess() {
        // Illegal state to show activity, go back.
        if (viewModel.currentUserDataIncomplete() || viewModel.getUser().getGroupToken() != null) {
            finish();
            return false;
        }
        return true;
    }
}
