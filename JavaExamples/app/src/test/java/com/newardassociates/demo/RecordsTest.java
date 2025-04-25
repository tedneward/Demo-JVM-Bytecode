package com.newardassociates.demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class RecordsTest {
    @Test public void instantiateARecord() {
        Records records = new Records("Hee-Haw", "Country", "Hee-Haw", 1959);
        assertNotNull("Records should construct", records);
        assertEquals("Hee-Haw", records.album());
    }
    @Test public void equivalentRecordsAreEqual() {
        Records records1 = new Records("Hee-Haw", "Country", "Hee-Haw", 1959);
        Records records2 = new Records("Hee-Haw", "Country", "Hee-Haw", 1959);

        // Equivalent records are equal...
        assertTrue(records1.equals(records2));
        // ... but not identical
        assertFalse(records1 == records2);
    }
}
