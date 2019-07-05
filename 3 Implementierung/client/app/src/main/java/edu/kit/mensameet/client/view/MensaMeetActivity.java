package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class MensaMeetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onCreate(Bundle savedInstanceState,  boolean showLast, boolean showNext) {
        super.onCreate(savedInstanceState);
    }

}
