package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Main screen. Reads the municipality typed by the user, validates and normalises it with
 * {@link TextProcessor} and, when it is supported, opens {@link Municipality} with an
 * explicit Intent.
 *
 * <p>Student: Deng ZiChen (20243006955)</p>
 */
public class MainActivity extends AppCompatActivity {

    private EditText municipalityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        applySystemBarInsets();

        municipalityEditText = findViewById(R.id.municipalityEditText);
        municipalityEditText.setOnEditorActionListener((view, actionId, event) -> {
            boolean isGoOrDoneAction = actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND;
            boolean isEnterKey = event != null
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN;

            if (isGoOrDoneAction || isEnterKey) {
                submitMunicipality();
                return true; // the event is consumed, the keyboard stays open
            }
            return false;
        });
    }

    /**
     * Validates the current text of the input field and either shows an error (and stays on
     * this screen) or starts the second activity with the normalised name.
     */
    private void submitMunicipality() {
        String rawInput = municipalityEditText.getText().toString();

        if (TextProcessor.isBlank(rawInput)) {
            showInputError(R.string.error_empty_input);
            return;
        }

        String municipalityName = TextProcessor.processMunicipality(rawInput);
        if (municipalityName == null) {
            showInputError(R.string.error_unsupported_municipality);
            return;
        }

        // Explicit Intent: the target component is known, so only this application can
        // receive the validated name.
        hideKeyboard();
        Intent intent = new Intent(this, Municipality.class);
        intent.putExtra(Municipality.EXTRA_MUNICIPALITY, municipalityName);
        startActivity(intent);
    }

    /**
     * Displays the reason why the input was rejected and keeps the user on the main screen.
     *
     * @param messageResId string resource describing the problem
     */
    private void showInputError(int messageResId) {
        municipalityEditText.setError(getString(messageResId));
        municipalityEditText.requestFocus();
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Hides the soft keyboard after the input has been accepted, so that it does not stay on
     * screen while the second activity opens.
     */
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(municipalityEditText.getWindowToken(), 0);
        }
    }

    /**
     * Keeps the content clear of the status bar and navigation bar, because the
     * activity draws edge to edge on Android 15 and above.
     */
    private void applySystemBarInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
