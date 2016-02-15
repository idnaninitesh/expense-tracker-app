package com.codeitsuisse.team28.expensetracker.card;

import android.support.annotation.NonNull;
import android.view.View;

/**
 *
 */
public interface CardRenderer {
    /**
     *
     * @param view
     * @param card
     */
    void render(@NonNull final View view, @NonNull final Card card);
}
