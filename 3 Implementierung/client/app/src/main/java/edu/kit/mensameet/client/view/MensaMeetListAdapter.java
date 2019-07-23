package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MensaMeetListAdapter extends RecyclerView.Adapter<MensaMeetListAdapter.ViewHolder> {

    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    private List<MensaMeetItem> items;
    private LayoutInflater mInflater;
    private MensaMeetListAdapter.ItemClickListener mClickListener;
    private Context context;

    private Boolean checkBoxes = false;

    // data is passed into the constructor
    MensaMeetListAdapter(Context context, List<MensaMeetItem> items) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.items = items;
    }

    // inflates the row layout from xml when needed
    @Override
    public MensaMeetListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //  View view = mInflater.inflate(R.layout.list_item_layout, parent, false);
        //  LinearLayout itemLayout = view.findViewById(R.id.itemLayout);

        LinearLayout view = new LinearLayout(parent.getContext());
        view.setLayoutParams(WIDTH_MATCH_PARENT);

        view.addView(items.get(0).getView());

        return new MensaMeetListAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MensaMeetListAdapter.ViewHolder holder, int position) {

        items.get(position).fillData(holder);

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
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Object getItem(int id) {
        return items.get(id);
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
