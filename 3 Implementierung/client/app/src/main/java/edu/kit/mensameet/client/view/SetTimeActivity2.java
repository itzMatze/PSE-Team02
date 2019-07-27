package edu.kit.mensameet.client.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.view.databinding.ActivitySetTime2Binding;
import edu.kit.mensameet.client.view.databinding.ActivitySetTimeBinding;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;
import edu.kit.mensameet.client.viewmodel.SetTimeViewModel;
import edu.kit.mensameet.client.viewmodel.SetTimeViewModel2;
import edu.kit.mensameet.client.viewmodel.StateInterface;

//TODO: Set a special style to clickable textviews

public class SetTimeActivity2 extends MensaMeetActivity {
    private ActivitySetTime2Binding binding;
    private SetTimeViewModel2 viewModel;
    private Context activity;

    private TextView startTime;
    private TextView endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activity = this;

        viewModel = ViewModelProviders.of(this).get(SetTimeViewModel2.class);
        super.viewModel = viewModel;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_time2);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(this);

        startTime = findViewById(R.id.startTime);
        startTime.setTextAppearance(this, R.style.link_text);
        endTime = findViewById(R.id.endTime);
        endTime.setTextAppearance(this, R.style.link_text);

        startTime.setOnClickListener(new View.OnClickListener() {
            private int chosenHour = 12;
            private int chosenMinutes = 0;

            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialogStart = new TimePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String newTimeString = String.format("%02d:%02d", hourOfDay, minutes);
                        if (newTimeString.compareTo(endTime.getText().toString()) <= 0) {
                            startTime.setText(newTimeString);
                            chosenHour = hourOfDay;
                            chosenMinutes = minutes;
                        } else {
                            Toast.makeText(activity, getString(R.string.start_before_end), Toast.LENGTH_SHORT).show();
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

                TimePickerDialog timePickerDialogEnd = new TimePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String newTimeString = String.format("%02d:%02d", hourOfDay, minutes);
                        if (startTime.getText().toString().compareTo(newTimeString) <= 0) {
                            endTime.setText(newTimeString);
                            chosenHour = hourOfDay;
                            chosenMinutes = minutes;
                        } else {
                            Toast.makeText(activity, getString(R.string.start_before_end), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, chosenHour, chosenMinutes, true);

                timePickerDialogEnd.show();
            }

        });

        reloadData();

        super.onCreate(savedInstanceState);

    }

    protected void reloadData() {

        MensaMeetTime savedTime = MensaMeetSession.getInstance().getChosenTime();

        if (savedTime != null) {

            startTime.setText(MensaMeetTime.dateToString(savedTime.getStartTime()));
            endTime.setText(MensaMeetTime.dateToString(savedTime.getEndTime()));

        }
    }

    @Override
    protected void processStateChange(Pair<MensaMeetViewModel, StateInterface> it) {
        if (it.second == SetTimeViewModel2.State.TIME_SAVED_NEXT) {
            gotoActivity(SelectGroupActivity.class);
        } else if (it.second == SetTimeViewModel2.State.TIME_SAVED_BACK) {
            gotoActivity(SelectLinesActivity.class);
        }
    }

    @Override
    protected void onClickNext() {
        viewModel.saveTimeAndNext();
    }

    @Override
    protected void onClickBack() {
        viewModel.saveTimeAndBack();
    }
}
