package com.cesarvaliente.androirwearnotifications.ui.fragments.notifications;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
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
public class PagesNotificationFragment extends BaseNotificationFragment {

    @InjectView(R.id.page_1_checkbox)
    CheckBox mPage1;

    @InjectView(R.id.page_2_checkbox)
    CheckBox mPage2;

    @InjectView(R.id.page_3_checkbox)
    CheckBox mPage3;

    @InjectView(R.id.page_4_checkbox)
    CheckBox mPage4;

    @InjectView(R.id.page_5_checkbox)
    CheckBox mPage5;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PagesNotificationFragment newInstance() {
        PagesNotificationFragment fragment = new PagesNotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.pages));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pages_notification_fragment, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void showNotification() {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(Constants.PAGES_NOTIFICATION_ID, getNotification());
    }

    /**
     * Gets the {@link android.app.Notification} we are going to use in this example with all pages added to it.
     * @return
     */
    private Notification getNotification() {

        List<Notification> pages = getNotificationPages();
        int numPages = pages.size();

        String textContent = getResources().getQuantityString(R.plurals.pages_content, numPages,
                numPages);
        NotificationCompat.Builder builder = getNotificationBuilder(textContent);
        builder.setContentText(textContent);

        return new NotificationCompat.WearableExtender()
                .addPages(pages)
                .extend(builder)
                .build();
    }

    /**
     * Creates the {@link android.support.v4.app.NotificationCompat.Builder} we are going to use to build our
     * pages notification.
     * @return
     */
    private NotificationCompat.Builder getNotificationBuilder() {

        Intent replyIntent = new Intent(getActivity(), ResultActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getActivity())
                .setSmallIcon(android.R.drawable.ic_input_get)
                .setContentTitle(getString(R.string.pages_title))
                .setContentIntent(pendingIntent);
    }


    /**
     * Gets a {@link android.support.v4.app.NotificationCompat.BigTextStyle} instance that we use to give shape
     * and content to each page
     * @param title
     * @param content
     * @return
     */
    private NotificationCompat.BigTextStyle getBigStyle(String title, String content) {

        return new NotificationCompat.BigTextStyle()
                .setBigContentTitle(title)
                .bigText(content);
    }

    /**
     * Creates a new Page (a notification page)
     * @param title
     * @param content
     * @return
     */
    private Notification getBigPageNotification(int title, int content) {

        return new NotificationCompat.Builder(getActivity())
                .setStyle(getBigStyle(getString(title), getString(content)))
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .build();
    }

    /**
     * Creates a {@link java.util.List} of {@link android.app.Notification}
     * @return
     */
    private List<Notification> getNotificationPages() {

        List<Notification> notificationList = new ArrayList<Notification>();

        if (mPage1.isChecked()) {
            notificationList.add(getBigPageNotification(R.string.pages_title_1, R.string.pages_content_1));
        }
        if (mPage2.isChecked()) {
            notificationList.add(getBigPageNotification(R.string.pages_title_2, R.string.pages_content_2));
        }
        if (mPage3.isChecked()) {
            notificationList.add(getBigPageNotification(R.string.pages_title_3, R.string.pages_content_3));
        }
        if (mPage4.isChecked()) {
            notificationList.add(getBigPageNotification(R.string.pages_title_4, R.string.pages_content_4));
        }
        if (mPage5.isChecked()) {
            notificationList.add(getBigPageNotification(R.string.pages_title_5, R.string.pages_content_5));
        }

        return notificationList;
    }
}
