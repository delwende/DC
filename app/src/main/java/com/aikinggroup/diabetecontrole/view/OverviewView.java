package com.aikinggroup.diabetecontrole.view;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public interface OverviewView {
    @NonNull
    String convertDate(@NonNull final String date);

    @NonNull
    String convertDateToMonth(@NonNull final String date);

    @NonNull
    String getString(@StringRes final int stringId);
}
