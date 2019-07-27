package edu.kit.mensameet.client.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.viewmodel.GroupItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.SelectGroupViewModel;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public class GroupItem extends MensaMeetItem<Group> {

    private UserList userList;
    private GroupItemHandler handler;

    public GroupItem(Context context, MensaMeetDisplayMode displayMode, Group objectData) {
        super(context, displayMode, objectData);

        handler = new GroupItemHandler(objectData, displayMode);

        if (objectData != null) {
            userList = new UserList(context, objectData.getUsers(), MensaMeetList.DisplayMode.NO_SELECT, false);
        }

    }

    @Override
    protected void processStateChange(Pair<MensaMeetItemHandler, StateInterface> it) {

    }

    // necessary otherwise crash
    @Override
    public GroupItemHandler getHandler() {
        return handler;
    }

    @Override
    public View createView() {

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(super.WIDTH_MATCH_PARENT);
        view.setPadding(30, 20, 30, 20);

        // Field: name
        view.addView(super.createTextField(R.string.field_name, super.WIDTH_MATCH_PARENT, super.BIG_TEXT_SIZE));

        // Field: motto
        view.addView(super.createTextField(R.string.field_motto, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        // Field: time
        if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            final TextView chooseTime = new TextView(context);
            chooseTime.setTextAppearance(context, R.style.normal_text);
            chooseTime.setId((int)R.string.field_time);
            chooseTime.setText(R.string.chooseTime);

            chooseTime.setOnClickListener(new View.OnClickListener() {
                private int chosenHour = 12;
                private int chosenMinutes = 0;

                @Override
                public void onClick(View view) {

                    TimePickerDialog timePickerDialog = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                            chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
                            chosenHour = hourOfDay;
                            chosenMinutes = minutes;
                        }
                    }, chosenHour, chosenMinutes, true);

                    timePickerDialog.show();
                }

            });

            view.addView(chooseTime);
        } else {
            view.addView(super.createTextField(R.string.field_time, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));
        }

        // Field: line
        if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            final TextView chooseLine = new TextView(context);
            chooseLine.setTextAppearance(context, R.style.link_text);;
            chooseLine.setId((int)R.string.field_line);
            chooseLine.setText(R.string.selectLine);

            chooseLine.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SelectOneLineDialog selectOneLineDialog = new SelectOneLineDialog(context, new SelectOneLineDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick(SelectOneLineDialog dialog) {
                            List<Line> selectedLines = dialog.getSelectedLines();
                            if (selectedLines.size() > 0) {
                                chooseLine.setText(selectedLines.get(0).getName());
                            }
                        }
                    });

                    selectOneLineDialog.show();
                }
            });

            view.addView(chooseLine);
        } else {
            view.addView(super.createTextField(R.string.field_line, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));
        }

        // Field: Maximum member number

        if (super.displayMode == MensaMeetDisplayMode.SMALL || super.displayMode == MensaMeetDisplayMode.BIG_NOTEDITABLE) {

            view.addView(super.createTextField(R.string.field_max_members, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        } else if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            TextView maxMembersLabel = new TextView(context);
            maxMembersLabel.setText(R.string.field_max_members);
            maxMembersLabel.setTextAppearance(context, R.style.link_text);

            view.addView(maxMembersLabel);

            //get the spinner from the xml.
            Spinner dropdown = new Spinner(context);
            dropdown.setId((int)R.string.field_max_members);
            int maxMemberMax = context.getResources().getInteger(R.integer.max_member_max);
//create a list of items for the spinner.
            String[] items = new String[maxMemberMax];
            for (int i = 0; i < maxMemberMax; i++) {
                items[i] = Integer.toString(i + 1);
            }
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
            dropdown.setAdapter(adapter);

            view.addView(dropdown);

        }


            // Field: Join button
        if (super.displayMode == MensaMeetDisplayMode.SMALL) {

            final Button joinButton = new Button(context);
            joinButton.setLayoutParams(super.WIDTH_MATCH_PARENT);
            joinButton.setText(R.string.join_group);
            joinButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    handler.joinGroup();
                }
            });

            view.addView(joinButton);
        }

        // Field: User list
        if (super.displayMode != MensaMeetDisplayMode.BIG_EDITABLE) {
            LinearLayout userListContainer = new LinearLayout(context);
            userListContainer.setOrientation(LinearLayout.VERTICAL);
            userListContainer.setLayoutParams(super.WIDTH_MATCH_PARENT);
            userListContainer.setId((int)R.string.field_member_list);

            view.addView(userListContainer);
        }


        return view;
    }

    @Override
    public void fillObjectData() {

        super.fillTextField(R.string.field_name, super.objectData.getName());
        super.fillTextField(R.string.field_motto, super.objectData.getMotto());

        Date time = super.objectData.getMeetingDate();
        if (time != null) {
            super.fillTextField(R.string.field_time, MensaMeetTime.dateToString(time));
        }

        String line = super.objectData.getLine();
        if (line != null) {
            super.fillTextField(R.string.field_line, line);
        }

        View maxMembersField = view.findViewById((int)R.string.field_max_members);
        if (maxMembersField.getClass() == Spinner.class) {
            ((Spinner)maxMembersField).setSelection(super.objectData.getMaxMembers());
        } else {
            super.fillTextField(R.string.field_max_members, Integer.toString(super.objectData.getMaxMembers()));
        }

        super.fillSublist(R.string.field_member_list, userList);

    }

    @Override
    public void saveEditedObjectData() {

        objectData.setName(super.extractTextField(R.string.field_name));
        objectData.setMotto(super.extractTextField(R.string.field_motto));
        objectData.setMeetingDate(MensaMeetTime.stringToDate(super.extractTextField(R.string.field_time)));
        objectData.setLine(super.extractTextField(R.string.field_line));

        if (view.findViewById((int)R.string.field_max_members).getClass() == Spinner.class) {
            objectData.setMaxMembers(((Spinner)view.findViewById((int)R.string.field_max_members)).getSelectedItemPosition());
        } else {
            String maxMembers = super.extractTextField(R.string.field_max_members);
            if (maxMembers == null) {
                objectData.setMaxMembers(0);
            } else {
                objectData.setMaxMembers(Integer.parseInt(super.extractTextField(R.string.field_max_members)));
            }

        }


    }



    //TODO: fillData with view as parameter
}
