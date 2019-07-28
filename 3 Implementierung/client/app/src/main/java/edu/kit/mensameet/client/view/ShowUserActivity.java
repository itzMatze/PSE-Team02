package edu.kit.mensameet.client.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.User;
import edu.kit.mensameet.client.view.databinding.ActivityUserBinding;
import edu.kit.mensameet.client.viewmodel.UserViewModel;

public class ShowUserActivity extends MensaMeetActivity {

        private UserViewModel viewModel;
        private ActivityUserBinding binding;
        private UserItem userItem;
        private User user;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            setContentView(R.layout.activity_show_user);

            LinearLayout container = findViewById(R.id.container);

            user = MensaMeetSession.getInstance().getUserToShow();

            userItem = new UserItem(this, MensaMeetItem.DisplayMode.BIG_NOTEDITABLE, user);

            container.addView(userItem.getView());

            super.onCreate(savedInstanceState);

            if (buttonNext != null) {
                if (buttonHome != null) {
                    buttonHome.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                }
                buttonNext.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClickBack() {
            onBackPressed();
        }

}
