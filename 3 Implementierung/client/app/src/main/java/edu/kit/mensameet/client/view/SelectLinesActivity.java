package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.util.Pair;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.mensameet.client.model.FoodType;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.Meal;
import edu.kit.mensameet.client.model.MensaData;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.view.databinding.ActivitySelectLinesBinding;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public class SelectLinesActivity extends MensaMeetActivity {

    private SelectLinesViewModel viewModel;
    private ActivitySelectLinesBinding binding;
    private LineList lineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(SelectLinesViewModel.class);
        super.viewModel = viewModel;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_lines);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        LinearLayout container = findViewById(R.id.container);

        Meal[] linie1Meals = new Meal[]{new Meal("Schnitzel", (float) 2.60, new FoodType[]{FoodType.VEGAN,})};
        Line linie1 = new Line("Linie 1", linie1Meals);
        Meal[] linie2Meals = new Meal[]{new Meal("Salat", (float) 2.60, new FoodType[]{FoodType.VEGAN,})};
        Line linie2 = new Line("Linie 2", linie2Meals);
        Meal[] linie3Meals = new Meal[]{new Meal("Wurst", (float) 2.60, new FoodType[]{FoodType.VEGAN,})};
        Line linie3 = new Line("Linie 3", linie3Meals);
        MensaMeetSession.getInstance().setMensaData(new MensaData(new Line[]{linie1, linie2, linie3}));

        // TODO: put .getMensaData().getLines() into one method
        List<Line> linesList = new ArrayList<Line>(Arrays.asList(MensaMeetSession.getInstance().getMensaData().getLines()));

        lineList = new LineList(this, linesList,
                MensaMeetList.DisplayMode.MULTIPLE_SELECT,
                true);

        container.addView(lineList.getView());

        reloadData();

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void reloadData() {

        if (MensaMeetSession.getInstance().getChosenLines() != null) {

            //Toast.makeText(this, MensaMeetSession.getInstance().getChosenLines().size(), Toast.LENGTH_SHORT).show();

            lineList.setSelectedObjects(MensaMeetSession.getInstance().getChosenLines());


        }
    }

    @Override
    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {
        if (it.second == SelectLinesViewModel.State.LINES_SAVED) {
            gotoActivity(SetTimeActivity2.class);
        } else if (it.second == SelectLinesViewModel.State.NO_LINES_SELECTED) {
            Toast.makeText(this, getString(R.string.selectALine), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNext() {
        viewModel.setSelectedLines(lineList.getSelectedObjects());
        viewModel.saveLinesAndNext();
    }

    @Override
    public void onClickBack() {

    }
}
