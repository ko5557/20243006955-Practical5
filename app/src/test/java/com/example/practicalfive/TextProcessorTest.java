package com.example.practicalfive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link TextProcessor}.
 *
 * <p>Student: Deng ZiChen (20243006955)</p>
 */
public class TextProcessorTest {

    @Test
    public void lowerCaseNameIsCapitalised() {
        assertEquals("Beijing", TextProcessor.processMunicipality("beijing"));
    }

    @Test
    public void upperCaseNameIsCapitalised() {
        assertEquals("Shanghai", TextProcessor.processMunicipality("SHANGHAI"));
        assertEquals("Tianjin", TextProcessor.processMunicipality("TIANJIN"));
    }

    @Test
    public void mixedCaseNameIsCapitalised() {
        assertEquals("Chongqing", TextProcessor.processMunicipality("ChOnGqInG"));
    }

    @Test
    public void surroundingWhitespaceIsIgnored() {
        assertEquals("Beijing", TextProcessor.processMunicipality("   beijing   "));
    }

    @Test
    public void unsupportedMunicipalityIsRejected() {
        assertNull(TextProcessor.processMunicipality("Guangzhou"));
    }

    @Test
    public void nullEmptyAndWhitespaceAreRejected() {
        assertNull(TextProcessor.processMunicipality(null));
        assertNull(TextProcessor.processMunicipality(""));
        assertNull(TextProcessor.processMunicipality("     "));
    }

    @Test
    public void blankHelperMatchesTheSameRules() {
        assertTrue(TextProcessor.isBlank(null));
        assertTrue(TextProcessor.isBlank(""));
        assertTrue(TextProcessor.isBlank("  \t "));
        assertTrue(!TextProcessor.isBlank("Beijing"));
    }

    @Test
    public void summaryIndexFollowsTheStringArrayOrder() {
        assertEquals(0, TextProcessor.indexOfMunicipality("Beijing"));
        assertEquals(1, TextProcessor.indexOfMunicipality("Shanghai"));
        assertEquals(2, TextProcessor.indexOfMunicipality("Chongqing"));
        assertEquals(3, TextProcessor.indexOfMunicipality("Tianjin"));
        assertEquals(-1, TextProcessor.indexOfMunicipality("Guangzhou"));
    }
}
