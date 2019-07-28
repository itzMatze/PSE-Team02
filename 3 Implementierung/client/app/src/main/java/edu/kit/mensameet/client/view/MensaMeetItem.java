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
import edu.kit.mensameet.client.viewmodel.StateInterface;

public abstract class MensaMeetItem<T> {

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    protected Context context;
    protected T objectData;
    protected DisplayMode displayMode;
    protected MensaMeetItemHandler handler;

    protected ViewGroup.LayoutParams layoutParams = WIDTH_MATCH_PARENT;

    protected View view;

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

    public MensaMeetItemHandler getHandler() {
        return handler;
    }
    protected void processStateChange(Pair<MensaMeetItemHandler, StateInterface> it) {};

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

    protected TextView createLabel(@StringRes int id, ViewGroup.LayoutParams layoutParams, int textSize) {

        TextView textView = new TextView(context);
        textView.setText(id);
        textView.setTextSize(textSize);
        //textView.setLayoutParams(layoutParams);
        MensaMeetUtil.applyStyle(textView, R.style.label_text);

        return textView;
    }

    protected TextView createLabel(String string, ViewGroup.LayoutParams layoutParams, int textSize) {

        TextView textView = new TextView(context);
        textView.setText(string);
        textView.setTextSize(textSize);
        //textView.setLayoutParams(layoutParams);
        MensaMeetUtil.applyStyle(textView, R.style.label_text);

        return textView;
    }


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

    protected String extractSpinnerField(@StringRes int id) {
        View field = view.findViewById((int)id);

        if (field != null) {
            if (field.getClass() == Spinner.class) {
                return Integer.toString(((Spinner)field).getSelectedItemPosition());
            } else if (field.getClass() == TextView.class){
                return extractTextField(id);
            }
        }


        return null;
    }

    protected void fillSublist(@StringRes int id, MensaMeetList sublist) {
        View sublistContainer = view.findViewById((int)id);

        if (sublistContainer != null && sublistContainer.getClass() == LinearLayout.class) {
            LinearLayout linearLayout = (LinearLayout) sublistContainer;
            linearLayout.removeAllViews();
            linearLayout.addView(sublist.getView());
        }
    }

    public PopupWindow getPopUp() {

        return new PopupWindow(view);

    }

    public void setObjectData(T objectData) {
        this.objectData = objectData;
    }

    public T getObjectData() {
        return objectData;
    }

    public void saveEditedObjectData() {};

    public View getView() {
        return view;
    };

    public abstract View createView();

    public abstract void fillObjectData();

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public enum DisplayMode {
        BIG_EDITABLE, BIG_NOTEDITABLE, SMALL
    }
}
