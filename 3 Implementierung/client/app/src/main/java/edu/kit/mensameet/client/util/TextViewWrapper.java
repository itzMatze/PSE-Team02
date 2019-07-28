package edu.kit.mensameet.client.util;

import android.widget.TextView;

public class TextViewWrapper {

    private TextView textView;

    public TextViewWrapper(TextView textView) {
        this.textView = textView;
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }
}
