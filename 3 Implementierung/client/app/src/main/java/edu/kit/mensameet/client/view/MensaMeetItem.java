package edu.kit.mensameet.client.view;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import edu.kit.mensameet.client.viewmodel.MensaMeetItemHandler;
import edu.kit.mensameet.client.viewmodel.MensaMeetViewModel;
import edu.kit.mensameet.client.viewmodel.StateInterface;

public abstract class MensaMeetItem<T> {

    protected static final LinearLayout.LayoutParams NAME_PARAMS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
    protected static final LinearLayout.LayoutParams CHECKBOX_PARAMS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    protected static final int BIG_TEXT_SIZE = 20;
    protected static final int SMALL_TEXT_SIZE = 15;

    protected Context context;
    protected T objectData;
    protected MensaMeetDisplayMode displayMode;
    protected MensaMeetItemHandler handler;

    protected View view;

    public MensaMeetItem(Context context, MensaMeetDisplayMode displayMode, T objectData) {
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
        if (displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {
            EditText editText = new EditText(context);
            editText.setId((int)id);
            editText.setHint(id);
            editText.setLayoutParams(layoutParams);
            editText.setTextAppearance(context, R.style.normal_text);
            return editText;
        } else {
            TextView textView = new TextView(context);
            textView.setId((int)id);
            //textView.setLayoutParams(layoutParams);
            textView.setTextAppearance(context, R.style.normal_text);
            return textView;
        }
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

    protected void fillSublist(@StringRes int id, MensaMeetList sublist) {
        View sublistContainer = view.findViewById((int)id);

        if (sublistContainer != null && sublistContainer.getClass() == LinearLayout.class) {
            LinearLayout linearLayout = (LinearLayout) sublistContainer;
            linearLayout.removeAllViews();
            linearLayout.addView(sublist.getView());
        }
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

    public enum DisplayMode {
        BIG_EDITABLE, BIG_NOTEDITABLE, SMALL
    }
}
