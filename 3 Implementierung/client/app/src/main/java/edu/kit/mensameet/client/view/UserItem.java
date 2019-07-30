package edu.kit.mensameet.client.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import edu.kit.mensameet.client.model.Gender;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.MensaMeetUserPictureList;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.Subject;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.util.MensaMeetUtil;
import edu.kit.mensameet.client.viewmodel.UserItemHandler;

/**
 * The viewer of a single user.
 */
public class UserItem extends MensaMeetItem<User> {

    private UserItemHandler handler;

    private UserPictureItem userPictureItem;

    public UserItem(Context context, DisplayMode displayMode, User objectData) {
        //TODO: Put displayMode into MensaMeetItem as static subclass
        super(context, displayMode, objectData);

        handler = new UserItemHandler(objectData, displayMode);
    }

    @Override
    public View createView() {

        final int BIGGER_FONT_SIZE;
        final int SMALLER_FONT_SIZE;

        if (displayMode == DisplayMode.BIG_EDITABLE || displayMode == DisplayMode.BIG_NOTEDITABLE) {

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

        userPictureItem = new UserPictureItem(context, displayMode, null);
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

            descriptionArea.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
                            ((TextView) chooseDate.findViewById((int) R.string.field_birth_date))
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
            dropdown.setId((int) R.string.field_gender);
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

            descriptionArea.addView(createLabel(R.string.you_are, WIDTH_MATCH_PARENT, context.getResources().getInteger(R.integer.font_size_small)));

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int) R.string.field_status);
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
            dropdown.setId((int) R.string.field_subject);
            MensaMeetUtil.applyStyle(dropdown, R.style.dropdown_labelled);

            String[] items = new String[Subject.values().length];
            for (int i = 0; i < Subject.values().length; i++) {
                items[i] = context.getResources().getString(Subject.values()[i].id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            descriptionArea.addView(dropdown);
        }

        // Field: Delete button

               if (MensaMeetSession.getInstance().getUser() != null && MensaMeetSession.getInstance().getUser().getIsAdmin()) {

                if (displayMode == DisplayMode.SMALL && !objectData.equals(MensaMeetSession.getInstance().getUser())) {

                    final Button deleteButton = new Button(context);
                    deleteButton.setLayoutParams(WIDTH_MATCH_PARENT);
                    deleteButton.setText(R.string.delete_user);
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            handler.deleteUser();
                        }
                    });
                    deleteButton.setBackgroundColor(context.getResources().getColor(R.color.button_color_blue));

                    descriptionArea.addView(deleteButton);
                }
            }




        view.addView(descriptionArea);

        return view;
    }

    /**
     * Get data from object to view.
     */
    @Override
    public void fillObjectData() {

        if(objectData == null) {
            return;
        }

        userPictureItem.setObjectData(
            MensaMeetUserPictureList.getInstance().getPictureById(objectData.getProfilePictureId())
        );
        userPictureItem.fillObjectData();

        fillTextField(R.string.field_name, objectData.getName());

        fillTextField(R.string.field_motto, objectData.getMotto());

        Date birthDate = objectData.getBirthdate();
        if (birthDate != null) {
            fillTextField(R.string.field_birth_date, MensaMeetTime.dateToString(birthDate));
        }

        String argument;

        Gender gender = objectData.getGender();
        if (gender != null) {
            argument = context.getResources().getString(gender.id);
        } else {
            argument = null;
        }
        setSpinnerField(R.string.field_gender, argument);

        Status status = objectData.getStatus();
        if (status != null) {
            argument = context.getResources().getString(status.id);
        } else {
            argument = null;
        }
        setSpinnerField(R.string.field_status, argument);

        Subject subject = objectData.getSubject();
        if (subject != null) {
            argument = context.getResources().getString(subject.id);
        } else {
            argument = null;
        }
        setSpinnerField(R.string.field_subject, argument);
    }

    /**
     * Save data from view to object
     * */
    @Override
    public void saveEditedObjectData() {

        if(objectData == null) {
            return;
        }

        userPictureItem.saveEditedObjectData();
        objectData.setProfilePictureId(userPictureItem.getObjectData().getPictureId());

        objectData.setName(super.extractTextField(R.string.field_name));

        objectData.setMotto(super.extractTextField(R.string.field_motto));

        Date d = MensaMeetTime.stringToDate(super.extractTextField(R.string.field_birth_date));

        objectData.setBirthdate(d);

        objectData.setGender(Gender.valueOfString(context, extractSpinnerField(R.string.field_gender)));

        objectData.setStatus(Status.valueOfString(context, extractSpinnerField(R.string.field_status)));

        objectData.setSubject(Subject.valueOfString(context, extractSpinnerField(R.string.field_subject)));
    }

    @Override
    public UserItemHandler getHandler() {
        return handler;
    }
}
