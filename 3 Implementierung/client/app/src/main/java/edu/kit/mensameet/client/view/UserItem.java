package edu.kit.mensameet.client.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import edu.kit.mensameet.client.model.Gender;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.MensaMeetUserPictureList;
import edu.kit.mensameet.client.model.Status;
import edu.kit.mensameet.client.model.Subject;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public class UserItem extends MensaMeetItem<User> {

    public UserItem(Context context, MensaMeetDisplayMode displayMode, User objectData) {
        //TODO: Put displayMode into MensaMeetItem as static subclass
        super(context, displayMode, objectData);
    }

    @Override
    public View createView() {

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(super.WIDTH_MATCH_PARENT);
        view.setPadding(30, 20, 30, 20);

        MensaMeetUserPicture profilePicture = MensaMeetUserPictureList.getInstance().getPictureById(objectData.getProfilePictureId());
        UserPictureItem userPictureItem = new UserPictureItem(context, null, profilePicture);
        view.addView(userPictureItem.getView());

        view.addView(super.createTextField(R.string.field_name, super.WIDTH_MATCH_PARENT, super.BIG_TEXT_SIZE));

        view.addView(super.createTextField(R.string.field_motto, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        // Field: birth date
        if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            final TextView chooseDate = new TextView(context);
            chooseDate.setTextAppearance(context, R.style.link_text);
            chooseDate.setId((int)R.string.field_birth_date);
            chooseDate.setText(R.string.set_birth_date);

            chooseDate.setOnClickListener(new View.OnClickListener() {
                private int chosenDay = 1;
                private int chosenMonth = 1;
                private int chosenYear = 1990;

                @Override
                public void onClick(View view) {

                    DatePickerDialog timePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            chooseDate.setText(String.format("%02d.%02d.%04d", dayOfMonth, month + 1, year));
                            chosenDay = dayOfMonth;
                            chosenMonth = month + 1;
                            chosenYear = year;
                        }
                    }, chosenYear, chosenMonth - 1, chosenDay);

                    timePickerDialog.show();
                }

            });

            view.addView(chooseDate);
        } else {
            view.addView(super.createTextField(R.string.field_birth_date, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));
        }

        // Field: gender
        if (super.displayMode == MensaMeetDisplayMode.SMALL || super.displayMode == MensaMeetDisplayMode.BIG_NOTEDITABLE) {

            view.addView(super.createTextField(R.string.field_gender, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        } else if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int)R.string.field_gender);

            String[] items = new String[Gender.values().length];
            for (int i = 0; i < Gender.values().length; i++) {
                items[i] = context.getResources().getString(Gender.values()[i].id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            view.addView(dropdown);

        }

        // Field: status
        if (super.displayMode == MensaMeetDisplayMode.SMALL || super.displayMode == MensaMeetDisplayMode.BIG_NOTEDITABLE) {

            view.addView(super.createTextField(R.string.field_status, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        } else if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int)R.string.field_status);

            String[] items = new String[Status.values().length];
            for (int i = 0; i < Status.values().length; i++) {
                items[i] = context.getResources().getString(Status.values()[i].id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            view.addView(dropdown);

        }

        // Field: subject
        if (super.displayMode == MensaMeetDisplayMode.SMALL || super.displayMode == MensaMeetDisplayMode.BIG_NOTEDITABLE) {

            view.addView(super.createTextField(R.string.field_subject, super.WIDTH_MATCH_PARENT, super.SMALL_TEXT_SIZE));

        } else if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            Spinner dropdown = new Spinner(context);
            dropdown.setId((int)R.string.field_subject);

            String[] items = new String[Subject.values().length];
            for (int i = 0; i < Subject.values().length; i++) {
                items[i] = context.getResources().getString(Subject.values()[i].id);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items);

            dropdown.setAdapter(adapter);

            view.addView(dropdown);

        }


        return view;
    }

    @Override
    public void fillObjectData() {
        super.fillTextField(R.string.field_name, super.objectData.getUsername());
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
