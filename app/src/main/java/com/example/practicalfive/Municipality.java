package com.example.practicalfive;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Second screen of the application.
 *
 * <p>Student: Deng ZiChen (20243006955)</p>
 */
public class Municipality extends AppCompatActivity {

    /**
     * Intent extra key used to pass the validated municipality name from
     * {@link MainActivity} to this activity. Both activities use this single constant, so
     * the key can never get out of sync.
     */
    public static final String EXTRA_MUNICIPALITY = "com.example.practicalfive.extra.MUNICIPALITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);
        applySystemBarInsets();
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
