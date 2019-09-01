package edu.kit.mensameet.client.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.util.MensaMeetUtil;
import edu.kit.mensameet.client.view.databinding.ActivitySetTimeBinding;
import edu.kit.mensameet.client.viewmodel.SetTimeViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Activity for setting the time interval of the meal.
 */
public class SetTimeActivity extends MensaMeetActivity {
    private ActivitySetTimeBinding binding;
    protected SetTimeViewModel viewModel;

    private TextView startTime;
    private TextView endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SetTimeViewModel.class);
        super.viewModel = viewModel;

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!checkAccess()) {
            return;
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_time);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        observeLiveData();
        initializeButtons();

        startTime = findViewById(R.id.startTimeText);
        MensaMeetUtil.applyStyle(startTime, R.style.link_text);

        endTime = findViewById(R.id.endTimeText);
        MensaMeetUtil.applyStyle(endTime, R.style.link_text);

        // The time fields are linked with a time picker dialog
        startTime.setOnClickListener(new View.OnClickListener() {
            private int chosenHour = 12;
            private int chosenMinutes = 0;

            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialogStart = new TimePickerDialog(SetTimeActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String newTimeString = String.format("%02d:%02d", hourOfDay, minutes);
                        if (newTimeString.compareTo(endTime.getText().toString()) <= 0) {
                            startTime.setText(newTimeString);
                            chosenHour = hourOfDay;
                            chosenMinutes = minutes;
                        } else {
                            Toast.makeText(SetTimeActivity.this, getString(R.string.start_before_end), Toast.LENGTH_LONG).show();
                        }
                    }
                }, chosenHour, chosenMinutes, true);

                timePickerDialogStart.show();
            }

        });

        endTime.setOnClickListener(new View.OnClickListener() {
            private int chosenHour = 12;
            private int chosenMinutes = 0;

            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialogEnd = new TimePickerDialog(SetTimeActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String newTimeString = String.format("%02d:%02d", hourOfDay, minutes);
                        if (startTime.getText().toString().compareTo(newTimeString) <= 0) {
                            endTime.setText(newTimeString);
                            chosenHour = hourOfDay;
                            chosenMinutes = minutes;
                        } else {
                            Toast.makeText(SetTimeActivity.this, getString(R.string.start_before_end), Toast.LENGTH_LONG).show();
                        }
                    }
                }, chosenHour, chosenMinutes, true);

                timePickerDialogEnd.show();
            }

        });

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (!checkAccess()) {
            return;
        }

        MensaMeetTime savedTime = viewModel.getChosenTime();

        if (savedTime != null) {

            startTime.setText(MensaMeetTime.timeToString(savedTime.getStartTime()));
            endTime.setText(MensaMeetTime.timeToString(savedTime.getEndTime()));

        }
    }

    @Override
    protected void processStateChange(Pair<String, StateInterface> it) {
        if (it.second == SetTimeViewModel.State.TIME_SAVED_NEXT) {
            gotoActivity(SelectGroupActivity.class);
        } else if (it.second == SetTimeViewModel.State.TIME_SAVED_BACK) {
            gotoActivity(SelectLinesActivity.class);
        } else if (it.second == SetTimeViewModel.State.TIME_SAVED_HOME) {
            gotoActivity(HomeActivity.class);
        }
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

    @Override
    public void onClickNext() {
        viewModel.saveTimeAndNext();
    }

    @Override
    public void onClickBack() {
        viewModel.saveTimeAndBack();
    }

    @Override
    public void onClickHome() {
        viewModel.saveTimeAndHome();
    }
}
