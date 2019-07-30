package edu.kit.mensameet.client.view;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.Observer;

import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.util.MensaMeetUtil;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.SelectLinesViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Abstract basic class for an item of the type T, contains a lot of basic function for data transfer between view and object.
 *
 * @param <T>
 */
public abstract class MensaMeetItem<T> {

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    protected Context context;
    protected T objectData;
    protected DisplayMode displayMode;
    protected MensaMeetItemHandler handler;

    protected ViewGroup.LayoutParams layoutParams = WIDTH_MATCH_PARENT;

    protected View view;

    /**
     * Constructor.
     *
     * @param context Context of the parent.
     * @param displayMode   Item display mode.
     * @param objectData The data object of type T.
     */
    public MensaMeetItem(Context context, DisplayMode displayMode, T objectData) {
        this.context = context;
        this.objectData = objectData;
        this.displayMode = displayMode;
        this.view = createView();

        if (handler != null) {
            handler.getUiEventLiveData().observe((MensaMeetActivity) context, new Observer<Pair<MensaMeetItemHandler, StateInterface>>() {
                @Override
                public void onChanged(@Nullable Pair<MensaMeetItemHandler, StateInterface> it) {

                    processStateChange(it);

                }
            });
        }

    }

    protected void processStateChange(Pair<MensaMeetItemHandler, StateInterface> it) {    }

    public MensaMeetItemHandler getHandler() {
        return handler;
    }

    /**
     * Creates a text field, which can be either an EditText or a TextView, depending on the preference.
     *
     * @param id Id of the string resource with a description of the field, at the same time used as id for the field itself.
     * @param layoutParams Layout parameters.
     * @param textSize Text size.
     * @return The text field as view.
     */

    protected View createTextField(@StringRes int id, ViewGroup.LayoutParams layoutParams, int textSize) {
        if (displayMode == DisplayMode.BIG_EDITABLE) {
            EditText editText = new EditText(context);
            editText.setId((int)id);
            editText.setHint(id);
            editText.setTextSize(textSize);
            editText.setLayoutParams(layoutParams);
            //MensaMeetUtil.applyStyle(editText, R.style.normal_text);
            return editText;
        } else {
            TextView textView = new TextView(context);
            textView.setId((int)id);
            textView.setTextSize(textSize);
            textView.setLayoutParams(layoutParams);
            MensaMeetUtil.applyStyle(textView, R.style.normal_text);
            return textView;
        }
    }

    /**
     * Create a clickable link TextView.
     *
     * @param id String resource and element id at the same time.
     * @param defaultTextId Id of the default text to be displayed.
     * @param layoutParams Layout parameters.
     * @param textSize Text size.
     * @return The text field as view.
     */
    protected LinearLayout createLinkTextField(@StringRes int id, @StringRes int defaultTextId, ViewGroup.LayoutParams layoutParams, int textSize) {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(layoutParams));

        int leftPadding = context.getResources().getInteger(R.integer.link_text_padding_left_px);
        int topPadding = context.getResources().getInteger(R.integer.link_text_padding_top_px);
        int rightPadding = context.getResources().getInteger(R.integer.link_text_padding_right_px);
        int bottomPadding = context.getResources().getInteger(R.integer.link_text_padding_bottom_px);

        linearLayout.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.text_select));
        MensaMeetUtil.applyStyle(linearLayout, R.style.link_text);

        TextView textView = new TextView(context);
        textView.setId((int)id);
        textView.setTextSize(textSize);
        textView.setLayoutParams(WIDTH_MATCH_PARENT);
        textView.setText(defaultTextId);

        linearLayout.addView(textView);

        return linearLayout;

    }

    /**
     * Create a description label.
     *
     * @param id String id.
     * @param layoutParams Layout parameters.
     * @param textSize Text size.
     * @return Label as view.
     */
    protected TextView createLabel(@StringRes int id, ViewGroup.LayoutParams layoutParams, int textSize) {

        TextView textView = new TextView(context);
        textView.setText(id);
        textView.setTextSize(textSize);
        //textView.setLayoutParams(layoutParams);
        MensaMeetUtil.applyStyle(textView, R.style.label_text);

        return textView;
    }

    /**
     * Creates a label using a string instead of a string id.
     *
     * @param string String to be displayed.
     * @param layoutParams Layout parameters.
     * @param textSize Text size.
     * @returnv Label as view.
     */
    protected TextView createLabel(String string, ViewGroup.LayoutParams layoutParams, int textSize) {

        TextView textView = new TextView(context);
        textView.setText(string);
        textView.setTextSize(textSize);
        //textView.setLayoutParams(layoutParams);
        MensaMeetUtil.applyStyle(textView, R.style.label_text);

        return textView;
    }

    /**
     * Filling method for a text field.
     *
     * @param id Id of string description and text field.
     * @param text Text to be filled in.
     */
    protected void fillTextField(@StringRes int id, String text) {
        View field = view.findViewById((int)id);

        if (field != null) {
            if (field.getClass() == EditText.class) {
                EditText editText = (EditText) field;
                editText.setText(text);
            } else if (field.getClass() == TextView.class) {
                TextView textView = (TextView) field;
                textView.setText(text);
            }
        }

    }

    /**
     * Setting method for a spinner data selector or a TextView representation thereof in a read only mode.
     *
     * @param id Id of the spinner field and its string description.
     * @param string String to be filled.
     */
    protected void setSpinnerField(@StringRes int id, String string) {

        View field = view.findViewById((int)id);
        if (field != null) {
            if (field.getClass() == Spinner.class) {
                ((Spinner) field).setSelection(
                        MensaMeetUtil.getIndexInSpinner((Spinner) field, string));
            } else if (field.getClass() == TextView.class) {
                ((TextView) field).setText(string);
            }
        }
    }

    /**
     * Extracts a string from a text field.
     *
     * @param id Id of the text field and its string description.
     * @return Text content of the field.
     */
    protected String extractTextField(@StringRes int id) {
        View field = view.findViewById((int)id);
        String text = null;

        if (field != null) {
            if (field.getClass() == EditText.class) {
                EditText editText = (EditText) field;
                text = editText.getText().toString();
            } else if (field.getClass() == TextView.class) {
                TextView textView = (TextView) field;
                text = textView.getText().toString();
            }
        }

        return text;
    }

    /**
     * Extracts a string from a spinner or its TextView replacement.
     *
     * @param id Id of the spinner field and its string description.
     * @return Text content of the field.
     */
    protected String extractSpinnerField(@StringRes int id) {
        View field = view.findViewById((int)id);

        if (field != null) {
            if (field.getClass() == Spinner.class) {
                return ((Spinner)field).getSelectedItem().toString();
            } else if (field.getClass() == TextView.class){
                return extractTextField(id);
            }
        }


        return null;
    }

    /**
     * Method to fill in a sublist by putting in its view.
     *
     * @param id Id of the sublist placeholder.
     * @param sublist The sublist to be put in.
     */
    protected void fillSublist(@StringRes int id, MensaMeetList sublist) {
        View sublistContainer = view.findViewById((int)id);

        if (sublistContainer != null && sublistContainer.getClass() == LinearLayout.class) {
            LinearLayout linearLayout = (LinearLayout) sublistContainer;
            linearLayout.removeAllViews();
            linearLayout.addView(sublist.getView());
        }
    }

    /**
     *  Sets the object data.
     *
     * @param objectData The object data.
     */
    public void setObjectData(T objectData) {
        this.objectData = objectData;
    }

    public T getObjectData() {
        return objectData;
    }

    /**
     * Hook method for saving the object data from the view.
     */
    public void saveEditedObjectData() {};

    public View getView() {
        return view;
    };

    /**
     * Hook method for creating the view representation of the item.
     *
     * @return The view.
     */
    public abstract View createView();

    /**
     * Hook method for filling the object data into the view.
     */
    public abstract void fillObjectData();

    /**
     * Sets the general layout parameters for the view.
     *
     * @param layoutParams Layout parameters.
     */
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    /**
     * Display modes of Items.
     */
    public enum DisplayMode {
        /**
         * Big and editable.
         */
        BIG_EDITABLE,
        /**
         * Big and not editable.
         */
        BIG_NOTEDITABLE,
        /**
         * Small and not editable.
         */
        SMALL
    }
}
