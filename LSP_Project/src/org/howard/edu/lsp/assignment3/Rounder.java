package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility for decimal rounding with HALF_UP behavior.
 */
public final class Rounder {
    private Rounder() {}

    /**
     * Rounds a value to the given number of places using HALF_UP.
     * @param value input decimal
     * @param places number of decimal places
     * @return rounded double
     */
    public static double round(final double value, final int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
