package edu.kit.mensameet.client.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectLinesAdapter extends RecyclerView.Adapter<SelectLinesAdapter.LineItemViewHolder> {

    /*
    TODO: Edit all later
     */
    //private LineList dataList = new LineList();
    private List<String> dataList;

    // Create new views (invoked by the layout manager)
    @Override
    public SelectLinesAdapter.LineItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //ItemLineBinding binding = ItemLineBinding.inflate(inflater, parent, false)
        //return LineItemViewHolder(binding)
        return null;
    }

    public SelectLinesAdapter(List<String> myDataset) {
        dataList = myDataset;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(LineItemViewHolder holder, int position) {
        // - get element from dataList at this position
        // - replace the contents of the view with that element
        final String item = dataList.get(position);
        /*
        todo: change it after getting mmItem
        holder.getBinding().setItem(item);
        holder.getBinding().getRoot().setOnClickListener((OnClickListener)(new OnClickListener() {
          public final void onClick(View it) {
             WeatherOverviewAdapter.this.getOnItemSelected().invoke(item);
          }
        }));
         */

        holder.textView.setText(dataList.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /*
    public void setLines(LineList lines){
        dataList = lines;
        notifyDataSetChanged();
    }
     */

    public static class LineItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public LineItemViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}
