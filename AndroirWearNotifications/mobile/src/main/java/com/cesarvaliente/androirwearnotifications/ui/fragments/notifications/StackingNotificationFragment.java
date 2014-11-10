package com.cesarvaliente.androirwearnotifications.ui.fragments.notifications;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cesarvaliente.androirwearnotifications.R;
import com.cesarvaliente.androirwearnotifications.ui.activities.MainActivity;
import com.cesarvaliente.androirwearnotifications.ui.activities.ResultActivity;
import com.cesarvaliente.androirwearnotifications.ui.utils.Constants;

/**
 * Created by cesar on 25/10/14.
 */
public class StackingNotificationFragment extends BaseNotificationFragment {

    @InjectView(R.id.stack_1)
    CheckBox mEmail1;
    @InjectView(R.id.stack_2)
    CheckBox mEmail2;
    @InjectView(R.id.stack_3)
    CheckBox mEmail3;
    @InjectView(R.id.stack_4)
    CheckBox mEmail4;
    @InjectView(R.id.stack_summary)
    CheckBox mSummary;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StackingNotificationFragment newInstance() {
        StackingNotificationFragment fragment = new StackingNotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.stacking));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stacking_notification_fragment, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void showNotification() {
        List<Notification> notificationList = getNotificationList();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        int size = notificationList.size();
        for (int i = 0; i < size; i++) {
            notificationManagerCompat.notify(i, notificationList.get(i));
        }

        if (mSummary.isChecked()) {
            notificationManagerCompat.notify(size, getSummaryNotification(size));
        }
    }

    /**
     * Creates a {@link android.app.PendingIntent} that will be used to launch the {@link com.cesarvaliente
     * .androirwearnotifications.ui.activities.ResultActivity}
     * with some attached info.
     * @param textResult
     * @param notificationId   (used to identify uniquely a notification)
     * @return
     */
    private PendingIntent getPendingIntent(String textResult, int notificationId) {

        Intent replyIntent = new Intent(getActivity(), ResultActivity.class);
        replyIntent.putExtra(Constants.RESULT_TEXT_EXTRA, textResult);
        replyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return PendingIntent.getActivity(getActivity(), notificationId, replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Creates a simple email notification that will share the
     * group notification id that is used in this example to
     * group all notifications in one.
     * @param textId
     * @return
     */
    private Notification getNotification(int textId) {

        return new NotificationCompat.Builder(getActivity())
                .setContentIntent(getPendingIntent(getString(textId), textId))
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle(getString(R.string.stack_title))
                .setContentText(getString(textId))
                .setGroup(Constants.STACK_NOTIFICATION_GROUP_ID)
                .build();
    }

    /**
     * Creates the summary notification that will be useful for handheld devices
     * @param numEmails
     * @return
     */
    private Notification getSummaryNotification(int numEmails) {

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_email);

        String textResult = getTextResult(numEmails);

        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                .setBackground(largeIcon);

        return new NotificationCompat.Builder(getActivity())
                .setContentIntent(getPendingIntent(textResult, 0))
                .setLargeIcon(largeIcon)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle(textResult)
                .setStyle(getInboxStyle(numEmails))
                .extend(wearableExtender)
                .setGroupSummary(mSummary.isChecked())
                .setGroup(Constants.STACK_NOTIFICATION_GROUP_ID)
                .build();
    }

    /**
     * Creates the text that is going to use the summary notification
     * as well the activity result to show the summary
     * @param numEmails
     * @return
     */
    private String getTextResult(int numEmails) {
        return getResources().getQuantityString(R.plurals.stack_num_emails, numEmails, numEmails);
    }


    /**
     * Returns a {@link java.util.List} of {@link android.app.Notification} with all the email notifications that
     * the user has selected
     * @return
     */
    private List<Notification> getNotificationList() {

        List<Notification> notificationList = new ArrayList<Notification>();

        if (mEmail1.isChecked()) {
            notificationList.add(getNotification(R.string.stack_1_value));
        }
        if (mEmail2.isChecked()) {
            notificationList.add(getNotification(R.string.stack_2_value));
        }
        if (mEmail3.isChecked()) {
            notificationList.add(getNotification(R.string.stack_3_value));
        }
        if (mEmail4.isChecked()) {
            notificationList.add(getNotification(R.string.stack_4_value));
        }
        return notificationList;
    }

    /**
     * Gets an {@link android.support.v4.app.NotificationCompat.InboxStyle} that will give style to the summary
     * notification
     * @param numEmails
     * @return
     */
    private NotificationCompat.InboxStyle getInboxStyle(int numEmails) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        if (mEmail1.isChecked()) {
            inboxStyle.addLine(getString(R.string.stack_1_value));
        }
        if (mEmail2.isChecked()) {
            inboxStyle.addLine(getString(R.string.stack_2_value));
        }
        if (mEmail3.isChecked()) {
            inboxStyle.addLine(getString(R.string.stack_3_value));
        }
        if (mEmail4.isChecked()) {
            inboxStyle.addLine(getString(R.string.stack_4_value));
        }

        inboxStyle.setBigContentTitle(getTextResult(numEmails))
                  .setSummaryText(getString(R.string.stack_my_email));

        return inboxStyle;
    }

}
