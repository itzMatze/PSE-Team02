package edu.kit.mensameet.client.view;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.VisibleForTesting;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MealLines;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.util.MensaMeetUtil;
import edu.kit.mensameet.client.viewmodel.GroupItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * View representation of a single group, either as full-page or list element version.
 */
public class GroupItem extends MensaMeetItem<Group> {

    private int MAX_MEMBERS_MIN = 2;
    private int MAX_MEMBERS_MAX = 20; // minimum: 2

    private UserList userList;
    private GroupItemHandler handler;

    /**
     * Constructor.
     *
     * @param context Context of the parent.
     * @param displayMode Item display mode.
     * @param objectData The Group.
     */
    public GroupItem(Context context, DisplayMode displayMode, Group objectData) {
        super(context, displayMode, objectData);

        // Bind the handler.
        handler = new GroupItemHandler(objectData);
        super.initializeHandler(handler);

        if (objectData != null) {
            userList = new UserList(context, objectData.getUsers(), MensaMeetList.DisplayMode.NO_SELECT, false, false);
        }

    }

    @VisibleForTesting
    public void setHandler(GroupItemHandler handler) {
        this.handler = handler;
        super.initializeHandler(handler);
    }

    /**
     * Override necessary for stability reasons.
     */
    @Override
    public GroupItemHandler getHandler() {
        return handler;
    }

    /**
     * Creates the view, depending on preferences.
     *
     * @return The view.
     */
    @Override
    public View createView() {

        final int BIGGER_FONT_SIZE;
        final int SMALLER_FONT_SIZE;

        // Relative setting of fonts.
        if (displayMode == DisplayMode.BIG_EDITABLE || displayMode == DisplayMode.BIG_NOTEDITABLE)  {

            BIGGER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_big);
            SMALLER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_medium);

        } else {

            BIGGER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_medium);
            SMALLER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_small);

        }

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(layoutParams);

        int leftPadding = context.getResources().getInteger(R.integer.item_standard_padding_left_px);
        int topPadding = context.getResources().getInteger(R.integer.item_standard_padding_top_px);
        int rightPadding = context.getResources().getInteger(R.integer.item_standard_padding_right_px);
        int bottomPadding = context.getResources().getInteger(R.integer.item_standard_padding_bottom_px);

        view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        // Field: name
        TextView nameField = (TextView)createTextField(R.string.field_name, context.getResources().getString(R.string.field_name) + "*", WIDTH_MATCH_PARENT, BIGGER_FONT_SIZE);
        // TextView is the parent class of EditText and includes it
        nameField.setTypeface(nameField.getTypeface(), Typeface.BOLD);
        view.addView(nameField);

        // Field: motto
        view.addView(createTextField(R.string.field_motto, context.getResources().getString(R.string.field_motto) + "*", WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        LinearLayout infoBar = new LinearLayout(context);
        if (displayMode == DisplayMode.BIG_EDITABLE) {
            infoBar.setOrientation(LinearLayout.VERTICAL);
        } else {
            infoBar.setOrientation(LinearLayout.HORIZONTAL);
        }

        // Field: line
        if (displayMode == DisplayMode.BIG_EDITABLE) {

            final LinearLayout chooseLine = createLinkTextField(
                    R.string.field_line,
                    R.string.selectLine,
                    WIDTH_MATCH_PARENT,
                    SMALLER_FONT_SIZE);

            chooseLine.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SelectOneLineDialog selectOneLineDialog = new SelectOneLineDialog(context, getRepresentedValue(R.string.field_line), new SelectOneLineDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick(SelectOneLineDialog dialog) {
                            List<Line> selectedLines = dialog.getSelectedLines();
                            if (selectedLines.size() > 0) {

                                String mealLine = selectedLines.get(0).getMealLine();
                                ((TextView)chooseLine.findViewById((int)R.string.field_line))
                                        .setText(MealLines.valueOf(mealLine).getId());
                                setRepresentedValue(R.string.field_line, mealLine);

                            }
                        }
                    });

                    selectOneLineDialog.show();

                    TextView msgTxt = (TextView) selectOneLineDialog.findViewById(android.R.id.message);
                    msgTxt.setTextSize(16.0f);

                    /*
                    // Title font must be set after show();
                    TextView title = selectOneLineDialog.getWindow().findViewById(android.R.id.message);
                    title.setTypeface(ResourcesCompat.getFont(context, R.font.enriqueta));
                    selectOneLineDialog.getWindow().add*/
                }
            });

            infoBar.addView(chooseLine);

        } else {

            infoBar.addView(createTextField(R.string.field_line, WIDTH_WRAP_CONTENT, SMALLER_FONT_SIZE));

        }

        // Field: time
        if (displayMode == DisplayMode.BIG_EDITABLE) {

            final LinearLayout chooseTime = createLinkTextField(
                    R.string.field_time,
                    R.string.chooseTime,
                    WIDTH_MATCH_PARENT,
                    SMALLER_FONT_SIZE);

            chooseTime.setOnClickListener(new View.OnClickListener() {

                private int chosenHour = 12;
                private int chosenMinutes = 0;

                @Override
                public void onClick(View view) {

                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            context,
                            android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                            new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                            ((TextView)chooseTime.findViewById((int)R.string.field_time))
                                    .setText(String.format("%02d:%02d", hourOfDay, minutes));
                            chosenHour = hourOfDay;
                            chosenMinutes = minutes;
                        }
                    }, chosenHour, chosenMinutes, true);

                    timePickerDialog.show();
                }

            });

            infoBar.addView(chooseTime);

        } else {

            infoBar.addView(createTextField(R.string.field_time, WIDTH_WRAP_CONTENT, SMALLER_FONT_SIZE));

        }

        // Field: Maximum member number

        if (displayMode == DisplayMode.SMALL || displayMode == DisplayMode.BIG_NOTEDITABLE) {

            infoBar.addView(createTextField(R.string.members, WIDTH_WRAP_CONTENT, SMALLER_FONT_SIZE));

        } else if (displayMode == DisplayMode.BIG_EDITABLE) {

            infoBar.addView(createLabel(context.getResources().getString(R.string.field_max_members) + "*", WIDTH_MATCH_PARENT, context.getResources().getInteger(R.integer.font_size_small)));

            //get the spinner from the xml.
            Spinner dropdown = new Spinner(context);
            MensaMeetUtil.applyStyle(dropdown, R.style.dropdown_labelled);

            dropdown.setId((int)R.string.field_max_members);

            String[] items = new String[MAX_MEMBERS_MAX - 1];
            for (int i = 0; i < MAX_MEMBERS_MAX - 1 ; i++) {
                items[i] = Integer.toString(i + 2);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            infoBar.addView(dropdown);

        }

        view.addView(infoBar);

        // Hint: Meeting point
        if (displayMode == DisplayMode.BIG_NOTEDITABLE) {
            TextView meeting = new TextView(context);
            MensaMeetUtil.applyStyle(meeting, R.style.normal_text);
            meeting.setText(R.string.meeting_point);
            meeting.setTypeface(standardFont);
            meeting.setTextSize(12);
            view.addView(meeting);
        }

        LinearLayout expandArea = new LinearLayout(context);
        expandArea.setOrientation(LinearLayout.VERTICAL);
        expandArea.setId(R.id.expand_area);
        MensaMeetUtil.applyStyle(expandArea, R.style.expand_area);

        // Field: Join button
        if (displayMode == DisplayMode.SMALL && (handler.getObjectData().getMaxMembers() > handler.getObjectData().getUsers().size())) {

            final Button joinButton = new Button(context);
            joinButton.setLayoutParams(BUTTON_LAYOUT);
            joinButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            joinButton.setTextColor(context.getResources().getColor(R.color.button_text_color));
            joinButton.setTextSize(18);
            joinButton.setTypeface(standardFont);
            joinButton.setTypeface(joinButton.getTypeface(), Typeface.BOLD);
            joinButton.setAllCaps(false);

            joinButton.setText(R.string.join_group);
            joinButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    handler.joinGroup();
                }
            });

            expandArea.addView(joinButton);
        }

        // Field: Delete button
        if (handler.getCurrentUser().getIsAdmin()) {

            if (displayMode == DisplayMode.SMALL || displayMode == DisplayMode.BIG_NOTEDITABLE) {

                final Button deleteButton = new Button(context);
                deleteButton.setLayoutParams(BUTTON_LAYOUT);
                deleteButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                deleteButton.setTextColor(context.getResources().getColor(R.color.button_text_color));
                deleteButton.setTextSize(18);
                deleteButton.setTypeface(standardFont);
                deleteButton.setTypeface(deleteButton.getTypeface(), Typeface.BOLD);
                deleteButton.setAllCaps(false);

                deleteButton.setText(R.string.delete_group);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        new AlertDialog.Builder(context)
                                .setTitle(R.string.delete_group)
                                .setMessage(R.string.really_delete_group)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        handler.deleteGroup();
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                });

                expandArea.addView(deleteButton);
            }
        }


        // Field: User list
        if (displayMode != DisplayMode.BIG_EDITABLE) {

            NestedScrollView nestedScrollView = new NestedScrollView(context);
            nestedScrollView.setLayoutParams(WIDTH_HEIGHT_MATCH_PARENT);
            nestedScrollView.setId((int)R.string.field_member_list);
            expandArea.addView(nestedScrollView);
        }

        view.addView(expandArea);


        return view;
    }

    /**
     * Puts the data from the object into the view.
     */
    @Override
    public void fillObjectData() {

        Group objectData = handler.getObjectData();

        if (objectData == null) {
            return;
        }

        fillTextField(R.string.field_name, objectData.getName());
        fillTextField(R.string.field_motto, objectData.getMotto());

        Date time = objectData.getMeetingDate();
        if (time != null) {
            fillTextField(R.string.field_time, MensaMeetTime.timeToString(time));
        }

        String line = objectData.getLine();
        MealLines mealLine = null;
        try {
            mealLine = MealLines.valueOf(line);
        } catch (Exception e) {
            // not corresponding mealLine
        }

        if (line != null && mealLine != null) {
            fillTextField(R.string.field_line, context.getResources().getString(mealLine.getId()));
            setRepresentedValue(R.string.field_line, line);
        }

        View maxMembersField = view.findViewById((int)R.string.field_max_members);
        if (maxMembersField != null && maxMembersField instanceof Spinner) {
            int spinnerIndex = objectData.getMaxMembers() - 2; // value a -> index a-2
            ((Spinner)maxMembersField).setSelection(spinnerIndex);
        }

        View membersField = view.findViewById((int)R.string.members);
        if (membersField != null) {

            int userNumber;
            if (objectData.getUsers() != null) {
                userNumber = objectData.getUsers().size();
            } else {
                userNumber = 0;
            }

            String occupation = userNumber + "/" +
                    objectData.getMaxMembers();

            fillTextField(R.string.members, occupation);
        }

        fillSublist(R.string.field_member_list, userList);

    }

    /**
     * Puts the data from the view into the object.
     */
    @Override
    public void saveEditedObjectData() {

        Group objectData = handler.getObjectData();

        if (objectData == null) {
            return;
        }

        objectData.setName(extractTextField(R.string.field_name));
        objectData.setMotto(extractTextField(R.string.field_motto));
        objectData.setMeetingDate(MensaMeetTime.stringToTime(extractTextField(R.string.field_time)));
        objectData.setLine(getRepresentedValue(R.string.field_line));

        String maxMembers = extractSpinnerOrTextField(R.string.field_max_members);

        if (maxMembers == null) {
            objectData.setMaxMembers(MAX_MEMBERS_MIN);
        } else {
            objectData.setMaxMembers(Integer.parseInt(maxMembers));
        }

        handler.setObjectData(objectData);

    }

    public UserList getUserList() {
        return userList;
    }

}
