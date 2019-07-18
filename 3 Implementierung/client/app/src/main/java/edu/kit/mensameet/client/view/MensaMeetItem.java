package edu.kit.mensameet.client.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public abstract class MensaMeetItem<Object> {

    protected static final LinearLayout.LayoutParams TITLE_PARAMS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
    protected static final LinearLayout.LayoutParams CHECKBOX_PARAMS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    protected static final int BIG_TEXT_SIZE = 20;
    protected static final int SMALL_TEXT_SIZE = 15;

    protected Context context;
    protected Object objectData;
    protected MensaMeetDisplayMode displayMode;

    public MensaMeetItem(Context context, MensaMeetDisplayMode displayMode, Object objectData) {
        this.context = context;
        this.objectData = objectData;
        this.displayMode = displayMode;
    }

    protected View createTextField(int id, ViewGroup.LayoutParams layoutParams, int textSize) {
        if (displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {
            EditText editText = new EditText(context);
            editText.setId(id);
            editText.setLayoutParams(layoutParams);
            editText.setTextSize(textSize);
            return editText;
        } else {
            TextView textView = new TextView(context);
            textView.setId(id);
            textView.setLayoutParams(layoutParams);
            textView.setTextSize(textSize);
            return textView;
        }
    }

    protected View createItemCheckBox(int id, ViewGroup.LayoutParams layoutParams) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setId(GroupItem.ItemElements.CHECKBOX);
        checkBox.setLayoutParams(layoutParams);
        return checkBox;
    }

    protected void fillTextField(MensaMeetListAdapter.ViewHolder holder, int id, String text) {
        View view = holder.getElementById(id);

        if (view.getClass() == EditText.class) {
            EditText editText = (EditText) view;
            editText.setText(text);
        } else if (view.getClass() == TextView.class) {
            TextView textView = (TextView) view;
            textView.setText(text);
        }
    }

    protected void fillSublist(MensaMeetListAdapter.ViewHolder holder, int id, MensaMeetList sublist) {
        View view = holder.getElementById(id);

        if (view.getClass() == LinearLayout.class) {
            LinearLayout linearLayout = (LinearLayout)view;
            linearLayout.removeAllViews();
            linearLayout.addView(sublist.getView());

        }


    }

    public abstract View getView();

    public abstract void fillData(MensaMeetListAdapter.ViewHolder holder);
}
