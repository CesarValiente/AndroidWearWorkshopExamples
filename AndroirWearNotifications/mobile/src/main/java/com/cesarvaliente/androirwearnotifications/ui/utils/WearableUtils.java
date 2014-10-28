package com.cesarvaliente.androirwearnotifications.ui.utils;

import android.support.v4.app.NotificationCompat;

/**
 * Created by cesar on 28/10/14.
 */
public class WearableUtils {

    /**
     * Checks and return an instance of {@link android.support.v4.app.NotificationCompat.WearableExtender}
     * @param wearableExtender
     * @return
     */
    public static NotificationCompat.WearableExtender getWearableExtender(
            NotificationCompat.WearableExtender wearableExtender) {

        if (wearableExtender == null) {
            return new NotificationCompat.WearableExtender();
        } else {
            return wearableExtender;
        }
    }
}
