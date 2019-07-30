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
        handler = new GroupItemHandler(objectData, displayMode);

        if (objectData != null) {
            userList = new UserList(context, objectData.getUsers(), MensaMeetList.DisplayMode.NO_SELECT, false);
        }

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
        view.addView(createTextField(R.string.field_name, WIDTH_MATCH_PARENT, BIGGER_FONT_SIZE));

        // Field: motto
        view.addView(createTextField(R.string.field_motto, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

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

            view.addView(chooseTime);

        } else {

            view.addView(createTextField(R.string.field_time, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

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

                    SelectOneLineDialog selectOneLineDialog = new SelectOneLineDialog(context, new SelectOneLineDialog.OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick(SelectOneLineDialog dialog) {
                            List<Line> selectedLines = dialog.getSelectedLines();
                            if (selectedLines.size() > 0) {
                                ((TextView)chooseLine.findViewById((int)R.string.field_line))
                                        .setText(context.getResources().getString(MealLines.valueOf(selectedLines.get(0).getMealLine()).id));
                            }
                        }
                    });

                    selectOneLineDialog.show();
                }
            });

            view.addView(chooseLine);

        } else {

            view.addView(createTextField(R.string.field_line, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        }

        // Field: Maximum member number

        if (displayMode == DisplayMode.SMALL || displayMode == DisplayMode.BIG_NOTEDITABLE) {

            view.addView(createTextField(R.string.members, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        } else if (displayMode == DisplayMode.BIG_EDITABLE) {

            view.addView(createLabel(R.string.field_max_members, WIDTH_MATCH_PARENT, context.getResources().getInteger(R.integer.font_size_small)));

            //get the spinner from the xml.
            Spinner dropdown = new Spinner(context);
            MensaMeetUtil.applyStyle(dropdown, R.style.dropdown_labelled);

            dropdown.setId((int)R.string.field_max_members);
            int maxMemberMax = context.getResources().getInteger(R.integer.max_member_max);

            String[] items = new String[maxMemberMax];
            for (int i = 0; i < maxMemberMax; i++) {
                items[i] = Integer.toString(i + 1);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            view.addView(dropdown);

        }

        LinearLayout expandArea = new LinearLayout(context);
        expandArea.setOrientation(LinearLayout.VERTICAL);
        expandArea.setId(R.id.expand_area);
        MensaMeetUtil.applyStyle(expandArea, R.style.expand_area);

        // Field: Join button
        if (displayMode == DisplayMode.SMALL) {

            final Button joinButton = new Button(context);
            joinButton.setLayoutParams(WIDTH_MATCH_PARENT);
            joinButton.setText(R.string.join_group);
            joinButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    handler.setGroup(objectData);
                    MensaMeetSession.getInstance().setCreatedGroup(objectData);
                    handler.joinGroup();
                }
            });
            joinButton.setBackgroundColor(context.getResources().getColor(R.color.button_color_blue));

            expandArea.addView(joinButton);
        }

        // Field: Delete button
        if (MensaMeetSession.getInstance().getUser().getIsAdmin()) {

            if (displayMode == DisplayMode.SMALL) {

                final Button deleteButton = new Button(context);
                deleteButton.setLayoutParams(WIDTH_MATCH_PARENT);
                deleteButton.setText(R.string.delete_group);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        handler.deleteGroup();
                    }
                });
                deleteButton.setBackgroundColor(context.getResources().getColor(R.color.button_color_blue));

                expandArea.addView(deleteButton);
            }
        }


        // Field: User list
        if (displayMode != DisplayMode.BIG_EDITABLE) {

            LinearLayout userListContainer = new LinearLayout(context);
            userListContainer.setOrientation(LinearLayout.VERTICAL);
            userListContainer.setLayoutParams(WIDTH_MATCH_PARENT);
            userListContainer.setId((int)R.string.field_member_list);

            expandArea.addView(userListContainer);
        }

        view.addView(expandArea);


        return view;
    }

    /**
     * Puts the data from the object into the view.
     */
    @Override
    public void fillObjectData() {

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
        if (line != null && MealLines.valueOf(line) != null) {
            fillTextField(R.string.field_line, context.getResources().getString(MealLines.valueOf(line).id));
        }

        View maxMembersField = view.findViewById((int)R.string.field_max_members);
        if (maxMembersField != null && maxMembersField.getClass() == Spinner.class) {
            ((Spinner)maxMembersField).setSelection(objectData.getMaxMembers());
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

        if (objectData == null) {
            return;
        }

        objectData.setName(super.extractTextField(R.string.field_name));
        objectData.setMotto(super.extractTextField(R.string.field_motto));
        objectData.setMeetingDate(MensaMeetTime.stringToTime(super.extractTextField(R.string.field_time)));
        objectData.setLine(MealLines.valueOfString(context, super.extractTextField(R.string.field_line)).toString());

        String maxMembers = extractSpinnerField(R.string.field_max_members);

        if (maxMembers == null) {
            objectData.setMaxMembers(0);
        } else {
            objectData.setMaxMembers(Integer.parseInt(maxMembers));
        }

    }

    public UserList getUserList() {
        return userList;
    }

}
