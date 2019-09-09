package edu.kit.mensameet.client.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;

import java.util.Date;

import edu.kit.mensameet.client.model.Gender;
import edu.kit.mensameet.client.model.MensaMeetTime;
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

        super(context, displayMode, objectData);

        handler = new UserItemHandler(objectData);
        super.initializeHandler(handler);
    }

    @VisibleForTesting
    public void setHandler(UserItemHandler handler) {
        this.handler = handler;
        super.initializeHandler(handler);
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
            descriptionArea.setPadding(context.getResources().getInteger(R.integer.description_standard_padding_left_px), 0, 0, 0);

        } else {

            descriptionArea.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        // Field: name
        TextView nameField = (TextView)createTextField(R.string.field_name, context.getResources().getString(R.string.field_name) + "*", WIDTH_MATCH_PARENT, BIGGER_FONT_SIZE);
        // TextView is the parent class of EditText and includes it
        nameField.setTypeface(nameField.getTypeface(), Typeface.BOLD);
        descriptionArea.addView(nameField);

        // Field: motto
        descriptionArea.addView(createTextField(R.string.field_motto, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        // Field: birth date

        if (displayMode == DisplayMode.BIG_EDITABLE) {

            final LinearLayout chooseDate = createLinkTextField(
                    R.string.field_birth_date,
                    R.string.set_birth_date,
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0),
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

                    timePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, context.getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((TextView) chooseDate.findViewById((int) R.string.field_birth_date))
                                    .setText(getDefaultValue(R.string.field_birth_date));
                        }
                    });

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

            ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, SpinnerItem.createSpinnerItemArray(Gender.values(), context));

            dropdown.setAdapter(adapter);

            descriptionArea.addView(dropdown);
        }

        // Field: status
        if (displayMode == DisplayMode.BIG_NOTEDITABLE) {

            descriptionArea.addView(super.createTextField(R.string.field_status, WIDTH_MATCH_PARENT, SMALLER_FONT_SIZE));

        } else if (displayMode == DisplayMode.BIG_EDITABLE) {

            descriptionArea.addView(createLabel(context.getResources().getString(R.string.you_are) + "*", WIDTH_MATCH_PARENT, context.getResources().getInteger(R.integer.font_size_small)));

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int) R.string.field_status);
            MensaMeetUtil.applyStyle(dropdown, R.style.dropdown_labelled);

            ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, SpinnerItem.createSpinnerItemArray(Status.values(), context));

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

            ArrayAdapter<SpinnerItem> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, SpinnerItem.createSpinnerItemArray(Subject.values(), context));

            dropdown.setAdapter(adapter);

            descriptionArea.addView(dropdown);
        }

        // Field: Delete button

               if (handler.getCurrentUser() != null && handler.getCurrentUser().getIsAdmin()) {

                if (displayMode == DisplayMode.SMALL && !handler.getObjectData().equals(handler.getCurrentUser())) {

                    final Button deleteButton = new Button(context);
                    deleteButton.setLayoutParams(BUTTON_LAYOUT);
                    deleteButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    deleteButton.setTextColor(context.getResources().getColor(R.color.button_text_color));
                    deleteButton.setTextSize(18);
                    deleteButton.setTypeface(standardFont);
                    deleteButton.setTypeface(deleteButton.getTypeface(), Typeface.BOLD);
                    deleteButton.setAllCaps(false);

                    deleteButton.setText(R.string.delete_user);
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            new AlertDialog.Builder(context)
                                    .setTitle(R.string.delete_user)
                                    .setMessage(R.string.really_delete_user)

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            handler.deleteUser();
                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();


                        }
                    });

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

        User objectData = handler.getObjectData();

        if(objectData == null) {
            return;
        }

        userPictureItem.setObjectData(
            MensaMeetUserPictureList.getInstance().getPictureById(objectData.getProfilePictureId())
        );
        userPictureItem.fillObjectData();

        fillTextField(R.string.field_name, objectData.getName());

        fillTextField(R.string.field_motto, objectData.getMotto());

        String birthDateString = null;
        Date birthDate = objectData.getBirthdate();
        if (birthDate != null) {
            birthDateString = MensaMeetTime.dateToString(birthDate);
        }
        fillTextField(R.string.field_birth_date, birthDateString);

        setSpinnerOrTextField(R.string.field_gender, objectData.getGender());

        setSpinnerOrTextField(R.string.field_status, objectData.getStatus());

        setSpinnerOrTextField(R.string.field_subject, objectData.getSubject());

    }

    /**
     * Save data from view to object
     * */
    @Override
    public void saveEditedObjectData() {

        User objectData = handler.getObjectData();

        if(objectData == null) {
            return;
        }

        userPictureItem.saveEditedObjectData();
        objectData.setProfilePictureId(userPictureItem.getObjectData().getPictureId());

        objectData.setName(super.extractTextField(R.string.field_name));

        objectData.setMotto(super.extractTextField(R.string.field_motto));

        Date d = MensaMeetTime.stringToDate(super.extractTextField(R.string.field_birth_date));

        objectData.setBirthdate(d);

        Gender gender = null;
        try {
            gender = Gender.valueOf(extractSpinnerOrTextField(R.string.field_gender));
        } catch (Exception e) {}
        objectData.setGender(gender);

        Status status = null;
        try {
            status = Status.valueOf(extractSpinnerOrTextField(R.string.field_status));
        } catch (Exception e) {}
        objectData.setStatus(status);

        Subject subject = null;
        try {
            subject = Subject.valueOf(extractSpinnerOrTextField(R.string.field_subject));
        } catch (Exception e) {}
        objectData.setSubject(subject);

        handler.setObjectData(objectData);
    }

    @Override
    public UserItemHandler getHandler() {
        return handler;
    }
}
