package com.cesarvaliente.androirwearnotifications.ui.fragments.notifications;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cesarvaliente.androirwearnotifications.R;
import com.cesarvaliente.androirwearnotifications.ui.activities.MainActivity;
import com.cesarvaliente.androirwearnotifications.ui.activities.OpenActivity;

/**
 * Created by cesar on 25/10/14.
 */
public class BasicNotificationFragment extends Fragment {

    private final int BASIC_NOTIFICATION_ID = 1;

    @InjectView(R.id.basic_title)
    EditText mTitleEditText;

    @InjectView(R.id.basic_content)
    EditText mContentEditText;

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

    public void showNotification() {

        String title = checkTitle();
        String content = checkContent();

        Intent viewIntent = new Intent(getActivity(), OpenActivity.class);
        viewIntent.putExtra(OpenActivity.OPEN_INTENT_EXTRA, title + " " + content);
        viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, viewIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(BASIC_NOTIFICATION_ID, notificationBuilder.build());
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
