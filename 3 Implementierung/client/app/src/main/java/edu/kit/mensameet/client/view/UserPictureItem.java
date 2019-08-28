package edu.kit.mensameet.client.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.MensaMeetUserPictureList;
import edu.kit.mensameet.client.viewmodel.UserPictureItemHandler;

/**
 * Separate Class to create the view for displaying and selecting the user picture.
 *
 */
public class UserPictureItem extends MensaMeetItem<MensaMeetUserPicture> implements UserPictureAdapter.ItemClickListener {

    private ImageView pictureView;
    private RecyclerView recyclerView;
    private UserPictureAdapter adapter;
    private MensaMeetUserPicture selectedPicture;
    private UserPictureItemHandler handler;

    /**
     * Constructor.
     *
     * @param context Context of parent.
     * @param displayMode   Item display mode.
     * @param objectData User.
     */
    public UserPictureItem(Context context, DisplayMode displayMode, MensaMeetUserPicture objectData) {

        super(context, displayMode, objectData);
        handler = new UserPictureItemHandler(objectData);
        super.initializeHandler(handler);
    }

    /**
     * Creates the view.
     * @return The view.
     */
    @Override
    public View createView() {

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(WIDTH_MATCH_PARENT);
        view.setPadding(context.getResources().getInteger(R.integer.picture_standard_padding),
                context.getResources().getInteger(R.integer.picture_standard_padding),
                context.getResources().getInteger(R.integer.picture_standard_padding),
                context.getResources().getInteger(R.integer.picture_standard_padding));

        MensaMeetUserPicture objectData = handler.getObjectData();

        if (objectData == null) {
            objectData = handler.getDefaultPicture();
        }

        pictureView = new ImageView(context);

        if (displayMode == DisplayMode.SMALL) {

            pictureView.setLayoutParams(new LinearLayout.LayoutParams(
                    context.getResources().getInteger(R.integer.small_picture_width),
                    context.getResources().getInteger(R.integer.small_picture_height)));

        } else {

            pictureView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    context.getResources().getInteger(R.integer.big_picture_height)));

        }

        view.addView(pictureView);

        if (displayMode == DisplayMode.BIG_EDITABLE) {

            pictureView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    pictureView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            });

            // set up the RecyclerView
            recyclerView = new RecyclerView(context);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    context.getResources().getInteger(R.integer.big_picture_height)));
            recyclerView.setItemAnimator(null);

            //LinearSnapHelper snapHelper = new LinearSnapHelper();
            //snapHelper.attachToRecyclerView(recyclerView);

            MensaMeetUserPicture[] items = MensaMeetUserPictureList.getInstance().getPictures();

            adapter = new UserPictureAdapter(context, items);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

            recyclerView.setVisibility(View.GONE);

            view.addView(recyclerView);

        }

        return view;
    }

    @Override
    public void fillObjectData() {

        pictureView.setImageResource(handler.getObjectData().getResourceId());

    }

    @Override
    public void saveEditedObjectData() {

        handler.setObjectData(((UserPictureAdapter)recyclerView.getAdapter()).getSelectedPicture());

    }

    @Override
    public void onItemClick(View view, int position) {
       selectedPicture = adapter.getItem(position);
       recyclerView.setVisibility(View.GONE);
       pictureView.setImageResource(selectedPicture.getResourceId());
       pictureView.setVisibility(View.VISIBLE);
    }
}
