package com.cesarvaliente.androirwearnotifications.ui.fragments.notifications;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cesarvaliente.androirwearnotifications.R;
import com.cesarvaliente.androirwearnotifications.ui.activities.MainActivity;
import com.cesarvaliente.androirwearnotifications.ui.activities.ResultActivity;
import com.cesarvaliente.androirwearnotifications.ui.utils.Constants;
import com.cesarvaliente.androirwearnotifications.ui.utils.WearableUtils;

/**
 * Created by cesar on 25/10/14.
 */
public class BasicNotificationFragment extends BaseNotificationFragment {

    private final int MAX_NOTIFICATION_CONTENT_LENGTH = 15;

    @InjectView(R.id.basic_title)
    EditText mTitleEditText;

    @InjectView(R.id.basic_content)
    EditText mContentEditText;

    @InjectView(R.id.basic_map)
    CheckBox mAction1Checkbox;

    @InjectView(R.id.basic_just_wearable)
    CheckBox mJustWearableCheckbox;

    @InjectView(R.id.basic_hide_icon)
    CheckBox mHideIconCheckbox;

    @InjectView(R.id.basic_change_background)
    CheckBox mChangeBackgroundCheckbox;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BasicNotificationFragment newInstance() {
        BasicNotificationFragment fragment = new BasicNotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.basic));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.basic_notification_fragment, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void showNotification() {

        String title = checkTitle();
        String content = checkContent();

        Intent viewIntent = new Intent(getActivity(), ResultActivity.class);
        viewIntent.putExtra(Constants.RESULT_TEXT_EXTRA, title + " " + content);
        viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, viewIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent);

        if (content.length() > MAX_NOTIFICATION_CONTENT_LENGTH) {
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.bigText(content);
            notificationBuilder.setStyle(bigTextStyle);
        }

        //Always use NotificationCompat since some features of the WearableExtender on the NotificationManager
        //don't work
        NotificationCompat.WearableExtender wearableExtender = null;

        if (mAction1Checkbox.isChecked()) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode("46.7833856,23.6165124(Hello world!!)"));
            mapIntent.setData(geoUri);
            PendingIntent mapPendingIntent = PendingIntent.getActivity(getActivity(), 0, mapIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                    android.R.drawable.ic_dialog_map, getString(R.string.map),
                    mapPendingIntent).build();

            if (mJustWearableCheckbox.isChecked()) {
                wearableExtender = WearableUtils.getWearableExtender(wearableExtender).addAction(action);
            } else {
                notificationBuilder.addAction(action);
            }
        }

        if (mHideIconCheckbox.isChecked()) {
            wearableExtender = WearableUtils.getWearableExtender(wearableExtender).setHintHideIcon(true);
        }

        if (mChangeBackgroundCheckbox.isChecked()) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.common_ic_googleplayservices);
            wearableExtender = WearableUtils.getWearableExtender(wearableExtender).setBackground(bitmap);
        }

        if (wearableExtender != null) {
            notificationBuilder.extend(wearableExtender);
        }

        //Always use NotficiationManagerCompat since some functions don't work in the NotificationManager api
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(Constants.BASIC_NOTIFICATION_ID, notificationBuilder.build());
    }

    private String checkTitle() {
        return checkField(mTitleEditText, R.string.title);
    }

    private String checkContent() {
        return checkField(mContentEditText, R.string.content);
    }

    private String checkField(EditText editText, int defaultText) {

        if (!TextUtils.isEmpty(editText.getText().toString())) {
            return editText.getText().toString();
        } else {
            return getString(defaultText);
        }
    }
}
