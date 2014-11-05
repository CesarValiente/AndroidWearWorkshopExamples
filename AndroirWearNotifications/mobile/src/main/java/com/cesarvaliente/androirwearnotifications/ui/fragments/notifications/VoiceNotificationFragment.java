package com.cesarvaliente.androirwearnotifications.ui.fragments.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import com.cesarvaliente.androirwearnotifications.R;
import com.cesarvaliente.androirwearnotifications.ui.activities.MainActivity;
import com.cesarvaliente.androirwearnotifications.ui.activities.ResultActivity;

/**
 * Created by cesar on 25/10/14.
 */
public class VoiceNotificationFragment extends BaseNotificationFragment {

    private final int VOICE_NOTIFICATION_ID = 2;

    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VoiceNotificationFragment newInstance() {
        VoiceNotificationFragment fragment = new VoiceNotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.voice));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.voice_notification_fragment, container, false);
        ButterKnife.inject(rootView);
        return rootView;
    }

    @Override
    public void showNotification() {

        //We have to create a RemoteInput object that provides a custom label for the voice input prompt
        String replyLabel = getResources().getString(R.string.voice_reply);

        //Voice reply choices
        String[] replyChoices = getResources().getStringArray(R.array.voice_reply_choices);

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

        //Create a intent for the reply action
        Intent replyIntent = new Intent(getActivity(), ResultActivity.class);
        PendingIntent replyPendingIntent = PendingIntent.getActivity(getActivity(), 0, replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create the reply action and add the remote imput
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(android.R.drawable
                .ic_dialog_email, getString(R.string.voice_reply), replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        //Build the notification and add the action via WeareableExtender
        Notification notification = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .setContentTitle(getString(R.string.voice_message))
                .setContentText(getString(R.string.voice_new_message))
                .extend(new NotificationCompat.WearableExtender().addAction(action))
                .build();

        //Sends the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        notificationManagerCompat.notify(VOICE_NOTIFICATION_ID, notification);
    }
}
