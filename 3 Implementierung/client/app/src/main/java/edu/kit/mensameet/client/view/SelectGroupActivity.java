package edu.kit.mensameet.client.view;

import android.app.ListActivity;
import android.os.Bundle;

public class SelectGroupActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_lines);
    }
}
