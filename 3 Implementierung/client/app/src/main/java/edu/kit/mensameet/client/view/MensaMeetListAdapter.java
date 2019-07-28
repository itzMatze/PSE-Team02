package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MensaMeetListAdapter<T> extends RecyclerView.Adapter<MensaMeetListAdapter.ViewHolder> {

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    private Context context;
    private List<MensaMeetItem<T>> items;
    private MensaMeetList.DisplayMode displayMode;

    private MensaMeetListAdapter.ItemClickListener mClickListener;
    private List<Boolean> selectionArray;
    private int selectedId;

    private List<Boolean> expandedArray;

    // data is passed into the constructor
    MensaMeetListAdapter(Context context, List<MensaMeetItem<T>> items, MensaMeetList.DisplayMode displayMode) {
        this.context = context;

        setItems(items);

        this.displayMode = displayMode;

        if (displayMode == MensaMeetList.DisplayMode.MULTIPLE_SELECT) {
            selectionArray = new ArrayList<Boolean>();
            for (int i = 0; i < items.size(); i++) {
                selectionArray.add(i, false);
            }
        } else if (displayMode == MensaMeetList.DisplayMode.SINGLE_SELECT) {
            selectedId = -1;
        }

        expandedArray = new ArrayList<Boolean>();
        for (int i = 0; i < items.size(); i++) {
            this.expandedArray.add(i, false);
        }
    }

    protected void flushSelectionArray() {
        for (int i = 0; i < selectionArray.size(); i++) {
            selectionArray.set(i, false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // inflates the row layout from xml when needed
    @Override
    public MensaMeetListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // here viewType equals position

        LinearLayout view = new LinearLayout(parent.getContext());
        view.setLayoutParams(WIDTH_MATCH_PARENT);

        view.addView(items.get(viewType).getView());

        return new MensaMeetListAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MensaMeetListAdapter.ViewHolder holder, int position) {

        items.get(position).fillObjectData();

        if (displayMode == MensaMeetList.DisplayMode.MULTIPLE_SELECT) {
            if (selectionArray.get(position)) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selection_background));
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.deselection_background));
            }
        } else if (displayMode == MensaMeetList.DisplayMode.SINGLE_SELECT) {
            if (selectedId == position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selection_background));
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.deselection_background));
            }
        }

        View expandArea = items.get(position).getView().findViewById(R.id.expand_area);

        if (expandArea != null) {
            expandArea.setVisibility(expandedArray.get(position) ? View.VISIBLE : View.GONE);
        }
    }

    public List<T> getSelectedObjects() {
        List<T> selectedObjects = new ArrayList<T>();

        if (displayMode == MensaMeetList.DisplayMode.MULTIPLE_SELECT) {
            for (int i = 0; i < items.size(); i++) {
                if (selectionArray.get(i)) {
                    selectedObjects.add(items.get(i).getObjectData());
                }
            }
        } else if (displayMode == MensaMeetList.DisplayMode.SINGLE_SELECT) {
            selectedObjects.add(items.get(selectedId).getObjectData());
        }

        return selectedObjects;
    }

    public void setSelectedObjects(List<T> objectList) {

        List<T> selectCandidates = new ArrayList<>();

        for (int i = 0; i < objectList.size(); i++) {
            selectCandidates.add(objectList.get(i));
        }

        if (displayMode == MensaMeetList.DisplayMode.MULTIPLE_SELECT) {

            flushSelectionArray();

            //Toast.makeText(context, "setselected", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < items.size(); i++) {

                int selectCandidatesSize = selectCandidates.size();

                for (int j = 0; j < selectCandidatesSize; j++) {

                    if (items.get(i).getObjectData().equals(selectCandidates.get(j))) {

                        selectionArray.set(i, true);
                        selectCandidates.remove(j);
                        break;
                    }
                }
            }
        } else if (displayMode == MensaMeetList.DisplayMode.SINGLE_SELECT) {

            if (selectCandidates.size() == 1) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getObjectData().equals(selectCandidates.get(0))) {
                        selectedId = i;
                        break;
                    }
                }
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return items.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public View getElementById(int id) {
            return itemView.findViewById(id);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();

            if (displayMode == MensaMeetList.DisplayMode.MULTIPLE_SELECT) {
                selectionArray.set(position, !selectionArray.get(position));
                notifyItemChanged(position);
            } else if (displayMode == MensaMeetList.DisplayMode.SINGLE_SELECT) {
                selectedId = position;
                notifyItemRangeChanged(0, items.size());
            }

            expandedArray.set(position, !expandedArray.get(position));
            notifyItemChanged(position);

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    MensaMeetItem getItem(int id) {
        return items.get(id);
    }

    public void setItems(List<MensaMeetItem<T>> items) {

        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
    }

    // allows clicks events to be caught
    void setClickListener(MensaMeetListAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
