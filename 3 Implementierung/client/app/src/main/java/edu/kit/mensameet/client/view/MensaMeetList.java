package edu.kit.mensameet.client.view;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public abstract class MensaMeetList<Object> implements MensaMeetListAdapter.ItemClickListener {

    protected Context context;
    protected List<Object> data;
    protected Boolean checkBoxes;
    protected LinearLayout view;
    protected RecyclerView recyclerView;
    protected MensaMeetListAdapter adapter;
    protected LinearLayoutManager layoutManager;

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    public MensaMeetList(Context context, List<Object> data, Boolean checkBoxes) {

        this.context = context;
        if (data == null) data = new ArrayList<Object>();
        this.data = data;
        this.checkBoxes = checkBoxes;
        this.view = new LinearLayout(context);
        this.view.setLayoutParams(WIDTH_MATCH_PARENT);
        this.view.setOrientation(LinearLayout.VERTICAL);

        // set up the RecyclerView
        this.recyclerView = new RecyclerView(context);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerView.setLayoutParams(WIDTH_MATCH_PARENT);

        List<MensaMeetItem> items = createItems();
        adapter = new MensaMeetListAdapter(context, items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        this.view.addView(recyclerView);
    }

    protected abstract List<MensaMeetItem> createItems();

    public View getView() {
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(context, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
