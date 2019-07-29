package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import edu.kit.mensameet.client.model.MensaMeetUserPicture;

/**
 * RecyclerView-Adapter for the UserPicture carousel in UserPictureItem.
 *
 */
public class UserPictureAdapter extends RecyclerView.Adapter<UserPictureAdapter.ViewHolder> {
    protected static final LinearLayout.LayoutParams WIDTH_MATCH_PARENT = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    private Context context;
    private MensaMeetUserPicture[] items;

    private UserPictureAdapter.ItemClickListener mClickListener;
    private int selectedId;

    // data is passed into the constructor
    UserPictureAdapter(Context context, MensaMeetUserPicture[] items) {
        this.context = context;

        setItems(items);

    }

    // inflates the row layout from xml when needed
    @Override
    public UserPictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LinearLayout view = new LinearLayout(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
        view.setPadding(50, 50,50, 50);

        ImageView pictureView = new ImageView(context);
        pictureView.setId(0);
        view.addView(pictureView);

        return new UserPictureAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(UserPictureAdapter.ViewHolder holder, int position) {

        LinearLayout container = (LinearLayout)holder.itemView;
        ImageView pictureView = container.findViewById(0);
        pictureView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        pictureView.setImageResource(items[position].getResourceId());

    }

    /**
     * Gets the selected picture.
     *
     * @return Selected picture.
     */
    public MensaMeetUserPicture getSelectedPicture() {

        return items[selectedId];

    }

    /**
     * Sets the picture to be selected.
     *
     * @param userPicture Picture to be selected.
     */
    public void setSelectedPicture(MensaMeetUserPicture userPicture) {

        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(userPicture)) {
                selectedId = i;
                break;
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return items.length;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            selectedId = getAdapterPosition();
            notifyItemRangeChanged(0, items.length);

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    MensaMeetUserPicture getItem(int id) {
        return items[id];
    }

    public void setItems(MensaMeetUserPicture[] items) {

        if (items == null) {
            this.items = new MensaMeetUserPicture[0];
        } else {
            this.items = items;
        }
    }

     // allows clicks events to be caught
    void setClickListener(UserPictureAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
