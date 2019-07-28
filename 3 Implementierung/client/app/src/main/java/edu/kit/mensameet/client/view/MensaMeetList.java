package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.ArrayList;
import java.util.List;

public abstract class MensaMeetList<T> implements MensaMeetListAdapter.ItemClickListener {

    protected Context context;
    protected List<T> data;
    protected DisplayMode displayMode;
    protected Boolean dividers;
    protected LinearLayout view;
    protected RecyclerView recyclerView;
    protected MensaMeetListAdapter adapter;
    protected LinearLayoutManager layoutManager;

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    public MensaMeetList(Context context, List<T> data, MensaMeetList.DisplayMode displayMode, Boolean dividers) {

        this.context = context;
        if (data == null) data = new ArrayList<T>();
        this.data = data;
        this.displayMode = displayMode;
        this.dividers = dividers;
        this.view = new LinearLayout(context);
        this.view.setLayoutParams(WIDTH_MATCH_PARENT);
        this.view.setOrientation(LinearLayout.VERTICAL);

        // set up the RecyclerView
        this.recyclerView = new RecyclerView(context);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        this.recyclerView.setLayoutParams(WIDTH_MATCH_PARENT);
        //this.recyclerView.setItemAnimator(null);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        List<MensaMeetItem<T>> items = createItems();
        adapter = new MensaMeetListAdapter<T>(context, items, displayMode);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        if (dividers) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
        }

        this.view.addView(recyclerView);
    }

    public MensaMeetItem<T> getItem(int id) {
        return adapter.getItem(id);
    }

    public int getItemCount() {
        return adapter.getItemCount();
    }

    protected abstract List<MensaMeetItem<T>> createItems();

    public View getView() {
        return view;
    }

    public List<T> getSelectedObjects() {
        return (List<T>) adapter.getSelectedObjects();
    }

    public void setSelectedObjects(List<T> objectList) {

        adapter.setSelectedObjects(objectList);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(context, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public static enum DisplayMode {
        NO_SELECT, SINGLE_SELECT, MULTIPLE_SELECT
    }
}
