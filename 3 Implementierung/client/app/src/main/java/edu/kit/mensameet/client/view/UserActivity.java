package edu.kit.mensameet.client.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import edu.kit.mensameet.client.model.User;

public class UserActivity extends MensaMeetActivity {
    private Drawable editTextBackground;
    private EditText username;
    private EditText motto;
    private EditText something;
    private TextView usernameText;
    private TextView mottoText;
    private TextView somethingText;
    private Button editButton;
    private ToggleButton genderButton;

    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    gotoHome();
                    return true;
                case R.id.navigation_back:
                    gotoHome();
                    return true;
                case R.id.navigation_next:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        username = findViewById(R.id.username);
        motto = findViewById(R.id.motto);
        something = findViewById(R.id.birthDate);
        usernameText = findViewById(R.id.usernameText);
        mottoText = findViewById(R.id.mottoText);
        somethingText = findViewById(R.id.birthDateText);
        editButton = findViewById(R.id.editProfileButton);
        genderButton = findViewById(R.id.genderButton);
        deactivateNavigationButton(findViewById(R.id.navigation_next));
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //entfernt die grüne Färbung in der BottomNavigation Leiste
        navView.setItemIconTintList(null);
    }

    public void onEditProfileClick(View v) {
        if (editButton.getText().equals("Profil bearbeiten")) {
            switchToEdit();
        } else if (editButton.getText().equals("Änderungen speichern")) {
            switchToView();
        }
    }

    private void switchToEdit() {

        //setzt die Eingabefelder auf sichtbar und anwählbar und die Textfelder auf unsichtbar
        setEditable(true);
        //fügt in die Bearbeitungsfelder die vorher gespeicherten Attribute ein, sofern diese schon einmal gesetzt wurden
        username.setText(User.getInstance().getUsername());
        String savedMotto = User.getInstance().getMotto();
        if (savedMotto.length() > 0) {
            motto.setText(savedMotto);
        }
        something.setText("Irgendwas");
        editButton.setText("Änderungen speichern");
    }

    private void switchToView() {
        User user = User.getInstance();
        //setzt die Eingabefelder auf unsichtbar und nicht anwählbar und die Textfelder auf sichtbar
        setEditable(false);
        //fügt den Benutzernamen und falls vorhanden die anderen Attribute ein
        usernameText.setText(User.getInstance().getUsername());
        String savedMotto = User.getInstance().getMotto();
        if (savedMotto.length() > 0) {
            mottoText.setText(savedMotto);
        }
        somethingText.setText("Irgendwas");
        editButton.setText("Profil bearbeiten");
    }

    private void setEditable(boolean isEditable) {
        //setzt die Sichtbarkeit für die TextEdit Elemente und die TextView Elemente, bei den TextEdit Elementen auch noch das enabled Attribut
        int visibilityOfEdit = View.INVISIBLE;
        int visibilityOfView = View.VISIBLE;
        if (isEditable) {
            visibilityOfEdit = View.VISIBLE;
            visibilityOfView = View.INVISIBLE;
        }
        username.setEnabled(isEditable);
        motto.setEnabled(isEditable);
        something.setEnabled(isEditable);
        username.setVisibility(visibilityOfEdit);
        motto.setVisibility(visibilityOfEdit);
        something.setVisibility(visibilityOfEdit);
        usernameText.setVisibility(visibilityOfView);
        mottoText.setVisibility(visibilityOfView);
        somethingText.setVisibility(visibilityOfView);
    }
}
