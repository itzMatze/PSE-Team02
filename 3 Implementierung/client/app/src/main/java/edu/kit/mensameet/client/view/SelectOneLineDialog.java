package edu.kit.mensameet.client.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;

/**
 * Dialog for selecting mensa lines, used in group creation form.
 */
public class SelectOneLineDialog extends AlertDialog {

    SelectOneLineDialog.OnPositiveClickListener listener;

    protected SelectOneLineDialog(Context context, SelectOneLineDialog.OnPositiveClickListener listener) {
        super(context);
        this.listener = listener;
    }

    List<Line> selectedLines = new ArrayList<Line>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        final LineList lineList = new LineList(getContext(), new ArrayList<Line>(Arrays.asList(MensaMeetSession.getInstance().getMensaData().getLines())), MensaMeetList.DisplayMode.SINGLE_SELECT, true);

        setTitle(R.string.selectLine);
        setView(lineList.getView());
        setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK, so save the selectedItems results somewhere
                // or return them to the component that opened the dialog
                selectedLines = lineList.getSelectedObjects();
                listener.onPositiveClick(SelectOneLineDialog.this);
            }
        });

        setButton(DialogInterface.BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        super.onCreate(savedInstanceState);
    }

    public List<Line> getSelectedLines() {
        return selectedLines;
    }

    public interface OnPositiveClickListener {
        void onPositiveClick(SelectOneLineDialog dialog);
    }
}
