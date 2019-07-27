package edu.kit.mensameet.client.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetUserPicture;
import edu.kit.mensameet.client.model.MensaMeetUserPictureList;

public class UserPictureItem extends MensaMeetItem<MensaMeetUserPicture> implements UserPictureAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private UserPictureAdapter adapter;


    public UserPictureItem(Context context, MensaMeetDisplayMode displayMode, MensaMeetUserPicture objectData) {
        //TODO: Put displayMode into MensaMeetItem as static subclass
        super(context, displayMode, objectData);

    }

    @Override
    public View createView() {

        LinearLayout view = new LinearLayout(context);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(super.WIDTH_MATCH_PARENT);
        view.setPadding(10, 10, 10, 10);

        if (super.displayMode == MensaMeetDisplayMode.BIG_EDITABLE) {

            // set up the RecyclerView
            recyclerView = new RecyclerView(context);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutParams(WIDTH_MATCH_PARENT);
            recyclerView.setItemAnimator(null);

            MensaMeetUserPicture[] items = MensaMeetUserPictureList.getInstance().getPictures();

            adapter = new UserPictureAdapter(context, items);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

            view.addView(recyclerView);

        } else {

            if (objectData == null) {
                objectData = MensaMeetUserPictureList.getInstance().getDefaultPicture();
            }

            ImageView pictureView = new ImageView(context);
            pictureView.setImageResource(objectData.getResourceId());
            view.addView(pictureView);

        }

        return view;
    }

    @Override
    public void fillObjectData() {
        ((UserPictureAdapter)recyclerView.getAdapter()).setSelectedPicture(objectData);
    }

    @Override
    public void saveEditedObjectData() {
        objectData = ((UserPictureAdapter)recyclerView.getAdapter()).getSelectedPicture();

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
