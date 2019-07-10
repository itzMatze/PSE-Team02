package edu.kit.mensameet.client.view;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

import edu.kit.mensameet.client.viewmodel.RegisterViewModel;

public class RegisterActivity extends MensaMeetActivity {
    private RegisterViewModel registerViewModel = new RegisterViewModel();
    private EditText usernameField;
    private EditText passwordField;
    private EditText passwordConfirmField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameField = findViewById(R.id.usernameTextInput);
        passwordField = findViewById(R.id.password);
        passwordConfirmField = findViewById(R.id.confirmPassword);
    }

    public void onRegisterClick(View v) {
        //frage die Eingaben des Benutzers ab
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = passwordConfirmField.getText().toString();
        //leite die Eingaben an das ViewModel weiter
        int returnCode = registerViewModel.register(username, password, confirmPassword, this);
        //setze abhängig vom Fehlercode eine Fehlermeldung oder bei 0 (erfolgreich) starte die nächste Activity
        if (returnCode == 1) {
            usernameField.setError(getString(R.string.invalid_username_message));
        } else if (returnCode == 2) {
            passwordField.setError(getString(R.string.invalid_password_message));
        } else if (returnCode == 3) {
            passwordConfirmField.setError(getString(R.string.passwords_not_equal_message));
        } else if (returnCode == 0) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
