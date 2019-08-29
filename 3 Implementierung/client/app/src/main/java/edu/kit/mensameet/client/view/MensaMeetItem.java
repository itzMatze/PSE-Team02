package edu.kit.mensameet.client.view;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;

import java.util.HashMap;
import java.util.Map;

import edu.kit.mensameet.client.model.IdEnum;
import edu.kit.mensameet.client.util.MensaMeetUtil;
import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.StateInterface;

/**
 * Abstract basic class for an item of the type T, contains a lot of basic function for data transfer between view and object.
 *
 * @param <T>
 */
public abstract class MensaMeetItem<T> {

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    protected Context context;
    protected DisplayMode displayMode;
    protected MensaMeetItemHandler<T> handler;

    protected Map<Integer, String> representedValues = new HashMap<>();

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
        this.displayMode = displayMode;

    }

    protected void initializeHandler(MensaMeetItemHandler<T> handler) {
        this.handler = handler;
    }

    public void observeHandlerLiveData() {

        // TODO: Generally check when observe is called, in item as well as in activity

        if (handler != null) {
            handler.getEventLiveData().observe((MensaMeetActivity) context, new Observer<Pair<String, StateInterface>>() {
                @Override
                public void onChanged(@Nullable Pair<String, StateInterface> it) {

                    processStateChange(it);

                }
            });
        }
    }

    protected void processStateChange(Pair<String, StateInterface> it) {    }

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
            editText.setTypeface(ResourcesCompat.getFont(context, R.font.enriqueta));
            //MensaMeetUtil.applyStyle(editText, R.style.normal_text);
            return editText;
        } else {
            TextView textView = new TextView(context);
            textView.setId((int)id);
            textView.setTextSize(textSize);
            textView.setLayoutParams(layoutParams);
            MensaMeetUtil.applyStyle(textView, R.style.normal_text);
            textView.setTypeface(ResourcesCompat.getFont(context, R.font.enriqueta));

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
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.enriqueta));

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
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.enriqueta));

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
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.enriqueta));

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
            if (field instanceof TextView) {
                TextView textView = (TextView) field;
                textView.setText(text);
            }
        }

    }

    /**
     * Setting method for a spinner data selector or a TextView representation thereof in a read only mode.
     *
     * @param id Id of the spinner field and its string description.
     * @param value String to be filled.
     */
    protected void setSpinnerOrTextField(@StringRes int id, String value) {

        View field = view.findViewById((int)id);
        if (field != null) {
            if (field instanceof Spinner) {
                ((Spinner) field).setSelection(
                        MensaMeetUtil.getIndexInSpinner((Spinner) field, value));
            } else if (field instanceof TextView) {
                ((TextView) field).setText(value);
            }
        }
    }

    /**
     * Setting method for a spinner data selector or a TextView representation thereof in a read only mode.
     *
     * @param id Id of the spinner field and its string description.
     * @param idEnum IdEnum to get the values from.
     */
    protected void setSpinnerOrTextField(@StringRes int id, IdEnum idEnum) {

        String value;
        String representation;

        if (idEnum != null) {
            value = idEnum.toString();
            representation = context.getResources().getString(idEnum.getId());
        } else {
            value = null;
            representation = null;
        }

        View field = view.findViewById((int)id);
        if (field != null) {
            if (field instanceof Spinner) {
                ((Spinner) field).setSelection(
                        MensaMeetUtil.getIndexInSpinner((Spinner) field, value));
            } else if (field instanceof TextView) {
                // If TextView to be set, put representation there and value as representedValue
                ((TextView)field).setText(representation);
                setRepresentedValue(id, value);
            }
        }

    }



    /**
     * Extracts a string from a text field. If represented value for id is set, return this instead.
     *
     * @param id Id of the text field and its string description.
     * @return Text content of the field.
     */
    protected String extractTextField(@StringRes int id) {

        View field = view.findViewById((int)id);
        String text = null;

        if (field != null) {
            if (field instanceof TextView) {
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
    protected String extractSpinnerOrTextField(@StringRes int id) {
        View field = view.findViewById((int)id);

        if (field != null) {
            if (field instanceof Spinner) {

                Object item = ((Spinner)field).getSelectedItem();
                if (item != null) {
                    if (item instanceof SpinnerItem) {
                        return ((SpinnerItem)((Spinner)field).getSelectedItem()).getValue();
                    } else if (item instanceof String) {
                        return (String)item;
                    }
                }


            } else if (field instanceof TextView){
                if (representedValueSet(id)) {
                    return getRepresentedValue(id);
                } else {
                    return extractTextField(id);
                }
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

        if (sublistContainer != null && sublistContainer instanceof LinearLayout) {
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
        if (handler != null) {
            handler.setObjectData(objectData);
        }

    }

    public T getObjectData() {
        if (handler != null) {
            return (T)handler.getObjectData();
        }
        return null;
    }

    /**
     * Hook method for saving the object data from the view.
     */
    public void saveEditedObjectData() {};

    public View getView() {
        if (view == null) {
            view = createView();
        }
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

    protected void setRepresentedValue(@StringRes int id, String value) {
        representedValues.put(new Integer(id), value);
    }

    protected String getRepresentedValue(@StringRes int id) {
        if (representedValueSet(id)) {
            return representedValues.get(new Integer(id));
        }

        return null;
    }

    protected Boolean representedValueSet(@StringRes int id) {
        return representedValues.containsKey(new Integer(id));
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
