package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

import edu.kit.mensameet.client.viewmodel.LoginViewModel;

public class LoginActivity extends MensaMeetActivity {
    private LoginViewModel loginViewModel = new LoginViewModel();
    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = findViewById(R.id.usernameTextInput);
        passwordField = findViewById(R.id.password);
    }

    public void onLoginClick(View v) {
        //frage die Eingaben des Benutzers ab
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        //leite die Eingaben an das ViewModel weiter
        int returnCode = loginViewModel.login(username, password, this);
        //setze abhängig vom Fehlercode eine Fehlermeldung oder bei 0 (erfolgreich) starte die nächste Activity
        if (returnCode == 1) {
            usernameField.setError(getString(R.string.invalid_username_message));
        } else if (returnCode == 2) {
            passwordField.setError(getString(R.string.invalid_password_message));
        } else if (returnCode == 0) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
