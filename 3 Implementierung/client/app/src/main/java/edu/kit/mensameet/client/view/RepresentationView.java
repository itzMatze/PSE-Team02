package edu.kit.mensameet.client.view;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;

public class RepresentationView extends AppCompatTextView {

    private String value;

    public RepresentationView(Context context, String value) {
        super(context);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
