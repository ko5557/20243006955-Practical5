package com.example.practicalfive;

/**
 * Validates and normalises the municipality name typed by the user.
 *
 * <p>This is a plain Java class on purpose: it does not extend {@code Activity} and it has
 * no associated XML layout, so the same validation logic can be reused by every screen
 * instead of being duplicated inside the activities.</p>
 *
 * <p>Student: Deng ZiChen (20243006955)</p>
 */
public class TextProcessor {

    /** Correctly capitalised name of Beijing. */
    public static final String BEIJING = "Beijing";
    /** Correctly capitalised name of Shanghai. */
    public static final String SHANGHAI = "Shanghai";
    /** Correctly capitalised name of Chongqing. */
    public static final String CHONGQING = "Chongqing";
    /** Correctly capitalised name of Tianjin. */
    public static final String TIANJIN = "Tianjin";

    /**
     * Supported municipalities, kept in the order of the summaries in
     * {@code res/values/arrays.xml}.
     */
    private static final String[] SUPPORTED_MUNICIPALITIES = {BEIJING, SHANGHAI, CHONGQING, TIANJIN};

    /**
     * Validates the municipality entered by the user and normalises its capitalisation.
     *
     * @param municipality the raw text typed by the user (may be {@code null})
     * @return the correctly capitalised municipality name, or {@code null} when the input is
     *         {@code null}, empty, whitespace only, or not one of the four supported
     *         municipalities
     */
    public static String processMunicipality(String municipality) {
        if (municipality == null) {
            return null;
        }

        String candidate = municipality.trim();
        if (candidate.isEmpty()) {
            return null;
        }

        for (String supported : SUPPORTED_MUNICIPALITIES) {
            if (supported.equalsIgnoreCase(candidate)) {
                return supported;
            }
        }

        // Unsupported name, for example "Guangzhou".
        return null;
    }

    /**
     * @param value text to inspect
     * @return {@code true} when the text is {@code null}, empty or contains only whitespace
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Finds the position of a municipality inside {@code municipalities_array}.
     *
     * @param municipalityName a normalised municipality name, for example {@code "Beijing"}
     * @return the index of the matching summary, or {@code -1} when the name is not supported
     */
    public static int indexOfMunicipality(String municipalityName) {
        for (int index = 0; index < SUPPORTED_MUNICIPALITIES.length; index++) {
            if (SUPPORTED_MUNICIPALITIES[index].equals(municipalityName)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * @return a copy of the supported municipality names, in {@code municipalities_array} order
     */
    public static String[] supportedMunicipalities() {
        return SUPPORTED_MUNICIPALITIES.clone();
    }
}
