package org.howard.edu.lsp.assignment3;

import java.util.Locale;

/**
 * Implements the Assignment 2 transformation rules for product rows:
 * - Uppercase product name
 * - If category is Electronics, apply 10% discount; if discounted price > 500 → "Premium Electronics"
 * - Round to 2 decimals (half up)
 * - Compute PriceRange: Low (<=10), Medium (<=100), High (<=500), Premium (>500)
 */
public class ProductTransformer implements Transformer {
    @Override
    public String[] transformRow(final String[] cols) {
        if (cols.length < 4) {
            throw new IllegalArgumentException("Row has fewer than 4 columns");
        }

        int productId = Integer.parseInt(cols[0].trim());
        String name = cols[1].trim().toUpperCase();
        double price = Double.parseDouble(cols[2].trim());
        String category = cols[3].trim();

        // Discount for Electronics and potential recategorization
        if (category.equalsIgnoreCase("Electronics")) {
            price = Rounder.round(price * 0.9, 2);
            if (price > 500.00) {
                category = "Premium Electronics";
            }
        }

        price = Rounder.round(price, 2);

        final String priceRange =
                (price <= 10.00) ? "Low" :
                (price <= 100.00) ? "Medium" :
                (price <= 500.00) ? "High" : "Premium";

        return new String[] {
            String.valueOf(productId),
            name,
            String.format(Locale.ROOT, "%.2f", price),
            category,
            priceRange
        };
    }
}
