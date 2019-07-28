package edu.kit.mensameet.client.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import edu.kit.mensameet.client.model.Gender;
import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.MensaMeetUserPictureList;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.Subject;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.MensaMeetUtil;

public class UserItem extends MensaMeetItem<User> {

    public UserItem(Context context, DisplayMode displayMode, User objectData) {
        //TODO: Put displayMode into MensaMeetItem as static subclass
        super(context, displayMode, objectData);
    }

    @Override
    public View createView() {

        final int BIGGER_FONT_SIZE;
        final int SMALLER_FONT_SIZE;

        if (displayMode == DisplayMode.BIG_EDITABLE || displayMode == DisplayMode.BIG_NOTEDITABLE)  {

            BIGGER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_big);
            SMALLER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_medium);

        } else {

            BIGGER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_medium);
            SMALLER_FONT_SIZE = context.getResources().getInteger(R.integer.font_size_small);

        }

        LinearLayout view = new LinearLayout(context);

        int leftPadding = context.getResources().getInteger(R.integer.item_standard_padding_left_px);
        int topPadding = context.getResources().getInteger(R.integer.item_standard_padding_top_px);
        int rightPadding = context.getResources().getInteger(R.integer.item_standard_padding_right_px);
        int bottomPadding = context.getResources().getInteger(R.integer.item_standard_padding_bottom_px);

        view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        view.setLayoutParams(WIDTH_MATCH_PARENT);

        if (displayMode == DisplayMode.SMALL) {

            view.setOrientation(LinearLayout.HORIZONTAL);

        } else {

            view.setOrientation(LinearLayout.VERTICAL);

        }

        // Field: user picture

        MensaMeetUserPicture profilePicture = MensaMeetUserPictureList.getInstance().getPictureById(objectData.getProfilePictureId());

        UserPictureItem userPictureItem = new UserPictureItem(context, displayMode, profilePicture);
        View itemView = userPictureItem.getView();

        if (displayMode == DisplayMode.SMALL) {

            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

        } else {

            itemView.setLayoutParams(WIDTH_MATCH_PARENT);

        }

        view.addView(itemView);

        LinearLayout descriptionArea = new LinearLayout(context);
        descriptionArea.setOrientation(LinearLayout.VERTICAL);

        if (displayMode == DisplayMode.SMALL) {

            descriptionArea.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        } else {

            descriptionArea.setLayoutParams(WIDTH_MATCH_PARENT);

        }

        // Field: name
        descriptionArea.addView(createTextField(R.string.field_name, WIDTH_MATCH_PARENT, BIGGER_FONT_SIZE));

        // Field: motto
        descriptionArea.addView(createTextField(R.string.field_motto, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        // Field: birth date
        if (displayMode == DisplayMode.BIG_EDITABLE) {

            final LinearLayout chooseDate = createLinkTextField(
                    R.string.field_birth_date,
                    R.string.set_birth_date,
                    WIDTH_MATCH_PARENT,
                    SMALLER_FONT_SIZE);

            chooseDate.setOnClickListener(new View.OnClickListener() {
                private int chosenDay = 1;
                private int chosenMonth = 1;
                private int chosenYear = 1990;

                @Override
                public void onClick(View view) {

                    DatePickerDialog timePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            ((TextView)chooseDate.findViewById((int)R.string.field_birth_date))
                                    .setText(String.format("%02d.%02d.%04d", dayOfMonth, month + 1, year));
                            chosenDay = dayOfMonth;
                            chosenMonth = month + 1;
                            chosenYear = year;
                        }
                    }, chosenYear, chosenMonth - 1, chosenDay);

                    timePickerDialog.show();
                }

            });

            descriptionArea.addView(chooseDate);

        } else if (displayMode == DisplayMode.BIG_NOTEDITABLE) {

            descriptionArea.addView(createTextField(R.string.field_birth_date, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        }

        // Field: gender
        if (displayMode == DisplayMode.BIG_NOTEDITABLE) {

            descriptionArea.addView(createTextField(R.string.field_gender, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        } else if (displayMode == DisplayMode.BIG_EDITABLE) {

            descriptionArea.addView(createLabel(R.string.your_gender, WIDTH_MATCH_PARENT, context.getResources().getInteger(R.integer.font_size_small)));

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int)R.string.field_gender);
            MensaMeetUtil.applyStyle(dropdown, R.style.dropdown_labelled);

            String[] items = new String[Gender.values().length];
            for (int i = 0; i < Gender.values().length; i++) {
                items[i] = context.getResources().getString(Gender.values()[i].id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            descriptionArea.addView(dropdown);

        }

        // Field: status
        if (displayMode == DisplayMode.BIG_NOTEDITABLE) {

            descriptionArea.addView(super.createTextField(R.string.field_status, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        } else if (displayMode == DisplayMode.BIG_EDITABLE) {

            descriptionArea.addView(createLabel(R.string.you_eat, WIDTH_MATCH_PARENT, context.getResources().getInteger(R.integer.font_size_small)));

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int)R.string.field_status);
            MensaMeetUtil.applyStyle(dropdown, R.style.dropdown_labelled);

            String[] items = new String[Status.values().length];
            for (int i = 0; i < Status.values().length; i++) {
                items[i] = context.getResources().getString(Status.values()[i].id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            descriptionArea.addView(dropdown);

        }

        // Field: subject
        if (displayMode == DisplayMode.BIG_NOTEDITABLE) {

            descriptionArea.addView(super.createTextField(R.string.field_subject, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        } else if (displayMode == DisplayMode.BIG_EDITABLE) {

            descriptionArea.addView(createLabel(R.string.your_subject, WIDTH_MATCH_PARENT, context.getResources().getInteger(R.integer.font_size_small)));

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int)R.string.field_subject);
            MensaMeetUtil.applyStyle(dropdown, R.style.dropdown_labelled);

            String[] items = new String[Subject.values().length];
            for (int i = 0; i < Subject.values().length; i++) {
                items[i] = context.getResources().getString(Subject.values()[i].id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            descriptionArea.addView(dropdown);

        }

        view.addView(descriptionArea);

        return view;
    }

    @Override
    public void fillObjectData() {

        super.fillTextField(R.string.field_name, super.objectData.getName());
        super.fillTextField(R.string.field_motto, super.objectData.getMotto());

    }

    @Override
    public void saveEditedObjectData() {

        /*
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

        }*/

    }
}
