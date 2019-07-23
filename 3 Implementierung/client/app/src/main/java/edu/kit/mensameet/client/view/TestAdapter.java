package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    TestAdapter(Context context, List<String> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_layout, parent, false);

        LinearLayout itemLayout = view.findViewById(R.id.itemLayout);

        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams widthMatchParent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout titleAndCheck = new LinearLayout(context);
        titleAndCheck.setOrientation(LinearLayout.HORIZONTAL);

        TextView titleTextView = new TextView(context);
        titleTextView.setId(ItemElements.TITLE);
        titleTextView.setLayoutParams(titleParams);
        titleTextView.setTextSize(20);
        titleAndCheck.addView(titleTextView);

        CheckBox checkItem = new CheckBox(context);
        checkItem.setId(ItemElements.CHECKBOX);
        checkItem.setLayoutParams(checkBoxParams);
        titleAndCheck.addView(checkItem);

        itemLayout.addView(titleAndCheck);

        TextView mottoTextView = new TextView(context);
        mottoTextView.setId(ItemElements.TITLE);
        mottoTextView.setLayoutParams(widthMatchParent);
        mottoTextView.setTextSize(15);
        mottoTextView.setText("Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test");

        itemLayout.addView(mottoTextView);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.titleTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        LinearLayout itemLayout;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(ItemElements.TITLE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ItemElements {
        static final int TITLE = 1;
        static final int CHECKBOX = 2;
    }
}