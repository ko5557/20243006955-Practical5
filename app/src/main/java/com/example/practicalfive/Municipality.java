package com.example.practicalfive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Second screen: displays the information of the municipality that was selected on the
 * main screen, changes its appearance according to that municipality and opens the official
 * website of the municipality.
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

    /** Official website of the municipality that is currently displayed. */
    private String websiteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);
        applySystemBarInsets();

        // The name is validated again here so that missing or tampered data is handled
        // safely instead of crashing the activity.
        String municipalityName =
                TextProcessor.processMunicipality(getIntent().getStringExtra(EXTRA_MUNICIPALITY));
        if (municipalityName == null) {
            Toast.makeText(this, R.string.error_missing_municipality, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setTitle(municipalityName);
        displayMunicipality(municipalityName);

        // Button click event: open the official website of the displayed municipality.
        Button visitSiteButton = findViewById(R.id.visitSiteButton);
        visitSiteButton.setOnClickListener(view -> openMunicipalityWebsite());
    }

    /**
     * Applies the name, image, background colour, information text colour and summary that
     * belong to the given municipality.
     *
     * @param municipalityName a normalised municipality name, for example {@code "Beijing"}
     */
    private void displayMunicipality(String municipalityName) {
        int accentColorRes;
        int imageRes;
        int websiteRes;

        switch (municipalityName) {
            case TextProcessor.BEIJING:
                accentColorRes = R.color.municipality_beijing;
                imageRes = R.drawable.beijing;
                websiteRes = R.string.website_beijing;
                break;
            case TextProcessor.SHANGHAI:
                accentColorRes = R.color.municipality_shanghai;
                imageRes = R.drawable.shanghai;
                websiteRes = R.string.website_shanghai;
                break;
            case TextProcessor.TIANJIN:
                accentColorRes = R.color.municipality_tianjin;
                imageRes = R.drawable.tianjin;
                websiteRes = R.string.website_tianjin;
                break;
            case TextProcessor.CHONGQING:
                accentColorRes = R.color.municipality_chongqing;
                imageRes = R.drawable.chongqing;
                websiteRes = R.string.website_chongqing;
                break;
            default:
                // Reached only when a name passed the earlier checks but has no resources.
                Toast.makeText(this, R.string.error_missing_municipality, Toast.LENGTH_LONG).show();
                finish();
                return;
        }

        websiteUrl = getString(websiteRes);

        int accentColor = ContextCompat.getColor(this, accentColorRes);
        int lightTextColor = ContextCompat.getColor(this, R.color.color_text_light);

        TextView nameTextView = findViewById(R.id.municipalityNameTextView);
        ImageView municipalityImageView = findViewById(R.id.municipalityImageView);
        TextView infoTextView = findViewById(R.id.infoTextView);

        // Name of the municipality on a background of its own colour.
        nameTextView.setText(municipalityName);
        nameTextView.setBackgroundColor(accentColor);
        nameTextView.setTextColor(lightTextColor);

        // Image that belongs to the municipality.
        municipalityImageView.setImageResource(imageRes);
        municipalityImageView.setContentDescription(
                getString(R.string.content_description_municipality_image_named, municipalityName));

        // Summary taken from municipalities_array, in the same order as TextProcessor.
        infoTextView.setText(readMunicipalitySummary(municipalityName));
        infoTextView.setTextColor(accentColor);

        // The "Visit site" button uses the same colour as the municipality name.
        // Both the background colour and the background tint are set, so the colour is
        // applied whether the button is inflated as a plain Button or as a MaterialButton.
        Button visitSiteButton = findViewById(R.id.visitSiteButton);
        visitSiteButton.setBackgroundColor(accentColor);
        visitSiteButton.setBackgroundTintList(ColorStateList.valueOf(accentColor));
        visitSiteButton.setTextColor(lightTextColor);
    }

    /**
     * Reads the factual summary of the given municipality from the
     * {@code municipalities_array} resource.
     *
     * @param municipalityName a normalised municipality name
     * @return the matching summary, or an empty string when the name is not supported
     */
    private String readMunicipalitySummary(String municipalityName) {
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);
        int index = TextProcessor.indexOfMunicipality(municipalityName);
        return index >= 0 && index < summaries.length ? summaries[index] : "";
    }

    /**
     * Opens the official website of the municipality that is currently displayed.
     *
     * <p>An <em>implicit</em> Intent is used: the application does not name a target
     * component, it only describes the action ({@link Intent#ACTION_VIEW}) and the data
     * (the website URL), so Android can resolve the request to a suitable application,
     * normally a web browser.</p>
     */
    private void openMunicipalityWebsite() {
        if (websiteUrl == null) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            // No browser (or other suitable application) is installed on this device.
            Toast.makeText(this, R.string.error_no_browser, Toast.LENGTH_LONG).show();
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
